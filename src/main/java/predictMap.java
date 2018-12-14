import jep.Jep;
import jep.JepConfig;
import jep.SharedInterpreter;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonParseException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.util.Collector;


import java.util.StringTokenizer;

public class predictMap extends RichMapFunction<TweetData, String>{
    @Override
    public String map(TweetData s) throws jep.JepException {
        //JepConfig j = new JepConfig();

        JepConfig config = new JepConfig();
        config.addIncludePaths("src/python");
        config.addSharedModules("numpy");
        config.addSharedModules("allennlp");
        config.addSharedModules("scipy");
        config.addSharedModules("torch");
        Jep theJep = config.createJep();


        // jep

        //theJep.runScript("src/python/new_test.py")

        theJep.eval("from new_pred import NewTest");
        //theJep.eval("y=class_test()");
        //Object result = theJep.getValue("y");
        //System.out.println(result.toString());


        theJep.eval("s = NewTest()");
        theJep.set("text", s.tweetText);
        Object result = theJep.getValue("s.run(text)");
        String res = result.toString();
        System.out.print(res);
        theJep.close();
        return "hi";
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
            System.out.println(s);
            String text;
            try {
                JsonNode jsonNode = jsonParser.readValue(s, JsonNode.class);
                 text = getField(jsonNode, "text");
            }
            catch(JsonParseException a){
                text = "None ";

            }

            //String lang = getField(jsonNode.get("user"), "lang");




            return new TweetData(text, "", "",
                    2, 3, 4);
        }
}