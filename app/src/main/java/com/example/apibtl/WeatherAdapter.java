package com.example.apibtl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {
    Context context;
    ArrayList<Weather> weathers;
    int resource;

    public WeatherAdapter(Context context, ArrayList<Weather> weathers) {
        this.context = context;
        this.weathers = weathers;
    }

    @Override
    public int getCount() {
        return weathers.size();
    }

    @Override
    public Object getItem(int position) {
        return weathers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.line_weather_forecast, null);


        Weather weather = weathers.get(position);
        TextView tvDay = convertView.findViewById(R.id.tvLineDay);
        TextView tvMaxTemp = convertView.findViewById(R.id.tvLineTempMax);
        TextView tvMinTemp = convertView.findViewById(R.id.tvLineTempMin);
        TextView tvStatus = convertView.findViewById(R.id.tvLineStatus);
        ImageView imgStatus = convertView.findViewById(R.id.imgLineStatus);

        tvDay.setText(weather.getDay());
        tvStatus.setText(weather.getStatus());
        tvMinTemp.setText(weather.getMinTemp() + "°C");
        tvMaxTemp.setText(weather.getMaxTemp() + "°C");
        Picasso.with(context).load("http://openweathermap.org/img/wn/"+weather.imgStatus+".png").into(imgStatus);

        return convertView;
    }


//    class ViewHolder {
//        TextView tvDay, tvStatus, tvMaxTemp, tvMinTemp;
//        ImageView imgStatus;
//    }


}
