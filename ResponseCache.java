
/**
 * Write a description of class RequestCache here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;
import javafx.util.*;

public class ResponseCache
{
    /**
     * response cache map
     */
    HashMap<Request, Response> _cache = new HashMap<>(); /// response cache

    /**
     * maximum distance threshold in meters
     */
    int _maxDistanceThreshold = 300; 
    
    public ResponseCache()
    {
    }

    public ResponseCache(int maxDistanceThreshold)
    {
        _maxDistanceThreshold = maxDistanceThreshold;
    }

    public void setMaxDistanceThreshold(int distanceInMeters)
    {
        _maxDistanceThreshold = distanceInMeters;
    }
    
    public synchronized void add(Request request, Response response)
    {
        _cache.put(request, response);
        System.out.println("cache count:" + _cache.size() );
    }

    public synchronized Response find(Request request)
    {
        for(Request r : _cache.keySet())
        {
            //double lat = Math.pow( (r.lat() - request.lat()), 2);
            //double lon = Math.pow((r.lon() - request.lon()), 2);

            //double d = Math.sqrt(lat + lon);

            double d = distance(r, request);
            System.out.println("distance: " + d);

            if(d < _maxDistanceThreshold)
            {
                System.out.println("close enough");
                return _cache.get(r);
            }
        }

        return null;
    }
    
    /**
     * FROM https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
     * 
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * 
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
    */
    public static double distance(
        Request r1,
        Request r2) 
    {
        double lat1 = r1.lat();
        double lat2 = r2.lat();
        double lon1 = r1.lon();
        double lon2 = r2.lon();
        double el1 = 0.0D;
        double el2 = 0.0D;
        
        final int R = 6371; // Radius of the earth
        
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        
        double height = el1 - el2;
        
        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        
        return Math.sqrt(distance);
    }    
}
