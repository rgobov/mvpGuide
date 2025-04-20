package gobov.roma.mvpguide.repository;
import gobov.roma.mvpguide.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByCity(String city);
    List<Route> findByType(String type);
    List<Route> findByCityAndType(String city, String type);

    @Query("SELECT r FROM Route r JOIN FETCH r.points WHERE r.id = :id")
    Optional<Route> findByIdWithPoints(@Param("id") Long id);

    @Query("SELECT r FROM Route r WHERE :category MEMBER OF r.categories")
    List<Route> findByCategory(@Param("category") String category);
}