package com.vohraharsh.httpserver.controller;

import com.vohraharsh.httpserver.model.RequestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyHttpServerController {

    private static final Logger logger = LoggerFactory.getLogger(MyHttpServerController.class);

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        logger.info("Received a Get Request");
        return ResponseEntity.ok("Hello User");
    }

    @PostMapping("/hello")
    public ResponseEntity<String> handlePost(@RequestBody RequestData requestData) {
        logger.info("Received a Post Request with Data: Name={}, Message={}", requestData.getName(), requestData.getMessage());
        try {
            // Simulate processing delay
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            logger.error("Error during Process", e);
        }

        // Construct response message based on received data
        String responseMessage = "Received a Post Request from " + requestData.getName() + ": " + requestData.getMessage();
        return ResponseEntity.ok(responseMessage);
    }
}
