package mvpweather.com.mvpweather.presenter;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.oneframe.android.networking.listener.NetworkResponseListener;
import com.oneframe.android.networking.model.ErrorModel;
import com.oneframe.android.networking.model.ResultModel;

import java.util.List;

import mvpweather.com.mvpweather.api.CountriesServiceManager;
import mvpweather.com.mvpweather.data.SharedPreferenceUtils;
import mvpweather.com.mvpweather.model.countries.CountriesResponse;
import mvpweather.com.mvpweather.model.countries.Result;
import mvpweather.com.mvpweather.view.CountriesView;

public class CountriesPresenter {

    private CountriesView countriesView;
    private CountriesServiceManager countriesServiceManager;
    private SharedPreferenceUtils sharedPreferenceUtils;
    private Context context;

    public CountriesPresenter(CountriesView countriesView, Context context) {
        this.countriesView = countriesView;
        this.context = context;
        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(context);

        if (this.countriesServiceManager == null) {
            this.countriesServiceManager = new CountriesServiceManager();
        }
    }

    public void getCountries() {
        countriesView.operationStart();
        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(context);
        countriesServiceManager.getCountries(new NetworkResponseListener<CountriesResponse, String>() {
            @Override
            public void onSuccess(ResultModel<CountriesResponse> resultModel) {
                List<Result> resultList = resultModel.getModel().getRestResponse().getResult();
                sharedPreferenceUtils.setCountries(resultList);
                countriesView.operationStop();
                countriesView.countriesReady(resultList);

            }

            @Override
            public void onError(ErrorModel<String> errorModel) {
                countriesView.operationStop();
                countriesView.countriesNotReady(errorModel.getData());
            }
        });
    }

    public void getFilteredValue(String filterValue) {
        List<Result> resultList = Stream.of(sharedPreferenceUtils.getCountries()).filter(p -> p.getName().toLowerCase().contains(filterValue.toLowerCase())).collect(Collectors.toList());
        countriesView.countriesReady(resultList);
    }

    public void getSessionCountries() {
        sharedPreferenceUtils = SharedPreferenceUtils.getInstance(context);
        countriesView.countriesReady(sharedPreferenceUtils.getCountries());
    }
}
