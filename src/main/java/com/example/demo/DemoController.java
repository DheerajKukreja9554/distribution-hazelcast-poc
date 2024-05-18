package com.example.demo;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.swing.text.html.parser.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import reactor.core.publisher.Mono;

/**
 * DemoController
 */
@Slf4j
@RestController
public class DemoController {


    @GetMapping
    public Mono<String> hello(@RequestParam(defaultValue = "0") Integer num) {
        if (num > 0)
            return Mono.error(new Exception("some exception"));
        return Mono.just("hello");

    }

    @GetMapping("/receive")
    public String receivData(@RequestParam(defaultValue = "0") Integer num) {

        log.info("data received");
        return "Data received";
    }

    @PostMapping(value = "/receive", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String receivData(InnerDemoController data)
            throws SAXException, IOException, ParserConfigurationException, TransformerException {

        log.info("data received: body {}", data.getData());
        String request = data.getData();
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource src = new InputSource();
        src.setCharacterStream(new StringReader(request));
        Document doc = builder.parse(src);
        org.w3c.dom.Element element = doc.createElement("status");
        element.setTextContent("0");
        doc.getDocumentElement().appendChild(element);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new StringWriter());
        transformer.transform(source, result);

        // Return the XML string
        return result.getWriter().toString();
    }

    /**
     * InnerDemoController
     */
    @Data
    public class InnerDemoController {
        String data;

    }
}
