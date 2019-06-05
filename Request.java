
/**
 * Write a description of class Request here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Request
{
    double _lat;
    double _lon;
    
    /**
     * Constructor for objects of class Request
     */
    public Request(double lat, double lon)
    {
        _lat = lat;
        _lon = lon;
    }
    
    public double lat()
    {
        return _lat;
    }
    
    public double lon()
    {
        return _lon;
    }
    
    public String xmlBody()
    {
        return createRequestFor(_lat, _lon);
    }
    
    public String createRequestFor(double lat, double lon)
    {
    String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    request += "<ReverseGeoRQ xmlns=\"http://skyhookwireless.com/wps/2005\" version=\"2.26\" street-address-lookup=\"full\">";
        request += "<authentication version=\"2.2\">";
        request += "<key key=\"eJwVwUsKACAIBcB1hxH88LS2il0quns0I0P4g08bx3gVHEWFDZJIUKYLmXrAWoub7wMPMgr_\" username=\"interview-candidate\"/>";
        request += "</authentication>";
        request += "<point>";
        request += "<latitude>" + lat + "</latitude>";
        request += "<longitude>" + lon + "</longitude>";
        request += "</point>";
        request += "</ReverseGeoRQ>";
        
        return request;
    }
}
