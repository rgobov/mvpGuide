package gobov.roma.mvpguide.dto;

import lombok.Data;

@Data
public class PointOfInterestDTO {
    private String title;
    private double lat;
    private double lng;
}