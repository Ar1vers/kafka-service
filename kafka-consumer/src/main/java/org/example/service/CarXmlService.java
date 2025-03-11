package org.example.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.model.Car;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class CarXmlService {
    private final XmlMapper xmlMapper = new XmlMapper();

    public void createXmlFile(Car car) {
        String filePath = "C:\\Users\\chern\\IdeaProjects\\car\\car_" + car.getId() + ".xml";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Создание директорий, если их нет
                file.createNewFile();
            }

            String xmlContent = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(car);

            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write(xmlContent.getBytes(StandardCharsets.UTF_8));
            }

            System.out.println("Файл успешно создан: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + e.getMessage());
        }
    }
}