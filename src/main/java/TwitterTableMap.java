import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

public class TwitterTableMap implements FlatMapFunction<TweetData, Tuple3<String, String, String >> {
    @Override
    public void flatMap(TweetData tweetData, Collector<Tuple3<String, String, String>> out) throws Exception {


        Tuple3<String, String, String> fullTup = new Tuple3<>("s", "s", "s");

        out.collect(fullTup);
        int i = 0;
        //for (Tuple2<String, String> tup: tweetData.entsLabels){
            //Tuple3<String, String, String> fullTup = new Tuple3<>(tup.f0, tup.f1, tweetData.sentiment);
            //i++;
        //}
    }
}
