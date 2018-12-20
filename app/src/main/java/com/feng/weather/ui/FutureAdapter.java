package com.feng.weather.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feng.weather.R;
import com.feng.weather.entity.WeatherFuture;
import com.feng.weather.utils.WeatherUtil;

import java.util.List;

/**
 * author 丰
 * date   2018/12/6
 * desc
 */
public class FutureAdapter extends RecyclerView.Adapter<FutureAdapter.FutureHolder> {

    private List<WeatherFuture> mFutures;
    private Context mContext;

    public FutureAdapter(List<WeatherFuture> futures) {
        this.mFutures = futures;
    }

    @NonNull
    @Override
    public FutureHolder onCreateViewHolder(@NonNull ViewGroup group, int type) {
        if (mContext == null) {
            mContext = group.getContext();
        }
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.future_item, group, false);

        return new FutureHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FutureHolder futureHolder, int position) {
        WeatherFuture future = mFutures.get(position);
        futureHolder.week.setText(future.getWeek());
        futureHolder.temperature.setText(future.getTemperature());
        futureHolder.weather.setText(future.getWeather());
        futureHolder.wind.setText(future.getWind());
        futureHolder.ico.setImageResource(WeatherUtil.getImage(future.getWeather_id()));
    }

    @Override
    public int getItemCount() {
        return mFutures.size();
    }

    class FutureHolder extends RecyclerView.ViewHolder {
        TextView week;
        TextView temperature;
        TextView weather;
        TextView wind;
        ImageView ico;

        FutureHolder(@NonNull View itemView) {
            super(itemView);

            week = itemView.findViewById(R.id.tv_future_week);
            weather = itemView.findViewById(R.id.tv_future_weather);
            temperature = itemView.findViewById(R.id.tv_future_temperature);
            wind = itemView.findViewById(R.id.tv_future_wind);
            ico=itemView.findViewById(R.id.iv_future_ico);
        }
    }
}
