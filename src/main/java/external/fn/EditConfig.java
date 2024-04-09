package external.fn;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class EditConfig {
    /**
     * Import and modify the config XML file to incorporate with the data from record (train stop and fare data)
     *
     * @param record information of train stops and fares
     * @param path path to import the original xml file
     * @param outputPath path to save the modified xml file
     * @param constant constant corrective factor for every train stop
     * @param utility utility score for every train stop
     * @throws IOException IOException Error
     * @throws JDOMException ERROR generated from handling JDOM
     */
    public void editConfig(List<String[]> record, String path, String outputPath, Double constant, Double utility) throws IOException, JDOMException {
        // gather all the train information as a single text (to be used for plancalcroute)
        String tmp = "";
        for (int i = 1; i < record.size(); i++) {
            String stationInfo = record.get(i)[0];
            tmp = tmp.concat(",");
            tmp = tmp.concat("train" + stationInfo);
        }

        // JDOM XML modifier routine
        SAXBuilder builder = new SAXBuilder();
        // do not interfere the format
        builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        Document doc = builder.build(new File(path));
        Element rootNode = doc.getRootElement();

        // find the module node
        List<Element> listModuleNode = rootNode.getChildren("module");

        // modify the module with conditions
        for (Element module: listModuleNode) {
            String moduleName = module.getAttribute("name").getValue();

            // modify planscalcroute to include all the train stops
            if ("planscalcroute".equals(moduleName.trim())) {
                String modeList = module.getChild("param").getAttribute("name").getValue();
                if ("networkModes".equals(modeList.trim())) {
                    String currentList = module.getChild("param").getAttribute("value").getValue();
                    module.getChild("param").setAttribute("value", currentList.concat(tmp));
                }
            }

            // modify planCalcScore to include individual stop information with corresponding fares
            if ("planCalcScore".equals(moduleName.trim())) {
                for (int j = 1; j < record.size(); j++) {
                    module.getChild("parameterset").addContent(new Element("parameterset")
                            .setAttribute("type", "modeParams")
                            .addContent(new Element("param")
                                    .setAttribute("name", "constant")
                                    .setAttribute("value", constant.toString()))
                            .addContent(new Element("param")
                                    .setAttribute("name", "marginalUtilityOfTraveling_util_hr")
                                    .setAttribute("value", utility.toString()))
                            .addContent(new Element("param")
                                    .setAttribute("name", "mode")
                                    .setAttribute("value", "train" + record.get(j)[0]))
                            .addContent(new Element("param")
                                    .setAttribute("name", "dailyMonetaryConstant")
                                    .setAttribute("value", record.get(j)[1])));
                }
            }
        }

        // output the xml to the provided path
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        try (FileOutputStream output =
                     new FileOutputStream(outputPath)) {
            xmlOutput.output(doc, output);
        }
    }
}
