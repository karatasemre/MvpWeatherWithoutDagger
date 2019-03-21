package mvpweather.com.mvpweather.presenter;

import android.content.Context;

import com.oneframe.android.networking.listener.NetworkResponseListener;
import com.oneframe.android.networking.model.ErrorModel;
import com.oneframe.android.networking.model.ResultModel;

import java.util.ArrayList;
import java.util.List;

import mvpweather.com.mvpweather.api.CountriesServiceManager;
import mvpweather.com.mvpweather.api.WeatherServiceManager;
import mvpweather.com.mvpweather.data.SharedPreferenceUtils;
import mvpweather.com.mvpweather.model.countries.CountriesResponse;
import mvpweather.com.mvpweather.model.countries.Result;
import mvpweather.com.mvpweather.model.weather.WeatherResponse;
import mvpweather.com.mvpweather.view.SettingsView;

public class SettingsPresenter {

    private SettingsView settingsView;
    private SharedPreferenceUtils sharedPreferenceUtils;
    private Context mContext;
    private WeatherServiceManager weatherServiceManager;
    List<Result> availableCountries;
    List<Result> countryList;
    private CountriesServiceManager countriesServiceManager;

    public SettingsPresenter(SettingsView settingsView, Context mContext) {
        this.settingsView = settingsView;
        this.mContext = mContext;

        if (this.countriesServiceManager == null) {
            this.countriesServiceManager = new CountriesServiceManager();
        }

    }

    public void cleanCountries() {
        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(mContext);
        availableCountries = new ArrayList<>();

        countryList = new ArrayList<>();
        countryList = sharedPreferenceUtils.getCountries();
        settingsView.operationStart();
        for (Result country : countryList) {
            weatherServiceManager = new WeatherServiceManager();
            weatherServiceManager.getWeatherInfos(country.getName(), new NetworkResponseListener<WeatherResponse, String>() {
                @Override
                public void onSuccess(ResultModel<WeatherResponse> resultModel) {
                    availableCountries.add(country);
                    finishCleanCountryOperation(country);

                }

                @Override
                public void onError(ErrorModel<String> errorModel) {
                    finishCleanCountryOperation(country);
                }
            });
        }
    }

    public void getCountries() {
        settingsView.operationStart();
        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(mContext);
        countriesServiceManager.getCountries(new NetworkResponseListener<CountriesResponse, String>() {
            @Override
            public void onSuccess(ResultModel<CountriesResponse> resultModel) {
                List<Result> resultList = resultModel.getModel().getRestResponse().getResult();
                sharedPreferenceUtils.setCountries(resultList);
                settingsView.operationStop();
                settingsView.onCompleted();
            }

            @Override
            public void onError(ErrorModel<String> errorModel) {
                settingsView.operationStop();
            }
        });
    }

    public void finishCleanCountryOperation(Result country) {
        if (country == countryList.get(countryList.size() - 1)) {
            sharedPreferenceUtils.setCountries(availableCountries);
            settingsView.operationStop();
            settingsView.onCompleted();
        }
    }

}
