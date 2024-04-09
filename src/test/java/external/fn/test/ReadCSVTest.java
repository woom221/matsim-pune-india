package external.fn.test;

import external.fn.ReadCSV;
import org.junit.Assert;
import org.junit.Test;
import org.matsim.utils.eventsfilecomparison.EventsFileComparator;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadCSVTest {
    @Test
    public void testReadCSV() {
        File testFile = new File("scenarios/example/data/test.csv");
        ReadCSV readCSV = new ReadCSV();
        try {
            List<String[]> result = readCSV.read(testFile);
            String test_one = "98-98";
            String test_three = "CAD";
            Assert.assertEquals(result.get(1)[0], test_one);
            Assert.assertEquals(result.get(1)[2], test_three);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
