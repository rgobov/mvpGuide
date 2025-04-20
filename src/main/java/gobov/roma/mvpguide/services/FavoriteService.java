package gobov.roma.mvpguide.services;

import gobov.roma.mvpguide.repository.FavoriteRepository;
import gobov.roma.mvpguide.repository.PointOfInterestRepository;
import gobov.roma.mvpguide.repository.RouteRepository;
import gobov.roma.mvpguide.repository.UserRepository;
import gobov.roma.mvpguide.dto.FavoriteRequest;
import gobov.roma.mvpguide.exception.NotFoundSomedException;
import gobov.roma.mvpguide.model.Favorite;
import gobov.roma.mvpguide.model.PointOfInterest;
import gobov.roma.mvpguide.model.Route;
import gobov.roma.mvpguide.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final PointOfInterestRepository poiRepository;

    @Transactional
    public Favorite addFavorite(FavoriteRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow();

        Favorite favorite = Favorite.builder()
                .user(user)
                .build();

        if (request.getRouteId() != null) {
            Route route = routeRepository.findById(request.getRouteId())
                    .orElseThrow(() -> new NotFoundSomedException("Route not found"));
            favorite.setRoute(route);
        }

        if (request.getPoiId() != null) {
            PointOfInterest poi = poiRepository.findById(request.getPoiId())
                    .orElseThrow(() -> new NotFoundSomedException("POI not found"));
            favorite.setPointOfInterest(poi);
        }

        return favoriteRepository.save(favorite);
    }
}