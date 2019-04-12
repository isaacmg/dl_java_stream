import jep.Jep;
import jep.JepConfig;
import org.apache.flink.api.common.functions.RichMapFunction;
import jep.SharedInterpreter;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import scala.util.parsing.json.JSONObject;

import java.util.List;

public class flairMap extends RichMapFunction<TweetData, TweetData> {
    private SharedInterpreter j;

    @Override
    public void open(Configuration c)
    {
        try {
            j = new jep.SharedInterpreter();
            j.eval("from flair.data import Sentence");
            j.eval("from flair.models import SequenceTagger");
            j.eval("model = SequenceTagger.load('ner')");
        }
        catch (jep.JepException e) {
            e.printStackTrace();
        }
    }


    public TweetData map(TweetData tweet) throws jep.JepException{

        //tweetText = tweetText.replaceAll("\\p{C}", "");
        tweet.tweetText = tweet.tweetText.replaceAll("[^A-Za-z0-9]", " ");
        System.out.println(tweet.tweetText);
        try {
            j.set("text", tweet.tweetText);
            j.eval("s=Sentence(text)");
            j.eval("model.predict(s)");
            j.eval("sent = s.to_dict(tag_type='ner')");
            j.eval("result_arr = []");
            j.eval("for entity in sent[\"entities\"]:" + "\n"+
                    "   result_arr.append((entity[\"text\"], entity[\"type\"]))");
            Object tweetList= j.getValue("result_arr");
            // List of tuples (ent, labe)
            List tweetEnts = (List)tweetList;
            tweet.entsLabels = tweetEnts;
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

