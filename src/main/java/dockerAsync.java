import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;

public class dockerAsync extends RichAsyncFunction<BasicTweet, String > {
    @Override
    public void asyncInvoke(BasicTweet basicTweet, ResultFuture<String> resultFuture) {
        
    }
}
