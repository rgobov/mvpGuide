package gobov.roma.mvpguide.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "routes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String city;

    @Column
    private Integer duration; // В минутах

    @Column
    private Double distance; // В километрах

    @ElementCollection
    @CollectionTable(name = "route_categories", joinColumns = @JoinColumn(name = "route_id"))
    @Column(name = "category")
    private List<String> categories;

    @Column(nullable = false)
    private String type; // "outdoor" или "indoor"

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Добавлено отношение OneToMany с PointOfInterest
    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    private List<PointOfInterest> points;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}