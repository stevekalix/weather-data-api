package Weather.com.WeatherData.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="dataset")
public class WeatherModel {

    @Id
    private String datetime_utc;

    private  String  _conds;
    private  Integer  _dewptm;
    private  Integer _fog;
    private  Integer  _hail;
    private  String  _heatindexm;
    private  Integer  _hum;
    private  String  _precipm;
    private  Long   _pressurem;
    private  Integer  _rain;
    private  Integer  _snow;
    private  Integer  _tempm;
    private  Integer  _thunder;
    private  Integer  _tornado;
    private String _vism;
    private  Integer _wdird;
    private String _wdire;
    private  String _wgustm;
    private  String _windchillm;
    private  String _wspdm;

}
