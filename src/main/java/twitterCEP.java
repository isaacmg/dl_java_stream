import jep.Jep;
import jep.JepConfig;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.Tumble;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.sinks.CsvTableSink;
import org.apache.flink.table.sinks.TableSink;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;


public class twitterCEP {
    public static void main(String args[]) throws IOException, Exception {
        //ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        //File configOnDisk = new File("myFile.properties");
        //Files.copy(classloader.getResourceAsStream("myFile.properties"), configOnDisk.toPath(), StandardCopyOption.REPLACE_EXISTING);


        final ParameterTool params = ParameterTool.fromPropertiesFile("myFile.properties");
        TwitterSource twitterConnect = new TwitterSource(params.getProperties());
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> twitterStream = env.addSource(twitterConnect);
        DataStream<TweetData> sentimentStream= twitterStream
                .map(new BasicTweet()).filter(new FilterFunction<TweetData>() {
            @Override
            public boolean filter(TweetData tweetData) throws Exception {
                return tweetData.language.equals("en") && tweetData.properHashtag;
            }
        });
        DataStream<TweetData> tweetStream =  twitterStream.map(new BasicTweet());
        //tweetStream = AsyncDataStream.unorderedWait( tweetStream, new dockerAsync(), 1000, TimeUnit.MILLISECONDS, 100);
        DataStream<Tuple4<String, String, String, Integer>> secondTweet = sentimentStream.map(new tweetTupleMap());
        sentimentStream.print();
        secondTweet.timeWindowAll(Time.seconds(20)).sum(3);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        secondTweet.print();
        Table table = tableEnv.fromDataStream(secondTweet, "Elements, Sentiment, Time, Number");
        TableSink sink = new CsvTableSink("new",  "|");
        String[] fieldNames = {"a", "b", "c", "d"};
        TypeInformation[] fieldTypes = {Types.STRING, Types.STRING, Types.STRING, Types.INT};
        tableEnv.registerTableSink("CsvSinkTable", fieldNames, fieldTypes, sink);
        table.insertInto("CsvSinkTable");

        // res = Implement mapFunction -> Tuple3<String, List<String>, String> timeStamp as String, entities, sentiment
        // res.keyby(0).window(
        //flatMap tweetStream -> Tuple<String, String, String> timeStamp

        //tweetStream.print();
       // sentimentStream.writeAsCsv("file.csv");

        //StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        //Table table = tableEnv.fromDataStream(sentimentStream, "rawText, sentiment, timeStamp");

        //tableEnv.sqlQuery("SELECT * FROM ");
        //tableEnv.registerDataStream("sentimentTable", sentimentStream, "rawText, sentiment, timeStamp");
        //tableEnv.
        env.execute("Flair Filter");
    }
}


