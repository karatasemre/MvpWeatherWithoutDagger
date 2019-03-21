package mvpweather.com.mvpweather.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.oneframe.android.networking.NetworkingFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mvpweather.com.mvpweather.R;
import mvpweather.com.mvpweather.adapter.ViewPagerAdapter;
import mvpweather.com.mvpweather.ui.countries.CountriesFragment;
import mvpweather.com.mvpweather.ui.weather.WeatherFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.space_navigation)
    SpaceNavigationView spaceNavigation;
    @BindView(R.id.progress_content)
    LinearLayout progressBarContent;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.search_country)
    EditText searchCountry;

    public static boolean isSettingsToCountry = false;

    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        instance = this;

        NetworkingFactory.init(this);

        setUpViewPager();
        setSpaceNavigationBottomMenu(savedInstanceState);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position < 1) {
                    showToolbar();
                    spaceNavigation.changeCurrentItem(position);
                } else if (position == 1) {
                    showToolbar();
                    spaceNavigation.setCentreButtonSelectable(true);
                    spaceNavigation.changeCurrentItem(-1);
                } else {
                    goneToolbar();
                    spaceNavigation.changeCurrentItem(position - 1);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        spaceNavigation.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                viewPager.setCurrentItem(1);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex > 0) {
                    viewPager.setCurrentItem(itemIndex + 1);
                } else {
                    viewPager.setCurrentItem(itemIndex);
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Log.e("onItemReselected", itemName + " " + itemIndex);
            }
        });

        searchCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (viewPager.getCurrentItem() == 0) {
                    String tag = "android:switcher:" + R.id.viewPager + ":" + 0;
                    CountriesFragment countriesFragment = (CountriesFragment) getSupportFragmentManager().findFragmentByTag(tag);
                    if (s.length() > 0) {
                        countriesFragment.filteredCounties(s.toString());
                    } else {
                        countriesFragment.getSession();
                    }
                }
            }
        });

        viewPager.setCurrentItem(1);
    }

    @OnClick(R.id.search_icon)
    public void setSearchIcon() {
        String countryName = searchCountry.getText().toString();
        if (countryName.length() > 4) {
            String tag = "android:switcher:" + R.id.viewPager + ":" + 1;
            WeatherFragment f = (WeatherFragment) getSupportFragmentManager().findFragmentByTag(tag);
            f.triggerPresenter(countryName);
        }
    }

    public void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
    }

    public void setSpaceNavigationBottomMenu(Bundle savedInstanceState) {
        spaceNavigation.initWithSaveInstanceState(savedInstanceState);
        spaceNavigation.addSpaceItem(new SpaceItem("Countries", R.drawable.ic_home_tab));
        spaceNavigation.addSpaceItem(new SpaceItem("Settings", R.drawable.ic_settings_tab));
        spaceNavigation.setSpaceItemTextSize(12);
    }

    public void changeCountryToWeather(String countryName) {
        viewPager.setCurrentItem(1);
        String tag = "android:switcher:" + R.id.viewPager + ":" + 1;
        WeatherFragment f = (WeatherFragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.triggerPresenter(countryName);
    }

    public void changeSettingsToCountry() {
        isSettingsToCountry = true;
        viewPager.setCurrentItem(0);
    }

    public void showProgress() {
        progressBarContent.setVisibility(View.VISIBLE);
    }

    public void goneProgress() {
        progressBarContent.setVisibility(View.GONE);
    }

    public void goneToolbar() {
        collapsingToolbarLayout.setVisibility(View.GONE);
    }

    public void showToolbar() {
        collapsingToolbarLayout.setVisibility(View.VISIBLE);
    }

}
