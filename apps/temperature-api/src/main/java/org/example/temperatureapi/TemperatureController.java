package org.example.temperatureapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
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
        double temperature = random.nextDouble() * 30 + 15;
        temperature = Math.round(temperature * 10.0) / 10.0;

        // Создание объекта TemperatureResponse
        TemperatureResponse response = new TemperatureResponse();
        response.setValue(temperature);
        response.setStatus("ACTIVE");

        // Временная метка с временной зоной UTC в формате ISO 8601
        String timestamp = ZonedDateTime.now(ZoneOffset.UTC)
                .withNano(0) // округляем до секунд (если не нужно наносекунды)
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        response.setTimestamp(timestamp);

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
