package gobov.roma.mvpguide.repository;

import gobov.roma.mvpguide.model.Route;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomGeoRepositoryImpl implements CustomGeoRepository {
    private final EntityManager em;

    @Override
    public List<Route> findRoutesNearby(double lat, double lng, double radius) {
        return em.createQuery("""
            SELECT r FROM Route r JOIN r.points p 
            WHERE ST_DWithin(p.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius)
            GROUP BY r
            """, Route.class)
                .setParameter("lat", lat)
                .setParameter("lng", lng)
                .setParameter("radius", radius)
                .getResultList();
    }
}