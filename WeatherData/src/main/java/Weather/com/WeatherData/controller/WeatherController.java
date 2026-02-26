package Weather.com.WeatherData.controller;

import Weather.com.WeatherData.model.WeatherModel;
import Weather.com.WeatherData.repository.WeatherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WeatherController {

    @Autowired
    private WeatherRepo weatherRepo;

    // Health Check Endpoint
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Weather API is running successfully!");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadWeatherData(@RequestParam("file") MultipartFile file) {
        // Validate file is not empty
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        // Validate file name and extension
        String filename = file.getOriginalFilename();
        if (filename == null || (!filename.endsWith(".csv") && !filename.endsWith(".CSV"))) {
            return ResponseEntity.badRequest().body("File must be a CSV file");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            int lineNumber = 0;
            int savedCount = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                // Skip header row
                if (lineNumber == 1) continue;

                String[] data = line.split(",");
                // Skip incomplete rows
                if (data.length < 20) {
                    continue;
                }

                try {
                    WeatherModel weatherModel = new WeatherModel();
                    weatherModel.setDatetime_utc(data[0].trim());
                    weatherModel.set_conds(data[1].trim());
                    weatherModel.set_dewptm(Integer.parseInt(data[2].trim()));
                    weatherModel.set_fog(Integer.parseInt(data[3].trim()));
                    weatherModel.set_hail(Integer.parseInt(data[4].trim()));
                    weatherModel.set_heatindexm(data[5].trim());
                    weatherModel.set_hum(Integer.parseInt(data[6].trim()));
                    weatherModel.set_precipm(data[7].trim());
                    weatherModel.set_pressurem(Long.parseLong(data[8].trim()));
                    weatherModel.set_rain(Integer.parseInt(data[9].trim()));
                    weatherModel.set_snow(Integer.parseInt(data[10].trim()));
                    weatherModel.set_tempm(Integer.parseInt(data[11].trim()));
                    weatherModel.set_thunder(Integer.parseInt(data[12].trim()));
                    weatherModel.set_tornado(Integer.parseInt(data[13].trim()));
                    weatherModel.set_vism(data[14].trim());
                    weatherModel.set_wdird(Integer.parseInt(data[15].trim()));
                    weatherModel.set_wdire(data[16].trim());
                    weatherModel.set_wgustm(data[17].trim());
                    weatherModel.set_windchillm(data[18].trim());
                    weatherModel.set_wspdm(data[19].trim());

                    weatherRepo.save(weatherModel);
                    savedCount++;
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing line " + lineNumber + ": " + e.getMessage());
                }
            }

            return ResponseEntity.ok("File processed successfully. Saved " + savedCount + " records.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing the file: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<WeatherModel>> findAll() {
        List<WeatherModel> all = weatherRepo.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeatherModel> findById(@PathVariable Integer id) {
        Optional<WeatherModel> weatherModel = weatherRepo.findById(id);
        return weatherModel.map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            if (!weatherRepo.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            weatherRepo.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting record: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@PathVariable Integer id, @RequestBody WeatherModel weatherModel) {
        Optional<WeatherModel> existing = weatherRepo.findById(id);

        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        WeatherModel updated = existing.get();
        updated.set_conds(weatherModel.get_conds());
        updated.set_dewptm(weatherModel.get_dewptm());
        updated.set_fog(weatherModel.get_fog());
        updated.set_hail(weatherModel.get_hail());
        updated.set_heatindexm(weatherModel.get_heatindexm());
        updated.set_hum(weatherModel.get_hum());
        updated.set_precipm(weatherModel.get_precipm());
        updated.set_pressurem(weatherModel.get_pressurem());
        updated.set_rain(weatherModel.get_rain());
        updated.set_snow(weatherModel.get_snow());
        updated.set_tempm(weatherModel.get_tempm());
        updated.set_thunder(weatherModel.get_thunder());
        updated.set_tornado(weatherModel.get_tornado());
        updated.set_vism(weatherModel.get_vism());
        updated.set_wdird(weatherModel.get_wdird());
        updated.set_wdire(weatherModel.get_wdire());
        updated.set_wgustm(weatherModel.get_wgustm());
        updated.set_windchillm(weatherModel.get_windchillm());
        updated.set_wspdm(weatherModel.get_wspdm());
        updated.setDatetime_utc(weatherModel.getDatetime_utc());

        weatherRepo.save(updated);
        return ResponseEntity.ok("Updated successfully");
    }

    @GetMapping("/humidity/filter")
    public ResponseEntity<List<WeatherModel>> filterByHumidity(@RequestParam Integer hum) {
        List<WeatherModel> all = weatherRepo.findAll();
        List<WeatherModel> filtered = all.stream()
                .filter(w -> w.get_hum() > hum)
                .toList();

        if (filtered.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/temperature/filter")
    public ResponseEntity<List<WeatherModel>> filterByTemperature(@RequestParam Integer temp) {
        List<WeatherModel> all = weatherRepo.findAll();
        List<WeatherModel> filtered = all.stream()
                .filter(w -> w.get_tempm() >= temp)
                .toList();

        if (filtered.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filtered);
    }

    @GetMapping("/rain/filter")
    public ResponseEntity<List<WeatherModel>> filterByRain(@RequestParam Integer rain) {
        List<WeatherModel> all = weatherRepo.findAll();
        List<WeatherModel> filtered = all.stream()
                .filter(w -> w.get_rain() != null && w.get_rain().equals(rain))
                .toList();

        if (filtered.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filtered);
    }

}
