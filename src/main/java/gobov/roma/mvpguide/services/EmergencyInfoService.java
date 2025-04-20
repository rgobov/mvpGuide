package gobov.roma.mvpguide.services;

import gobov.roma.mvpguide.repository.EmergencyInfoRepository;
import gobov.roma.mvpguide.model.EmergencyInfo;
import gobov.roma.mvpguide.dto.EmergencyInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmergencyInfoService {

    private final EmergencyInfoRepository emergencyInfoRepository;

    @Autowired
    public EmergencyInfoService(EmergencyInfoRepository emergencyInfoRepository) {
        this.emergencyInfoRepository = emergencyInfoRepository;
    }

    // Получение информации по городу
    public EmergencyInfoDTO getEmergencyInfoByCity(String city) {
        Optional<EmergencyInfo> emergencyInfo = emergencyInfoRepository.findByCity(city);
        return emergencyInfo.map(this::convertToDTO).orElse(null);
    }

    // Получение информации по списку городов
    public List<EmergencyInfoDTO> getEmergencyInfoByCities(List<String> cities) {
        List<EmergencyInfo> emergencyInfos = emergencyInfoRepository.findByCities(cities);
        return emergencyInfos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Обновление номера экстренной службы
    @Transactional
    public void updateEmergencyPhone(String city, String phone) {
        emergencyInfoRepository.updateEmergencyPhone(city, phone);
    }

    // Конвертация EmergencyInfo в EmergencyInfoDTO
    private EmergencyInfoDTO convertToDTO(EmergencyInfo emergencyInfo) {
        return EmergencyInfoDTO.builder()
                .id(emergencyInfo.getId())
                .city(emergencyInfo.getCity())
                .emergencyPhone(emergencyInfo.getEmergencyPhone())
                .hospitalAddress(emergencyInfo.getHospitalAddress())
                .safetyTips(emergencyInfo.getSafetyTips())
                .museumContact(emergencyInfo.getMuseumContact())
                .build();
    }
}