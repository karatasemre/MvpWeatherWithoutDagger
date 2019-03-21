package mvpweather.com.mvpweather.presenter;

import com.oneframe.android.networking.listener.NetworkResponseListener;
import com.oneframe.android.networking.model.ErrorModel;
import com.oneframe.android.networking.model.ResultModel;

import mvpweather.com.mvpweather.api.WeatherServiceManager;
import mvpweather.com.mvpweather.model.weather.WeatherResponse;
import mvpweather.com.mvpweather.view.WeatherView;


public class WeatherPresenter {

    private WeatherView weatherView;
    private WeatherServiceManager weatherServiceManager;

    public WeatherPresenter(WeatherView weatherView) {
        this.weatherView = weatherView;
    }

    public void getWeatherSelectedCity(String countryName) {
        weatherView.operationStart();
        weatherServiceManager = new WeatherServiceManager();
        weatherServiceManager.getWeatherInfos(countryName, new NetworkResponseListener<WeatherResponse, String>() {
            @Override
            public void onSuccess(ResultModel<WeatherResponse> resultModel) {
                weatherView.operationStop();
                weatherView.weatherReady(resultModel.getModel());
            }

            @Override
            public void onError(ErrorModel<String> errorModel) {
                weatherView.operationStop();
                weatherView.weatherNotReady(errorModel.getData());
            }
        });
    }
}
