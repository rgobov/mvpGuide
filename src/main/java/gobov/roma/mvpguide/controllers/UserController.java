package gobov.roma.mvpguide.controllers;

import gobov.roma.mvpguide.dto.UserRegistrationDTO;
import gobov.roma.mvpguide.model.User;
import gobov.roma.mvpguide.security.UserContextHolder;
import gobov.roma.mvpguide.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User currentUser = UserContextHolder.getContext().getUser();

        // Только сам пользователь может получить свои данные
        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(403).build(); // Доступ запрещен
        }

        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDTO dto) {
        User user = userService.registerUser(dto);
        return ResponseEntity.status(201).body(user);
    }

    @PostMapping("/guest")
    public ResponseEntity<User> createGuestUser(@RequestBody String username) {
        User guest = userService.createGuestUser(username);
        return ResponseEntity.status(201).body(guest);
    }

    @PutMapping("/{id}/interests")
    public ResponseEntity<User> updateInterests(@PathVariable Long id, @RequestBody List<String> interests) {
        User currentUser = UserContextHolder.getContext().getUser();

        // Только сам пользователь может обновлять свои интересы
        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(403).build(); // Доступ запрещен
        }

        User updatedUser = userService.updateInterests(id, interests);
        return ResponseEntity.ok(updatedUser);
    }
}