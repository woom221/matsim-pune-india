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
import java.util.Arrays;
import java.util.List;

public class EditConfig {
    public void editConfig(List<String[]> record, String path, String outputPath, Double constant, Double utility) throws IOException, JDOMException {
        String tmp = "";
        for (int i = 1; i < record.size(); i++) {
            String stationInfo = record.get(i)[0];
            tmp = tmp.concat(",");
            tmp = tmp.concat("train" + stationInfo);
        }
        SAXBuilder builder = new SAXBuilder();
        builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        Document doc = builder.build(new File(path));
        Element rootNode = doc.getRootElement();
        List<Element> listModuleNode = rootNode.getChildren("module");
        for (Element module: listModuleNode) {
            String moduleName = module.getAttribute("name").getValue();
            if ("planscalcroute".equals(moduleName.trim())) {
                String modeList = module.getChild("param").getAttribute("name").getValue();
                if ("networkModes".equals(modeList.trim())) {
                    String currentList = module.getChild("param").getAttribute("value").getValue();
                    module.getChild("param").setAttribute("value", currentList.concat(tmp));
                }
            }
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
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        try (FileOutputStream output =
                     new FileOutputStream(outputPath)) {
            xmlOutput.output(doc, output);
        }
    }
}
