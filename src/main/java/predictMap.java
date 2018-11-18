import jep.Jep;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.util.Collector;


import java.util.StringTokenizer;

public class predictMap extends RichMapFunction<String, String>{
    @Override
    public String map(String s) throws jep.JepException {
        Jep theJep = new Jep();


        return "none";
    }
}


class BasicTuple implements MapFunction<String, TweetData> {
        private transient ObjectMapper jsonParser;
        public TweetData map(String s) throws Exception {
            if(jsonParser == null) {
                    jsonParser = new ObjectMapper();
                }
                return new TweetData("", "", "", 2, 3, 4);
        }
}