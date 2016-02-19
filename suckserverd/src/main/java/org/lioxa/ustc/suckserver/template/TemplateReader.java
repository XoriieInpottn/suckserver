package org.lioxa.ustc.suckserver.template;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The {@link TemplateReader} is used to read XML formatted template from
 * string, file or input stream.<br/>
 * The template must have ONLY one root node: "task".<br/>
 * Any tags in "task" aim to define the crawl procedures include:
 * <ul>
 * <li>create data <b>table</b></li>
 * <li><b>load</b> web page</li>
 * <li><b>select</b> DOM element</li>
 * <li><b>match</b> text content</li>
 * <li>save as temporary <b>var</b>iable</li>
 * <li><b>save</b> to database</li>
 * <li>...</li>
 * </ul>
 *
 * @author xi
 * @since Jan 4, 2016
 */
public class TemplateReader {

    TemplateNode tplRoot;

    /**
     * Read XML template from string.
     *
     * @param xmlStr
     *            The XML string.
     * @return The root node of the template tree.
     * @throws TemplateException
     *             There are syntax errors in the XML source.
     */
    public TemplateNode read(String xmlStr) throws TemplateException {
        InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
        return this.read(is);
    }

    /**
     * Read XML template from file.
     *
     * @param xmlFile
     *            The XML file.
     * @return The root node of the template tree.
     * @throws TemplateException
     *             There are syntax errors in the XML source.
     */
    public TemplateNode read(File xmlFile) throws TemplateException {
        InputStream is;
        try {
            is = new FileInputStream(xmlFile);
        } catch (FileNotFoundException e) {
            String msg = String.format("Failed to read XML file \"%s\".", xmlFile.getAbsolutePath());
            throw new TemplateException(msg, e);
        }
        return this.read(is);
    }

    /**
     * Read XML template from input stream.
     *
     * @param is
     *            The input stream.
     * @return The root node of the template tree.
     * @throws TemplateException
     *             There are syntax errors in the XML source.
     */
    public TemplateNode read(InputStream is) throws TemplateException {
        SAXParser parser;
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            parser = parserFactory.newSAXParser();
            // DocumentBuilderFactory builderFactory =
            // DocumentBuilderFactory.newInstance();
            // DocumentBuilder builder = builderFactory.newDocumentBuilder();
            // this.doc = builder.newDocument();
        } catch (ParserConfigurationException | SAXException e) {
            String msg = "Failed to create parser for the given template.";
            throw new TemplateException(msg, e);
        }
        DefaultHandler handler = new DefaultHandler() {

            Stack<TemplateNode> stack = new Stack<>();
            Locator locator;

            @Override
            public void setDocumentLocator(Locator locator) {
                // Save the locator, so that it can be used later for line
                // tracking when traversing nodes.
                this.locator = locator;
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                TemplateNode node = new TemplateNode();
                this.stack.push(node);
                node.setName(qName);
                node.setLineNumber(this.locator.getLineNumber());
                node.setColumnNumber(this.locator.getColumnNumber());
                Map<String, String> params = node.getParams();
                for (int i = 0; i < attributes.getLength(); i++) {
                    params.put(attributes.getQName(i), attributes.getValue(i));
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) {
                TemplateNode node = this.stack.pop();
                if (this.stack.isEmpty()) {
                    //
                    // root element
                    TemplateReader.this.tplRoot = node;
                } else {
                    //
                    // not root element, the peek of the stack is parent
                    TemplateNode parentElem = this.stack.peek();
                    parentElem.getChildren().add(node);
                }
            }

            @Override
            public void characters(char ch[], int start, int length) {
            }

        };
        try {
            parser.parse(is, handler);
        } catch (SAXException | IOException e) {
            String msg = "Failed to parse template.";
            throw new TemplateException(msg, e);
        }
        return this.tplRoot;
    }

}