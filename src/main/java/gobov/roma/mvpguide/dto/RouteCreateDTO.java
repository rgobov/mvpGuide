package gobov.roma.mvpguide.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class RouteCreateDTO {
    @NotBlank
    private String title;
    private String city;
    private String type;
    private List<String> categories;
    private List<PointOfInterestDTO> points;
}