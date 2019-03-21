package mvpweather.com.mvpweather.model.weather;

import lombok.Data;

/**
 * Created by Emre.Karatas on 21.03.2019.
 */
@Data
public class Sys {
    private Integer type;
    private Integer id;
    private Double message;
    private String country;
    private Double sunrise;
    private Double sunset;
}
