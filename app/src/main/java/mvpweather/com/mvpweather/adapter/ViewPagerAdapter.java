package mvpweather.com.mvpweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import mvpweather.com.mvpweather.ui.countries.CountriesFragment;
import mvpweather.com.mvpweather.ui.settings.SettingsFragment;
import mvpweather.com.mvpweather.ui.weather.WeatherFragment;

/**
 * Created by Emre.Karatas on 21.03.2019.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new CountriesFragment();
        } else if (position == 1) {
            fragment = new WeatherFragment();
        } else if(position == 2) {
            fragment = new SettingsFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Tab-1";
        } else if (position == 1) {
            title = "Tab-2";
        }
        return title;
    }
}