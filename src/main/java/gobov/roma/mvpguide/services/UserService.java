package gobov.roma.mvpguide.services;

import gobov.roma.mvpguide.dto.UserRegistrationDTO;
import gobov.roma.mvpguide.model.User;
import gobov.roma.mvpguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Инжектируем BCryptPasswordEncoder

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setEmail(updatedUser.getEmail());
                    user.setUsername(updatedUser.getUsername());
                    user.setPasswordHash(updatedUser.getPasswordHash());
                    user.setRole(updatedUser.getRole());
                    user.setLanguage(updatedUser.getLanguage());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateInterests(Long id, List<String> interests) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setInterests(interests);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
    }

    public User createGuestUser(String username) {
        User guest = new User();
        guest.setUsername(username);
        guest.setRole(User.Role.UNAUTHORIZED);
        return userRepository.save(guest);
    }

    public User registerUser(UserRegistrationDTO dto) {
        // Проверка на уникальность email
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + dto.getEmail());
        }

        // Проверка на уникальность username
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + dto.getUsername());
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword())); // Хэшируем пароль
        user.setRole(User.Role.AUTHORIZED);
        user.setLanguage(dto.getLanguage());
        return userRepository.save(user);
    }
}