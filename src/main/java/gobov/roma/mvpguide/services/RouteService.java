package gobov.roma.mvpguide.services;

import gobov.roma.mvpguide.repository.PointOfInterestRepository;
import gobov.roma.mvpguide.repository.RouteRepository;
import gobov.roma.mvpguide.dto.PointOfInterestDTO;
import gobov.roma.mvpguide.dto.RouteCreateDTO;
import gobov.roma.mvpguide.model.PointOfInterest;
import gobov.roma.mvpguide.model.Route;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final PointOfInterestRepository poiRepository;

    public List<Route> getRoutesByFilters(String city, String type) {
        if (city != null && type != null) {
            return routeRepository.findByCityAndType(city, type);
        } else if (city != null) {
            return routeRepository.findByCity(city);
        } else if (type != null) {
            return routeRepository.findByType(type);
        }
        return routeRepository.findAll();
    }

    public Route createRoute(RouteCreateDTO dto) {
        Route route = Route.builder()
                .title(dto.getTitle())
                .city(dto.getCity())
                .type(dto.getType())
                .categories(dto.getCategories())
                .build();

        Route savedRoute = routeRepository.save(route);
        savePOIsForRoute(savedRoute, dto.getPoints());
        return savedRoute;
    }

    private void savePOIsForRoute(Route route, List<PointOfInterestDTO> points) {
        points.forEach(poiDto -> {
            PointOfInterest poi = PointOfInterest.builder()
                    .route(route)
                    .title(poiDto.getTitle())
                    .location(createPoint(poiDto.getLat(), poiDto.getLng()))
                    .build();
            poiRepository.save(poi);
        });
    }

    private Point createPoint(double lat, double lng) {
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(new Coordinate(lng, lat));
    }
}