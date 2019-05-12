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


import java.util.ArrayList;
import java.util.Arrays;
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
        config.addSharedModules("flair");
        config.addSharedModules("onnx");
        config.addSharedModules("caffe2");

        Jep theJep = config.createJep();


        // jep

        //theJep.runScript("src/python/new_test.py")

        //theJep.eval("from new_pred import NewTest");
        //theJep.eval("y=class_test()");
        //Object result = theJep.getValue("y");
        //System.out.println(result.toString());
        //theJep.eval("s = NewTest()");
        theJep.eval("from simple_onnx import run_shit");
        theJep.set("text", s.tweetText);
        Object result = theJep.getValue("run_shit()");
        String res = result.toString();

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
            //System.out.println(s);
            String text;
            String lang = "None";
            String user = "Unknown";
            boolean tweetGood = false;
            String createdAt = "";
            try {
                JsonNode jsonNode = jsonParser.readValue(s, JsonNode.class);
                 text = getField(jsonNode, "text");
                 if (jsonNode.has("user") && jsonNode.has("lang")) {
                     lang = jsonNode.get("lang").textValue();
                     user = jsonNode.get("user").get("id").textValue();

                 }
                String [] relavantHashTags = {"GOT", "GOT8", "BattleOfWinterfell", "GameOfThrones"};

                 if(jsonNode.has("entities")){
                 if(jsonNode.get("entities").get("hashtags").isArray()){
                     for (JsonNode theNode: jsonNode.get("entities").get("hashtags")){

                         if(Arrays.stream(relavantHashTags).anyMatch(theNode.get("text").textValue().trim()::equals)){

                             tweetGood = true;
                         }
                     }

                 }}

                 if(jsonNode.has("created_at")){
                     createdAt = jsonNode.get("created_at").toString();
                 }





            }
            catch(JsonParseException a){
                text = "None ";


            }

            //String lang = getField(jsonNode.get("user"), "lang");




            return new TweetData(text, lang, user,tweetGood,
                    2, 3, 4, createdAt);
        }
}