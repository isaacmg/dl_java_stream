import jep.Jep;
import jep.JepConfig;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class twitterCEP {
    public static void main(String args[]) throws IOException, Exception {
       // ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        //File configOnDisk = new File("myFile.properties");
        //Files.copy(classloader.getResourceAsStream("myFile.properties"), configOnDisk.toPath(), StandardCopyOption.REPLACE_EXISTING);


        final ParameterTool params = ParameterTool.fromPropertiesFile("myFile.properties");
        TwitterSource twitterConnect = new TwitterSource(params.getProperties());
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> twitterStream = env.addSource(twitterConnect);
        twitterStream.map(new BasicTweet()).map(new flairMap());


        env.execute("Window WordCount");


    }
}

