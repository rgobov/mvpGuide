package gobov.roma.mvpguide.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyInfoDTO {

    private Long id;

    private String city;

    private String emergencyPhone;

    private String hospitalAddress;

    private String safetyTips;

    private String museumContact;
}