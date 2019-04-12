import jep.Jep;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;

import java.util.Iterator;

public class SentimentMap implements RichMapFunction {
    private Jep j;
    public void open(Configuration c)
    {
        try {
            j = new jep.SharedInterpreter();
            j.eval("from flair.data import Sentence");
            j.eval("from flair.models import TextClassifier");
            j.eval("classifier = TextClassifier.load('en-sentiment')");
        }
        catch (jep.JepException e) {
            e.printStackTrace();
        }
    }
    public Iterator<Tuple3<String, String, String>> map(TweetData tweet){
        try {
            j.set("text", tweet.tweetText);
            j.eval("s=Sentence(text)");
            j.eval("result = classifier.predict(s)");

        }
        catch (jep.JepException e){
            e.printStackTrace();

        }







    }
}
