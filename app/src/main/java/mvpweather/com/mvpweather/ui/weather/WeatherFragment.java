package mvpweather.com.mvpweather.ui.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mvpweather.com.mvpweather.R;
import mvpweather.com.mvpweather.model.weather.WeatherResponse;
import mvpweather.com.mvpweather.presenter.WeatherPresenter;
import mvpweather.com.mvpweather.ui.MainActivity;
import mvpweather.com.mvpweather.view.WeatherView;

public class WeatherFragment extends Fragment implements WeatherView {

    WeatherPresenter weatherPresenter;
    @BindView(R.id.weather_status_icon)
    ImageView weatherStatusIcon;
    @BindView(R.id.city_name)
    TextView cityName;
    @BindView(R.id.weather_status)
    TextView weatherStatus;
    @BindView(R.id.wind_speed)
    TextView windSpeed;
    @BindView(R.id.humidity)
    TextView humidity;
    @BindView(R.id.temp_max)
    TextView tempMax;
    @BindView(R.id.temp_min)
    TextView tempMin;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, v);

        weatherPresenter = new WeatherPresenter(this);
        weatherPresenter.getWeatherSelectedCity("Turkey");

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void weatherReady(WeatherResponse weatherResponse) {
        cityName.setText(weatherResponse.getName());
        weatherStatus.setText(weatherResponse.getWeather().get(0).getDescription());
        humidity.setText(weatherResponse.getMain().getHumidity().toString());
        tempMax.setText(weatherResponse.getMain().getTempMax() + "");
        tempMin.setText(weatherResponse.getMain().getTempMin() + "");
        windSpeed.setText(weatherResponse.getWind().getSpeed().toString());

        Glide.with(getContext()).load("http://openweathermap.org/img/w/" + weatherResponse.getWeather().get(0).getIcon() + ".png").into(weatherStatusIcon);
    }

    @Override
    public void weatherNotReady(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void operationStart() {
        MainActivity.instance.showProgress();
    }

    @Override
    public void operationStop() {
        MainActivity.instance.goneProgress();
    }

    public void triggerPresenter(String selectedCountry) {
        weatherPresenter.getWeatherSelectedCity(selectedCountry);
    }
}
