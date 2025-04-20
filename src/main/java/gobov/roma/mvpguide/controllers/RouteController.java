package gobov.roma.mvpguide.controllers;

import gobov.roma.mvpguide.dto.RouteCreateDTO;
import gobov.roma.mvpguide.model.Route;
import gobov.roma.mvpguide.model.User;
import gobov.roma.mvpguide.security.UserContextHolder;
import gobov.roma.mvpguide.services.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<List<Route>> getRoutes(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String environmentType,
            @RequestParam(required = false) String tourFormat) {
        List<Route> routes = routeService.getRoutesByFilters(city, environmentType, tourFormat);
        return ResponseEntity.ok(routes);
    }

    @PostMapping
    public ResponseEntity<Route> createRoute(@Valid @RequestBody RouteCreateDTO dto) {
        User currentUser = UserContextHolder.getContext().getUser();

        // Создание маршрутов доступно только авторизованным пользователям
        if (currentUser.getRole() != User.Role.AUTHORIZED) {
            return ResponseEntity.status(403).build();
        }

        Route route = routeService.createRoute(dto);
        return ResponseEntity.status(201).body(route);
    }
}