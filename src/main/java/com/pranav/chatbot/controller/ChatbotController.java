package com.pranav.chatbot.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ChatbotController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String rasaUrl = "http://localhost:5005/webhooks/rest/webhook";

    @PostMapping("/chat")
    public RasaResponse sendMessage(@RequestBody UserMessage userMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{\"sender\": \"user\", \"message\": \"" + userMessage.getMessage() + "\"}";
        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        RasaResponse[] responses = restTemplate.postForObject(rasaUrl, request, RasaResponse[].class);
        return responses != null && responses.length > 0 ? responses[0] : new RasaResponse("Sorry, no response.");
    }

    static class UserMessage {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    static class RasaResponse {
        private String text;

        public RasaResponse() {}

        public RasaResponse(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
