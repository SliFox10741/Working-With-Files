package org.example;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class XMLReader {
    public  void xml() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new File("shop.xml"));
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        try {
            XPathExpression xp = XPathFactory.newInstance().newXPath().compile("//config/load/enabled");
            Boolean.parseBoolean(xp.evaluate(doc));
            xp = XPathFactory.newInstance().newXPath().compile("//config/load/fileName");
            xp.evaluate(doc);
            xp = XPathFactory.newInstance().newXPath().compile("//config/load/format");
            xp.evaluate(doc);

            xp = XPathFactory.newInstance().newXPath().compile("//config/save/enabled");
            Boolean.parseBoolean(xp.evaluate(doc));
            xp = XPathFactory.newInstance().newXPath().compile("//config/save/fileName");
            xp.evaluate(doc);
            xp = XPathFactory.newInstance().newXPath().compile("//config/save/format");
            xp.evaluate(doc);

            xp = XPathFactory.newInstance().newXPath().compile("//config/log/enabled");
            Boolean.parseBoolean(xp.evaluate(doc));
            xp = XPathFactory.newInstance().newXPath().compile("//config/log/fileName");
            xp.evaluate(doc);

            System.out.println("XML WORK");

        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }
}
