
import org.apache.commons.io.IOUtils;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.flink.twitter.shaded.com.google.common.util.concurrent.FutureCallback;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.asynchttpclient.AsyncHttpClient;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;
// https://ci.apache.org/projects/flink/flink-docs-release-1.8/dev/stream/operators/asyncio.html Docs
public class dockerAsync extends RichAsyncFunction<TweetData, TweetData > {
    private transient ExecutorService executorService;

    @Override
    public void asyncInvoke(TweetData tweet, ResultFuture<TweetData> resultFuture) throws java.io.IOException {
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        httpClient.start();
        String jsonString ="{\"message\":\"" + tweet.tweetText + "\"}";
        HttpPost request = new HttpPost("http://localhost:5000/api/v1/analyzeSentiment");
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        StringEntity params = new StringEntity(jsonString, ContentType.APPLICATION_FORM_URLENCODED);
        //request.addHeader("content-type", "application/x-www-form-urlencoded");
        request.setEntity(params);

        //issue the asynchronous request, receive a future for result
        final Future<HttpResponse> result = httpClient.execute(request, null);

        // set the callback to be executed once the request by the client is complete
        // the callback simply forwards the result to the result future
        CompletableFuture.supplyAsync(new Supplier<String>(){

            @Override
            public String get() {
                try {

                    String json = IOUtils.toString(result.get().getEntity().getContent());
                    System.out.println(json);
                    return result.get().getEntity().toString();
                } catch (InterruptedException | ExecutionException | IOException e) {
                    // Normally handled explicitly.
                    return null;
                }
            }
        }).thenAccept( (String sentResult) -> {
            tweet.sentiment = sentResult;
            resultFuture.complete(Collections.singleton(tweet));
        });
    }

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







