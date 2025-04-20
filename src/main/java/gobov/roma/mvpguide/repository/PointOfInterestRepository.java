package gobov.roma.mvpguide.repository;

import gobov.roma.mvpguide.model.PointOfInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Long> {

    // Геопоиск через нативный SQL
    @Query(value = """
        SELECT * FROM points_of_interest p 
        WHERE ST_DWithin(p.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius)
        ORDER BY ST_Distance(p.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326))
        """, nativeQuery = true)
    List<PointOfInterest> findNearby(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radius") double radius);

    @Query("SELECT p FROM PointOfInterest p WHERE p.route.id = :routeId ORDER BY p.order")
    List<PointOfInterest> findByRouteId(@Param("routeId") Long routeId);

    @Modifying
    @Query("UPDATE PointOfInterest p SET p.arData = :arData WHERE p.id = :id")
    void updateARData(@Param("id") Long id, @Param("arData") Map<String, Object> arData);
}