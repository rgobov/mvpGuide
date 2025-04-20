package gobov.roma.mvpguide.controllers;

import gobov.roma.mvpguide.dto.UserRegistrationDTO;
import gobov.roma.mvpguide.model.User;
import gobov.roma.mvpguide.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/guest")
    public ResponseEntity<User> createGuestSession(@RequestParam String username) {
        User user = userService.createGuestUser(username);
        return ResponseEntity.status(201).body(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserRegistrationDTO dto) {
        User user = userService.registerUser(dto);
        return ResponseEntity.status(201).body(user);
    }
}