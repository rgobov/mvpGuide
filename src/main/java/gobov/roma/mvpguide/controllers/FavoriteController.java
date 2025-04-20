package gobov.roma.mvpguide.controllers;

import gobov.roma.mvpguide.dto.FavoriteRequest;
import gobov.roma.mvpguide.model.Favorite;
import gobov.roma.mvpguide.model.User;
import gobov.roma.mvpguide.security.UserContextHolder;
import gobov.roma.mvpguide.services.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@Valid @RequestBody FavoriteRequest request) {
        User currentUser = UserContextHolder.getContext().getUser();

        // Добавление в избранное доступно только авторизованным пользователям
        if (currentUser.getRole() != User.Role.AUTHORIZED) {
            return ResponseEntity.status(403).build();
        }

        Favorite favorite = favoriteService.addFavorite(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
    }
}