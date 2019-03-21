package mvpweather.com.mvpweather.ui.countries;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mvpweather.com.mvpweather.R;
import mvpweather.com.mvpweather.adapter.CountriesAdapter;
import mvpweather.com.mvpweather.model.countries.Result;
import mvpweather.com.mvpweather.presenter.CountriesPresenter;
import mvpweather.com.mvpweather.ui.MainActivity;
import mvpweather.com.mvpweather.view.CountriesView;

public class CountriesFragment extends Fragment implements CountriesView {

    CountriesPresenter countriesPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    CountriesAdapter countriesAdapter;
    private List<Result> resultList = new ArrayList<>();

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_countries, container, false);
        unbinder = ButterKnife.bind(this, v);

        setRecyclerView();

        if (!MainActivity.instance.isSettingsToCountry) {
            countriesPresenter = new CountriesPresenter(this, getActivity());
            countriesPresenter.getCountries();
        } else {
            countriesPresenter.getSessionCountries();
        }


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setRecyclerView() {
        countriesAdapter = new CountriesAdapter(resultList, (MainActivity) getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(countriesAdapter);
    }

    @Override
    public void countriesReady(List<Result> countries) {
        countriesAdapter = new CountriesAdapter(countries, (MainActivity) getActivity());
        recyclerView.setAdapter(countriesAdapter);
        countriesAdapter.notifyDataSetChanged();
    }

    public void filteredCounties(String filterValue) {
        countriesPresenter.getFilteredValue(filterValue);
    }

    public void getSession(){
        countriesPresenter.getSessionCountries();
    }

    @Override
    public void countriesNotReady(String error) {
        Log.e("asd", "asd");
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
