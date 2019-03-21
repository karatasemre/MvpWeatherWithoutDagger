package mvpweather.com.mvpweather.view;

import java.util.List;

import mvpweather.com.mvpweather.model.countries.Result;

public interface CountriesView {

    void countriesReady(List<Result> countries);

    void countriesNotReady(String error);

    void operationStart();

    void operationStop();
}
