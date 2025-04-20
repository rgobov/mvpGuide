package gobov.roma.mvpguide.services;

import gobov.roma.mvpguide.repository.PointOfInterestRepository;
import gobov.roma.mvpguide.exception.NotFoundException;
import gobov.roma.mvpguide.model.PointOfInterest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PointOfInterestService {
    private final PointOfInterestRepository poiRepository;

    @Transactional(readOnly = true)
    public List<PointOfInterest> getNearbyPoints(double lat, double lng, double radius) {
        // Получаем координаты из точки (если нужно)
        // Но лучше использовать параметры напрямую:
        return poiRepository.findNearby(lat, lng, radius);
    }

    public void updateARData(Long poiId, Map<String, Object> arData) {
        PointOfInterest poi = poiRepository.findById(poiId)
                .orElseThrow(() -> new NotFoundException("POI not found"));
        poi.setArData(arData);
        poiRepository.save(poi);
    }
}