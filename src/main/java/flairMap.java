import jep.Jep;
import jep.JepConfig;
import org.apache.flink.api.common.functions.RichMapFunction;
import jep.SharedInterpreter;
import org.apache.flink.configuration.Configuration;

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
        try {
            j.set("text", "Some basic shit for the thing");
            j.eval("s=Sentence(text)");
            Object result = j.getValue("model.predict(s)");
            System.out.println(result.toString());
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

