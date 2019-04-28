import jep.Jep;
import jep.JepConfig;
import org.apache.flink.api.common.functions.RichMapFunction;
import jep.SharedInterpreter;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import scala.util.parsing.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class flairMap extends RichMapFunction<TweetData, TweetData> {
    private SharedInterpreter j;
    private transient ObjectMapper jsonParser;

    private String getField(JsonNode json, String field){
        if(json.has(field)){
            json.get(field);
            return json.get(field).textValue();
        }
        return "";

    }

    @Override
    public void open(Configuration c)
    {
        if(jsonParser == null) {
            jsonParser = new ObjectMapper();
        }
        try {
            j = new jep.SharedInterpreter();
            j.eval("from flair.data import Sentence");
            j.eval("from flair.models import SequenceTagger");
            j.eval("import json");
            j.eval("model = SequenceTagger.load('ner')");

        }
        catch (jep.JepException e) {
            e.printStackTrace();
        }
    }


    public TweetData map(TweetData tweet) throws jep.JepException, IOException {

        //tweetText = tweetText.replaceAll("\\p{C}", "");
        tweet.tweetText = tweet.tweetText.replaceAll("[^A-Za-z0-9]", " ");
        //System.out.println(tweet.tweetText);
        try {
            j.set("text", tweet.tweetText);
            j.eval("s=Sentence(text)");
            j.eval("model.predict(s)");
            j.eval("result = s.to_dict(tag_type='ner')");

            Object tweetList= j.getValue("json.dumps(result)");

            JsonNode jsonNode = jsonParser.readValue(tweetList.toString(), JsonNode.class);
            getField(jsonNode, "entities");
            tweet.entsLabels = new ArrayList<>();
            tweet.sentiment = "None";
            return tweet;


        }


        catch(jep.JepException e){
            e.printStackTrace();
            throw e;
            //theJep.close();

        }

        //return "";
    }


}

