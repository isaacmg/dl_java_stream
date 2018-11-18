import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class predictMap extends RichMapFunction<String, String>{
    @Override
    public String map(String s) {
        System.out.println(s);
        return s;
    }
}
