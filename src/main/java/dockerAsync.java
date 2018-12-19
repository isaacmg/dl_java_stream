import com.sun.tools.internal.ws.wsdl.document.http.HTTPUrlEncoded;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class dockerAsync extends RichAsyncFunction<TweetData, String > {
    @Override
    public void asyncInvoke(TweetData tweet, ResultFuture<String> resultFuture) throws java.io.IOException {
        String s = tweet.tweetText;
        URL dockerModelURL = new URL("http://docker_container_url.com");
        HttpURLConnection conn = (HttpURLConnection) dockerModelURL.openConnection();
        conn.setRequestMethod("GET");






    }
}
