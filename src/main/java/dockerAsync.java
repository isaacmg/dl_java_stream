import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;

public class dockerAsync extends RichAsyncFunction<TweetData, String > {
    @Override
    public void asyncInvoke(TweetData tweet, ResultFuture<String> resultFuture) {
        String s = tweet.tweetText;
    }
}
