package external.fn.test;

import external.fn.AddTrainStopsToPerson;
import org.jdom2.JDOMException;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class AddTrainStopstoPersonTest {
    @Test
    public void testAddTrainStopPersonTest() throws IOException, JDOMException {
        String path = "scenarios/example/planTest.xml";
        String outputPath = "scenarios/example/planTestModified.xml";
        List<Integer> id = new ArrayList<>();
        id.add(1);
        List<Integer> from = new ArrayList<>();
        from.add(50);
        List<Integer> to = new ArrayList<>();
        to.add(30);
        AddTrainStopsToPerson addTrainStopsToPerson = new AddTrainStopsToPerson();
        addTrainStopsToPerson.addTrainStops(path, id, from, to, outputPath);
    }
}
