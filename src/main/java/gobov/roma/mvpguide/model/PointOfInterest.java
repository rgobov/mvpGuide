package gobov.roma.mvpguide.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Point;

import java.util.Map;

@Entity
@Table(name = "points_of_interest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointOfInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false, columnDefinition = "geometry(Point, 4326)")
    private Point location; // Для хранения latitude и longitude

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "order_in_route", nullable = false)
    private Integer order;

    @Column(name = "beacon_id")
    private String beaconId; // Для музейных экскурсий

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "ar_data", columnDefinition = "jsonb")
    private Map<String, Object> arData; // Для AR-контента

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "indoor_coordinates", columnDefinition = "jsonb")
    private Map<String, Object> indoorCoordinates; // Для музейных координат

    @Column(name = "ar_precision")
    private Double arPrecision; // Точность для AR (в метрах)
}