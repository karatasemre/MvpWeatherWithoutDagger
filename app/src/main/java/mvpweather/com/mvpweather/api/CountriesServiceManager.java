package mvpweather.com.mvpweather.api;

import com.oneframe.android.networking.NetworkConfig;
import com.oneframe.android.networking.NetworkManager;
import com.oneframe.android.networking.NetworkingFactory;
import com.oneframe.android.networking.listener.NetworkResponseListener;

import mvpweather.com.mvpweather.model.countries.CountriesResponse;

import static mvpweather.com.mvpweather.api.ConstantMethodSL.GET_COUNTRIES;

public class CountriesServiceManager {
    NetworkManager networkManager;
    public static final String RESULT_TAG = "Data";
    public static String COUNTRIES_URL = "http://services.groupkt.com/";

    public CountriesServiceManager() {
        networkManager = NetworkingFactory.create();
        NetworkConfig.getInstance().setURL(COUNTRIES_URL);
        NetworkConfig.getInstance().getDefaultHeaders().put("Cache-Control", "no-cache");
        networkManager.setNetworkLearning(new NetworkLearning());
    }

    public void getCountries(final NetworkResponseListener<CountriesResponse, String> listener) {
        networkManager.get(GET_COUNTRIES, listener);
    }

}
