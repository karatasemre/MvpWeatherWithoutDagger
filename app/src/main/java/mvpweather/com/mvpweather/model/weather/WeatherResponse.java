package mvpweather.com.mvpweather.model.weather;

import java.util.List;

import lombok.Data;

/**
 * Created by Emre.Karatas on 21.03.2019.
 */
@Data
public class WeatherResponse {
    private Coord coord;
    private List<Weather> weather = null;
    private String base;
    private Main main;
    private Integer visibility;
    private Wind wind;
    private Clouds clouds;
    private Double dt;
    private Sys sys;
    private Integer id;
    private String name;
    private Double cod;
}
