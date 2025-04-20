package gobov.roma.mvpguide.services;

import gobov.roma.mvpguide.dto.RouteCreateDTO;
import gobov.roma.mvpguide.model.PointOfInterest;
import gobov.roma.mvpguide.model.Route;
import gobov.roma.mvpguide.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with id " + id));
    }

    public List<Route> getRoutesByFilters(String city, String environmentType, String tourFormat) {
        return routeRepository.findAll().stream()
                .filter(route -> city == null || route.getCity().equalsIgnoreCase(city))
                .filter(route -> environmentType == null || route.getEnvironmentTypes().stream()
                        .anyMatch(type -> type.name().equalsIgnoreCase(environmentType)))
                .filter(route -> tourFormat == null || route.getTourFormat().name().equalsIgnoreCase(tourFormat))
                .collect(Collectors.toList());
    }

    public Route createRoute(RouteCreateDTO dto) {
        Route route = Route.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .city(dto.getCity())
                .duration(dto.getDuration())
                .distance(dto.getDistance())
                .categories(dto.getCategories())
                .environmentTypes(dto.getEnvironmentTypes())
                .tourFormat(dto.getTourFormat())
                .build();

        List<PointOfInterest> points = dto.getPointsOfInterest().stream().map(poiDto -> {
            Point location = geometryFactory.createPoint(new Coordinate(
                    poiDto.getLocation().getLongitude(),
                    poiDto.getLocation().getLatitude()
            ));
            location.setSRID(4326);

            return PointOfInterest.builder()
                    .route(route)
                    .title(poiDto.getTitle())
                    .description(poiDto.getDescription())
                    .location(location)
                    .audioUrl(poiDto.getAudioUrl())
                    .imageUrl(poiDto.getImageUrl())
                    .order(poiDto.getOrder())
                    .beaconId(poiDto.getBeaconId())
                    .arData(poiDto.getArData())
                    .indoorCoordinates(poiDto.getIndoorCoordinates())
                    .arPrecision(poiDto.getArPrecision())
                    .build();
        }).collect(Collectors.toList());

        route.setPoints(points);
        return routeRepository.save(route);
    }
}