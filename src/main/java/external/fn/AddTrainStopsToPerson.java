package external.fn;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Arrays;
import java.util.List;

/*
function that takes xml file path, and sequency of person id, train stop from, and train stop to
where train stop from represents where the passenger with person id took the train from and to is where they got off
 */
public class AddTrainStopsToPerson {
    public void addTrainStops(String path, List<Integer> personID, List<Integer> from, List<Integer> to, String outputPath) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        builder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        Document doc = builder.build(new File(path));

        Element rootNode = doc.getRootElement();
        List<Element> listPersonNode = rootNode.getChildren("person");
        for (Element person: listPersonNode) {
            String id = person.getAttribute("id").getValue();
            int index;
            if ((index = personID.indexOf(Integer.valueOf(id))) != -1) {
                /* person.addContent(new Element("attributes")
                        .addContent(new Element("attribute")
                                .setAttribute("name", "train_from" )
                                .setAttribute("class", "java.lang.Integer")
                                .setText(Integer.toString(from.get(index))))
                        .addContent(new Element("attribute")
                                .setAttribute("name", "train_to" )
                                .setAttribute("class", "java.lang.Integer")
                                .setText(Integer.toString(to.get(index)))));
                 */
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
