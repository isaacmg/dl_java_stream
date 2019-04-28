
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.flink.twitter.shaded.com.google.common.util.concurrent.FutureCallback;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;
// https://ci.apache.org/projects/flink/flink-docs-release-1.8/dev/stream/operators/asyncio.html Docs
public class dockerAsync extends RichAsyncFunction<TweetData, String > {
    private transient ExecutorService executorService;

    @Override
    public void asyncInvoke(TweetData tweet, ResultFuture<String> resultFuture) throws java.io.IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        String jsonString ="{\"message\":\"Inoffensive and unremarkable.\" }";
        HttpPost request = new HttpPost("http://localhost:5000/api/v1/analyzeSentiment");
        StringEntity params = new StringEntity(jsonString);
        request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.setEntity(params);



    }

       // CompletableFuture.supplyAsync(Supplier)
        // curl --request POSiT \
        //  --url http://localhost:5000/api/v1/analyzeSentiment \
        //  --header 'content-type: application/json' \
        //  --data '{"message":"Inoffensive and unremarkable."
        //}'
        //client.execute(new HttpGet(new URI("http://myservice/" + key)), new FutureCallback<HttpResponse>() {
    //             @Override
    //                public void completed(final HttpResponse response) {
    //                    System.out.println("completed successfully");
    //                    Item item = gson.fromJson(EntityUtils.toString(response.getEntity), Item.class);
    //                    resultFuture.complete(Collections.singleton(item));
    //                }
    //        });
    //    });





    }

