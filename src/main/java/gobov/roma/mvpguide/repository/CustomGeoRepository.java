package gobov.roma.mvpguide.repository;

import gobov.roma.mvpguide.model.Route;
import java.util.List;

public interface CustomGeoRepository {
    List<Route> findRoutesNearby(double lat, double lng, double radius);
}