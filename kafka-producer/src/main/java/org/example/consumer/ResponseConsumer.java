package org.example.consumer;

import org.example.model.Car;
import org.example.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ResponseConsumer {

    private static final Logger log = LoggerFactory.getLogger(ResponseConsumer.class);

    @Autowired
    private CarRepository carRepository;

    @KafkaListener(topics = "${topic.response.name}")
    public void consumeResponse(String carId) {
        try {
            Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Автомобиль с id " + carId + " не найден"));

            car.setFileCreated(true);
            carRepository.save(car);
            log.info("fileCreated обновлён для автомобиля с id: {}", carId);
        } catch (Exception e) {
            log.error("Ошибка при обновлении автомобиля с id {}: {}", carId, e.getMessage());
        }
    }
}