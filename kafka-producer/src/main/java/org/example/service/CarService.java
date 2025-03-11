package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.model.Car;
import org.example.producer.CarProducer;
import org.example.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private static final Logger log = LoggerFactory.getLogger(CarService.class);

    @Autowired
    private CarProducer carProducer;

    @Autowired
    private CarRepository carRepository;

    public String processCar(String carId) {
        Car existingCar = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + carId));

        existingCar.setFileCreated(false);
        carRepository.save(existingCar);

        log.info("Car is received by kafka-producer: {}", existingCar);
        return carProducer.send(existingCar);
    }
}