package org.example.temperatureapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class TemperatureController {

    private final Random random = new Random();

    @GetMapping("/temperature")
    public TemperatureResponse getTemperature(@RequestParam(value = "location", defaultValue = "") String location,
                                              @RequestParam(value = "sensorId", defaultValue = "") String sensorId) {

        // Если местоположение не указано, используем значение по умолчанию на основе sensorId
        if (location.isEmpty()) {
            switch (sensorId) {
                case "1":
                    location = "Living Room";
                    break;
                case "2":
                    location = "Bedroom";
                    break;
                case "3":
                    location = "Kitchen";
                    break;
                default:
                    location = "Unknown";
                    break;
            }
        }

        // Если sensorId не указан, генерируем его на основе местоположения
        if (sensorId.isEmpty()) {
            switch (location) {
                case "Living Room":
                    sensorId = "1";
                    break;
                case "Bedroom":
                    sensorId = "2";
                    break;
                case "Kitchen":
                    sensorId = "3";
                    break;
                default:
                    sensorId = "0";
                    break;
            }
        }

        // Генерация случайной температуры
        double temperature = random.nextDouble() * 30 + 15; // от 15 до 45 градусов

        // Создание объекта TemperatureResponse
        TemperatureResponse response = new TemperatureResponse();
        response.setValue(temperature);
        response.setStatus("ACTIVE"); // Пример статуса
        response.setTimestamp( LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return response;
    }

    // Класс для хранения данных температуры
    public static class TemperatureResponse {
        @JsonProperty("value")
        private double value;
        @JsonProperty("status")
        private String status;
        @JsonProperty("timestamp")
        private String timestamp;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
