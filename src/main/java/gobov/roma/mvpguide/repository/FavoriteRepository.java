package gobov.roma.mvpguide.repository;

import gobov.roma.mvpguide.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("""
        SELECT f FROM Favorite f 
        WHERE f.user.id = :userId 
        AND (f.route IS NOT NULL OR f.pointOfInterest IS NOT NULL)
        """)
    List<Favorite> findByUserId(@Param("userId") Long userId);

    boolean existsByUserIdAndRouteId(Long userId, Long routeId);
    boolean existsByUserIdAndPointOfInterestId(Long userId, Long poiId);

    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.user.id = :userId AND f.route.id = :routeId")
    void deleteByUserAndRoute(@Param("userId") Long userId, @Param("routeId") Long routeId);
}