package gobov.roma.mvpguide.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;  // Опционально для зарегистрированных

    private String username;

    @Column(name = "password_hash")
    private String passwordHash;  // Только для зарегистрированных

    @Enumerated(EnumType.STRING)
    private UserType type = UserType.GUEST;  // GUEST или REGISTERED

    @ElementCollection
    private List<String> interests;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Favorite> favorites;

    // Добавлено поле для языка
    @Column(name = "language")
    private String language;

    public enum UserType {
        GUEST, REGISTERED
    }
}