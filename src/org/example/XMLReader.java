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
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    Document doc;

    public XMLReader() {
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new File("shop.xml"));
            doc.getDocumentElement().normalize();
            System.out.println("XML WORK");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public File loadXML () throws XPathExpressionException, IOException {
        File loadFile;
        XPathExpression xp = XPathFactory.newInstance().newXPath().compile("//config/load/enabled");
        boolean value = Boolean.parseBoolean(xp.evaluate(doc));

        if(value) {
            xp = XPathFactory.newInstance().newXPath().compile("//config/load/fileName");
            String fileName = xp.evaluate(doc);

            xp = XPathFactory.newInstance().newXPath().compile("//config/load/format");
            String format = xp.evaluate(doc);
            if (fileName.contains(format)) {
                loadFile = new File(fileName);
                loadFile.createNewFile();
                return loadFile;
            }
            else {
                throw new RuntimeException("Формат файла не соответсвует переданному значению");
            }
        }
        return null;
    }

    public File saveXML () throws XPathExpressionException, IOException {
        File saveFile;

        XPathExpression xp = XPathFactory.newInstance().newXPath().compile("//config/save/enabled");
        boolean value = Boolean.parseBoolean(xp.evaluate(doc));

        if(value) {
            xp = XPathFactory.newInstance().newXPath().compile("//config/save/fileName");
            String fileName = xp.evaluate(doc);

            xp = XPathFactory.newInstance().newXPath().compile("//config/save/format");
            String format = xp.evaluate(doc);

            if (fileName.contains(format)) {
                saveFile = new File(fileName);
                saveFile.createNewFile();
                return saveFile;
            }
            else {
                throw new RuntimeException("Формат файла не соответсвует переданному значению");
            }
        }
        return null;
    }

    public File logXML () throws XPathExpressionException, IOException {
        File logFile;

        XPathExpression xp = XPathFactory.newInstance().newXPath().compile("//config/log/enabled");
        boolean value = Boolean.parseBoolean(xp.evaluate(doc));

        if(value) {
            xp = XPathFactory.newInstance().newXPath().compile("//config/log/fileName");
            String fileName = xp.evaluate(doc);
            logFile = new File(fileName);
            logFile.createNewFile();
            return logFile;
        }
        return null;
    }
}
