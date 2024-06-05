package com.example.demo;

import com.example.demo.parralelism.poc.QueueClient1;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * DemoController
 */
@Slf4j
@RestController
public class DemoController {

    public DemoController(Environment env) {
        QueueClient1.startPusher(env.getProperty("queue.client.file"));
    }


    @GetMapping
    public Mono<InnerDemoController> hello(@RequestParam(defaultValue = "0") Integer num) {
        if (num > 0)
            return Mono.error(new Exception("some exception"));
        return Mono.just(new InnerDemoController("value"));

    }

    @GetMapping(value = "/receive", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String receivDataNew(@RequestBody InnerDemoController controller) {

        log.info("data received: {}", controller);
        return "Data received";
    }

    @PostMapping(value = "/receive", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String receivDataPost(@RequestBody InnerDemoController controller) {

        log.info("data received: {}", controller);
        return "Data received";
    }

    // @PostMapping(value = "/receive", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // public String receivData(InnerDemoController data)
    // throws SAXException, IOException, ParserConfigurationException, TransformerException {

    // log.info("data received: body {}", data.getData());
    // String request = data.getData();
    // DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    // InputSource src = new InputSource();
    // src.setCharacterStream(new StringReader(request));
    // Document doc = builder.parse(src);
    // org.w3c.dom.Element element = doc.createElement("status");
    // element.setTextContent("0");
    // doc.getDocumentElement().appendChild(element);
    // TransformerFactory transformerFactory = TransformerFactory.newInstance();
    // Transformer transformer = transformerFactory.newTransformer();
    // DOMSource source = new DOMSource(doc);
    // StreamResult result = new StreamResult(new StringWriter());
    // transformer.transform(source, result);

    // // Return the XML string
    // return result.getWriter().toString();
    // }

    /**
     * InnerDemoController
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerDemoController {
        String data;

    }
}
