package gobov.roma.mvpguide.repository;

import gobov.roma.mvpguide.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    // Заменяем findByType на запрос, который ищет по environmentTypes
    @Query("SELECT r FROM Route r JOIN r.environmentTypes et WHERE et = :environmentType")
    List<Route> findByEnvironmentType(@Param("environmentType") Route.EnvironmentType environmentType);

    // Дополнительно можно добавить метод для поиска по tourFormat
    List<Route> findByTourFormat(Route.TourFormat tourFormat);

    // Метод для поиска по городу (если нужно)
    List<Route> findByCityIgnoreCase(String city);
}