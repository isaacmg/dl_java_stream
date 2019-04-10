import jep.Jep;
import jep.JepConfig;
import org.apache.flink.api.common.functions.RichMapFunction;
import jep.SharedInterpreter;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import scala.util.parsing.json.JSONObject;

public class flairMap extends RichMapFunction<TweetData, String> {
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


    public String map(TweetData tweet) throws jep.JepException{
        String tweetText = tweet.tweetText;
        //tweetText = tweetText.replaceAll("\\p{C}", "");
        tweetText = tweetText.replaceAll("[^A-Za-z0-9]", " ");
        System.out.println(tweetText);
        try {
            j.set("text", tweetText);
            j.eval("s=Sentence(text)");
            j.eval("model.predict(s)");
            j.eval("sent = s.to_dict(tag_type='ner')");
            j.eval("result_arr = []");
            j.eval("for entity in sent:" + "\n"+
                    "   result_arr.append((entity[\"text\"], entity[\"type\"]))");
            Object result = j.getValue("result_arr");
            return result.toString();


        }


        catch(jep.JepException e){
            e.printStackTrace();
            throw e;
            //theJep.close();

        }

        //return "";
    }


}

