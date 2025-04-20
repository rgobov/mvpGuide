package gobov.roma.mvpguide.repository;

import gobov.roma.mvpguide.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByPointOfInterestId(Long poiId);
    List<Media> findByPointOfInterestIdAndType(Long poiId, String type);

    @Query("SELECT m FROM Media m WHERE m.arContent = true AND m.pointOfInterest.id = :poiId")
    List<Media> findARContentByPoi(@Param("poiId") Long poiId);

    @Modifying
    @Query("DELETE FROM Media m WHERE m.pointOfInterest.id = :poiId AND m.type = :type")
    void deleteByPoiIdAndType(@Param("poiId") Long poiId, @Param("type") String type);
}