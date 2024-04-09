package external.fn.test;

import external.fn.AddTrainStopsToPerson;
import external.fn.EditConfig;
import external.fn.ReadCSV;
import org.jdom2.JDOMException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class editConfigModeListTest {
    @Test
    public void testEditConfigModeListTest() throws Exception {
        String path = "scenarios/example/editConfigTestPartOne.xml";
        String outputPath = "scenarios/example/editConfigTestPartOne.xml";
        File testFile = new File("scenarios/example/data/fare.csv");
        ReadCSV readCSV = new ReadCSV();
        EditConfig editConfig = new EditConfig();
        List<String[]> record = readCSV.read(testFile);
        editConfig.editConfig(record, path, outputPath,0.0, 0.0);
    }
}
