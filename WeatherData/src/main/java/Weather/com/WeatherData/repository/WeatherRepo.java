package Weather.com.WeatherData.repository;

import Weather.com.WeatherData.model.WeatherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepo extends JpaRepository<WeatherModel, Integer> {
}
