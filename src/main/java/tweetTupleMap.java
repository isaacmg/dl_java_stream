import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;

public class tweetTupleMap implements MapFunction<TweetData, Tuple3<String, String, Integer>> {
    @Override
    public Tuple3<String, String, Integer> map(TweetData tweetData) throws Exception {
        return new Tuple3<>(tweetData.tweetText, tweetData.tweetDateTime, 1);
    }
}
