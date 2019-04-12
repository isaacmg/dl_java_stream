import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import java.util.List;

import java.util.ArrayList;

public class TweetData {
    public String tweetText;
    public String language;
    public String user;
    public List<Tuple2<String, String>> entsLabels;
    public ArrayList<String> urls;
    // Tuple in the format T
    public String sentiment;



    TweetData(String tweetText, String lang, String user, int retweetCount, int favoriteCount, int replyCount){
        this.tweetText = tweetText;
        language = lang;
        this.user = user;




    }
}
