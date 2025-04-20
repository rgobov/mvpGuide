package gobov.roma.mvpguide.controllers;

import gobov.roma.mvpguide.model.PointOfInterest;
import gobov.roma.mvpguide.services.PointOfInterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/poi")
@RequiredArgsConstructor
public class POIController {
    private final PointOfInterestService poiService;

    @GetMapping("/nearby")
    public ResponseEntity<List<PointOfInterest>> getNearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam double radius) {
        List<PointOfInterest> pois = poiService.getNearbyPoints(lat, lng, radius);
        return ResponseEntity.ok(pois);
    }
}