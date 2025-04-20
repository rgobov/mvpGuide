package gobov.roma.mvpguide.dto;

import gobov.roma.mvpguide.model.Route;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class RouteCreateDTO {
    private String title;
    private String description;
    private String city;
    private Integer duration;
    private Double distance;
    private List<String> categories;
    private Set<Route.EnvironmentType> environmentTypes; // Новое поле
    private Route.TourFormat tourFormat; // Новое поле
    private List<PointOfInterestDTO> pointsOfInterest;

    @Data
    public static class PointOfInterestDTO {
        private String title;
        private String description;
        private PointDTO location;
        private String audioUrl;
        private String imageUrl;
        private Integer order;
        private String beaconId;
        private Map<String, Object> arData;
        private Map<String, Object> indoorCoordinates;
        private Double arPrecision;

        @Data
        public static class PointDTO {
            private double latitude;
            private double longitude;
        }
    }
}