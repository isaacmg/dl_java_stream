import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import java.util.List;

import java.util.ArrayList;

public class TweetData {
    public String tweetText;
    public String language;
    public String user;
    public String tweetDateTime;
    public ArrayList<Tuple2<String, String>> entsLabels;
    public ArrayList<String> urls;
    // Tuple in the format T
    public String sentiment;
    public boolean properHashtag;



    TweetData(String tweetText, String lang, String user, boolean tweetGood, int retweetCount, int favoriteCount,
              int replyCount, String dateString){
        this.tweetText = tweetText;
        language = lang;
        this.user = user;
        properHashtag = tweetGood;
        tweetDateTime = dateString;




    }
}
