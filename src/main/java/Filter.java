import com.twitter.hbc.core.endpoint.Location;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StreamingEndpoint;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import java.io.Serializable;
import java.util.List;
/**
 * Created by Isaac Godfried on 1/23/2017.
 */
public class Filter implements TwitterSource.EndpointInitializer, Serializable {
    private static List<String> terms;
    private static List<Location> locations;
    private static List<Long> userIDS;

    Filter(List<String> terms1, List<Location> l, List<Long> users ){
        terms = terms1;
        locations = l;
        userIDS = users;

    }
    public StreamingEndpoint createEndpoint(){

        StatusesFilterEndpoint status1 = new StatusesFilterEndpoint();
        if(terms!=null) status1.trackTerms(terms);
        if(locations!=null) status1.locations(locations);
        if(userIDS!=null) status1.followings(userIDS);
        return status1;
    }

}