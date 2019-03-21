package mvpweather.com.mvpweather.ui.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mvpweather.com.mvpweather.R;
import mvpweather.com.mvpweather.presenter.SettingsPresenter;
import mvpweather.com.mvpweather.ui.MainActivity;
import mvpweather.com.mvpweather.view.SettingsView;

public class SettingsFragment extends Fragment implements SettingsView {

    SettingsPresenter settingsPresenter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, v);

        settingsPresenter = new SettingsPresenter(this, getContext());

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCompleted() {
        MainActivity.instance.changeSettingsToCountry();
    }

    @OnClick(R.id.clean_country)
    public void cleanCountry() {
        settingsPresenter.cleanCountries();
    }

    @OnClick(R.id.all_country)
    public void allCountry() {
        settingsPresenter.getCountries();
    }

    @Override
    public void operationStart() {
        MainActivity.instance.showProgress();
    }

    @Override
    public void operationStop() {
        MainActivity.instance.goneProgress();
    }
}
