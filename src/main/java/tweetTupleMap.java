import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple4;

public class tweetTupleMap implements MapFunction<TweetData, Tuple4<String, String, String, Integer>> {
    @Override
    public Tuple4<String, String, String, Integer> map(TweetData tweetData) throws Exception {
        return new Tuple4<>("nada", tweetData.sentiment, tweetData.tweetDateTime, 1);
    }
}
