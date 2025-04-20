package gobov.roma.mvpguide.repository;

import gobov.roma.mvpguide.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.favorites WHERE u.id = :id")
    Optional<User> findByIdWithFavorites(@Param("id") Long id);

    @Modifying
    @Query("UPDATE User u SET u.language = :language WHERE u.id = :id")
    void updateLanguage(@Param("id") Long id, @Param("language") String language);
}