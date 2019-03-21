package mvpweather.com.mvpweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mvpweather.com.mvpweather.R;
import mvpweather.com.mvpweather.model.countries.Result;
import mvpweather.com.mvpweather.ui.MainActivity;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.MyViewHolder> {

    private List<Result> resultList;

    private MainActivity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView countryName, countryAlpha;
        public CircleImageView countryFlag;
        public RelativeLayout itemContent;

        public MyViewHolder(View view) {
            super(view);
            countryName = view.findViewById(R.id.country_name);
            countryAlpha = view.findViewById(R.id.country_alpha);
            countryFlag = view.findViewById(R.id.country_flag);
            itemContent = view.findViewById(R.id.item_content);
        }
    }

    public CountriesAdapter(List<Result> resultList, MainActivity mActivity) {
        this.resultList = resultList;
        this.mActivity = mActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_countries, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Result result = resultList.get(position);
        holder.countryName.setText(result.getName());
        holder.countryAlpha.setText(result.getAlpha2Code());

        holder.itemContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.changeCountryToWeather(result.getName());
            }
        });

        Glide.with(mActivity)
                .load("https://www.countryflags.io/" + result.getAlpha2Code() + "/flat/64.png")
                .into(holder.countryFlag);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
}