package org.example.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CarProducer {
    private static final Logger log = LoggerFactory.getLogger(CarProducer.class);

    @Value("${topic.name}")
    private String orderTopic;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String send(Car car) {
        try {
            String message = objectMapper.writeValueAsString(car);
            kafkaTemplate.send(orderTopic, message);
            log.info("Car produced {}", message);
            return "Message sent";
        } catch (JsonProcessingException e) {
            return "Error parse json";
        }
    }
}