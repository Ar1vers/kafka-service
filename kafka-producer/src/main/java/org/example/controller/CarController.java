package org.example.controller;

import org.example.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public String getRequest() {
        return "All works!";
    }

    @PostMapping("/cars/{carId}")
    public ResponseEntity<String> postRequest(@PathVariable String carId) {
        String result = carService.processCar(carId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}