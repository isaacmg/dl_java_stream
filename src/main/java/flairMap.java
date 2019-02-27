import jep.Jep;
import jep.JepConfig;
import org.apache.flink.api.common.functions.RichMapFunction;
import jep.SharedInterpreter;

public class flairMap extends RichMapFunction<TweetData, String> {

    public String map(TweetData tweet) throws jep.JepException{
        try {
            JepConfig config = new JepConfig();
            config.addIncludePaths("src/python");
            config.addSharedModules("numpy");
            config.addSharedModules("gensim");
            config.addSharedModules("torch");
            config.addSharedModules("sklearn");
            config.addSharedModules("flair");
            SharedInterpreter theJep = new jep.SharedInterpreter();
            theJep.eval("from flair.data import Sentence");
            theJep.eval("from flair.models import SequenceTagger");
            // Need to do more preprocessing on tweet.tweetText
            theJep.set("text", "Some basic shit for the thing");
            theJep.eval("s=Sentence(text)");
            theJep.eval("model = SequenceTagger.load('ner')");
            Object result = theJep.getValue("model.predict(s)");
            System.out.println(result.toString());
            theJep.close();
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

