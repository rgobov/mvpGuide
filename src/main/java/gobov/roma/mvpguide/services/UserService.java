package gobov.roma.mvpguide.services;

import gobov.roma.mvpguide.dto.UserRegistrationDTO;
import gobov.roma.mvpguide.exception.ConflictException;
import gobov.roma.mvpguide.exception.NotFoundException;
import gobov.roma.mvpguide.model.User;
import gobov.roma.mvpguide.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Создание гостевого пользователя
    public User createGuestUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setType(User.UserType.GUEST);
        return userRepository.save(user);
    }

    // Регистрация нового пользователя
    public User registerUser(UserRegistrationDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException("Email уже зарегистрирован: " + dto.getEmail());
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPasswordHash(hashPassword(dto.getPassword()));
        user.setUsername(dto.getUsername());
        user.setType(User.UserType.REGISTERED);
        return userRepository.save(user);
    }

    // Обновление интересов пользователя
    public User updateInterests(Long id, List<String> interests) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с ID: " + id));
        user.setInterests(interests);
        return userRepository.save(user);
    }

    // Получение пользователя по ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден с ID: " + id));
    }

    private String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}