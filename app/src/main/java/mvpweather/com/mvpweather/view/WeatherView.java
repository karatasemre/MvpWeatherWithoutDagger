package mvpweather.com.mvpweather.view;

import mvpweather.com.mvpweather.model.weather.WeatherResponse;

public interface WeatherView {
    void weatherReady(WeatherResponse weatherResponse);

    void weatherNotReady(String error);

    void operationStart();

    void operationStop();
}
