package mvpweather.com.mvpweather.model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by Emre.Karatas on 21.03.2019.
 */
@Data
public class Main {
    private Double temp;
    private Double pressure;
    private Double humidity;
    @SerializedName("temp_min")
    @Expose
    private Double tempMin;
    @SerializedName("temp_max")
    @Expose
    private Double tempMax;

}
