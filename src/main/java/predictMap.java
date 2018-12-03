import jep.Jep;
import jep.JepConfig;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.util.Collector;


import java.util.StringTokenizer;

public class predictMap extends RichMapFunction<TweetData, String>{
    @Override
    public String map(TweetData s) throws jep.JepException {
        //JepConfig j = new JepConfig();
        //j.addIncludePaths("");
        Jep theJep = new Jep();
        theJep.runScript("src/python/new_test.py");
        theJep.eval("import NewTest");
        return "none";
    }
}


class BasicTweet implements MapFunction<String, TweetData> {
        private transient ObjectMapper jsonParser;

        private String getField(JsonNode json, String field){
            if(json.has(field)){
                return json.get(field).textValue();
            }
            return "";

        }

        public TweetData map(String s) throws Exception {
            if(jsonParser == null) {
                    jsonParser = new ObjectMapper();
                }

            JsonNode jsonNode = jsonParser.readValue(s, JsonNode.class);
            String text = getField(jsonNode, "text");
            //String lang = getField(jsonNode.get("user"), "lang");




            return new TweetData(text, "", "",
                    2, 3, 4);
        }
}