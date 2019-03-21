package mvpweather.com.mvpweather.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import mvpweather.com.mvpweather.model.countries.Result;

/**
 * Created by Emre.Karatas on 21.03.2019.
 */

public class SharedPreferenceUtils {
    private static SharedPreferenceUtils mSharedPreferenceUtils;
    protected Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    public String GET_ALL_COUNTRIES = "GET_ALL_COUNTRIES";

    private SharedPreferenceUtils(Context context) {
        mContext = context;
        mSharedPreferences = context.getSharedPreferences("countryToWeather", Context.MODE_PRIVATE);
        mSharedPreferencesEditor = mSharedPreferences.edit();
    }

    public static synchronized SharedPreferenceUtils getInstance(Context context) {

        if (mSharedPreferenceUtils == null) {
            mSharedPreferenceUtils = new SharedPreferenceUtils(context.getApplicationContext());
        }
        return mSharedPreferenceUtils;
    }

    public void setCountries(List<Result> resultList) {
        Gson gson = new Gson();
        String json = gson.toJson(resultList);
        mSharedPreferencesEditor.putString(GET_ALL_COUNTRIES, json);
        mSharedPreferencesEditor.commit();
    }

    public List<Result> getCountries() {
        Gson gson = new Gson();
        return gson.fromJson(mSharedPreferences.getString(GET_ALL_COUNTRIES, ""), new TypeToken<ArrayList<Result>>() {
        }.getType());
    }

    public void removeKey(String key) {
        if (mSharedPreferencesEditor != null) {
            mSharedPreferencesEditor.remove(key);
            mSharedPreferencesEditor.commit();
        }
    }

    public void clear() {
        mSharedPreferencesEditor.clear().commit();
    }
}
