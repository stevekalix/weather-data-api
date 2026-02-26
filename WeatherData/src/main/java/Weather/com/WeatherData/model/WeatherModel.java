package Weather.com.WeatherData.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "weather_data")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class WeatherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("datetime_utc")
    private String datetime_utc;

    @JsonProperty("_conds")
    private String _conds;

    @JsonProperty("_dewptm")
    private Integer _dewptm;

    @JsonProperty("_fog")
    private Integer _fog;

    @JsonProperty("_hail")
    private Integer _hail;

    @JsonProperty("_heatindexm")
    private String _heatindexm;

    @JsonProperty("_hum")
    private Integer _hum;

    @JsonProperty("_precipm")
    private String _precipm;

    @JsonProperty("_pressurem")
    private Long _pressurem;

    @JsonProperty("_rain")
    private Integer _rain;

    @JsonProperty("_snow")
    private Integer _snow;

    @JsonProperty("_tempm")
    private Integer _tempm;

    @JsonProperty("_thunder")
    private Integer _thunder;

    @JsonProperty("_tornado")
    private Integer _tornado;

    @JsonProperty("_vism")
    private String _vism;

    @JsonProperty("_wdird")
    private Integer _wdird;

    @JsonProperty("_wdire")
    private String _wdire;

    @JsonProperty("_wgustm")
    private String _wgustm;

    @JsonProperty("_windchillm")
    private String _windchillm;

    @JsonProperty("_wspdm")
    private String _wspdm;
}
