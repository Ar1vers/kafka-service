package org.example.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Car;
import org.example.service.CarXmlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CarConsumer {
    @Value("${topic.response.name}")
    private String responseTopic;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarXmlService carXmlService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(CarConsumer.class);

    @KafkaListener(topics = "${topic.name}")
    public void consumeMessage(String message) {
        try {
            Car car = objectMapper.readValue(message, Car.class);
            carXmlService.createXmlFile(car);
            kafkaTemplate.send(responseTopic, car.getId());
            log.info("Car consumed: {}", car);
        } catch (JsonProcessingException e) {
            log.error("Error processing message: {}", message, e);
        }
    }
}