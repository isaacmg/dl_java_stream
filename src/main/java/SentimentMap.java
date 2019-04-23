import jep.Jep;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SentimentMap extends RichMapFunction<TweetData, List<Tuple3<String, String, String>>> {
    private Jep j;
    public void open(Configuration c)
    {
        try {
            j = new jep.SharedInterpreter();
            j.eval("from flair.data import Sentence");
            j.eval("from flair.models import TextClassifier");
            j.eval("classifier = TextClassifier.load_from_file('/Users/isaac/.flair/models/imdb.pt')");
        }
        catch (jep.JepException e) {
            e.printStackTrace();
        }
    }
    public List<Tuple3<String, String, String>> map(TweetData tweet){
        try {
            j.set("text", tweet.tweetText);
            j.eval("s=Sentence(text)");
            j.eval("result = classifier.predict(s)");
            Object o = j.getValue("result");
            System.out.println(o.toString());
            ArrayList<Tuple3<String, String, String>> s = new ArrayList<>();
            for (Tuple2<String, String> tup: tweet.entsLabels){
                Tuple3<String, String, String> fullTup = new Tuple3<>(tup.f0, tup.f1, o.toString());
                s.add(fullTup);

            }
            return s ;

        }
        catch (jep.JepException e){
            e.printStackTrace();
            return null ;

        }







    }
}
