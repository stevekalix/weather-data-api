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
@RequestMapping("/api")

public class WeatherController {

    @Autowired
    private WeatherRepo weatherRepo;


    @PostMapping("/file")
    public  ResponseEntity<String> creareted(@RequestParam("file") MultipartFile file) {

        if(file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try(BufferedReader br= new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                WeatherModel weatherModel = new WeatherModel();
                weatherModel.setDatetime_utc(data[0]);
                weatherModel.set_conds(data[1]);
                weatherModel.set_dewptm(Integer.parseInt(data[2]));
                weatherModel.set_fog(Integer.parseInt(data[3]));
                weatherModel.set_hail(Integer.parseInt(data[4]));
                weatherModel.set_heatindexm(data[5]);
                weatherModel.set_hum(Integer.parseInt(data[6]));
                weatherModel.set_precipm(data[7]);
                weatherModel.set_pressurem(Long.parseLong(data[8]));
                weatherModel.set_rain(Integer.parseInt(data[9]));
                weatherModel.set_snow(Integer.parseInt(data[10]));
                weatherModel.set_tempm(Integer.parseInt(data[11]));
                weatherModel.set_thunder(Integer.parseInt(data[12]));
                weatherModel.set_tornado(Integer.parseInt(data[13]));
                weatherModel.set_vism(data[14]);
                weatherModel.set_wdird(Integer.parseInt(data[15]));
                weatherModel.set_wdire(data[16]);
                weatherModel.set_wgustm(data[17]);
                weatherModel.set_windchillm(data[18]);
                weatherModel.set_wspdm(data[19]);
                weatherRepo.save(weatherModel);
            }
            return ResponseEntity.ok("File processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file: " + e.getMessage());
        }
    }

    //het all the values
    @GetMapping("/all")
    public ResponseEntity<List<WeatherModel>> findAll(){
        List<WeatherModel> all = weatherRepo.findAll();
        if(all.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(all);
        }
    }



    // get by id
   @GetMapping("/{id}")
    public ResponseEntity<WeatherModel> findById(@PathVariable String id){
        return weatherRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id){
        try{
            weatherRepo.deleteById(id);
            return ResponseEntity.ok("Deleted successfully");
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // update by id
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@PathVariable String id, @RequestBody WeatherModel weatherModel){
        Optional<WeatherModel> all = weatherRepo.findById(id);
        if(all.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else {
            WeatherModel existingWeatherModel = all.get();
            existingWeatherModel.set_conds(weatherModel.get_conds());
            existingWeatherModel.set_dewptm(weatherModel.get_dewptm());
            existingWeatherModel.set_fog(weatherModel.get_fog());
            existingWeatherModel.set_hail(weatherModel.get_hail());
            existingWeatherModel.set_heatindexm(weatherModel.get_heatindexm());
            existingWeatherModel.set_hum(weatherModel.get_hum());
            existingWeatherModel.set_precipm(weatherModel.get_precipm());
            existingWeatherModel.set_pressurem(weatherModel.get_pressurem());
            existingWeatherModel.set_rain(weatherModel.get_rain());
            existingWeatherModel.set_snow(weatherModel.get_snow());
            existingWeatherModel.set_tempm(weatherModel.get_tempm());
            existingWeatherModel.set_thunder(weatherModel.get_thunder());
            existingWeatherModel.set_tornado(weatherModel.get_tornado());
            existingWeatherModel.set_vism(weatherModel.get_vism());
            existingWeatherModel.set_wdird(weatherModel.get_wdird());
            existingWeatherModel.set_wdire(weatherModel.get_wdire());
            existingWeatherModel.set_wgustm(weatherModel.get_wgustm());
            existingWeatherModel.set_windchillm(weatherModel.get_windchillm());
            existingWeatherModel.set_wspdm(weatherModel.get_wspdm());

            weatherRepo.save(existingWeatherModel);
            return ResponseEntity.ok("Updated successfully");
        }
    }

    // get all the values with humidity greater than a specific value
    @GetMapping("/humidity")
    public ResponseEntity<List<WeatherModel>> greater_hum(@RequestParam Integer hum){
        List<WeatherModel> all = weatherRepo.findAll();
        List<WeatherModel> filtered = all.stream().filter(w -> w.get_hum() > hum).toList();
        if (filtered.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        else {
            return new ResponseEntity<>(filtered, HttpStatus.OK);
        }
    }

}
