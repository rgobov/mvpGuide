package gobov.roma.mvpguide.repository;

import gobov.roma.mvpguide.model.EmergencyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyInfoRepository extends JpaRepository<EmergencyInfo, Long> {
    Optional<EmergencyInfo> findByCity(String city);

    @Query("SELECT e FROM EmergencyInfo e WHERE e.city IN :cities")
    List<EmergencyInfo> findByCities(@Param("cities") List<String> cities);

    @Modifying
    @Query("UPDATE EmergencyInfo e SET e.emergencyPhone = :phone WHERE e.city = :city")
    void updateEmergencyPhone(@Param("city") String city, @Param("phone") String phone);
}