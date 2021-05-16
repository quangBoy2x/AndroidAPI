package com.example.apibtl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    String nameCity = "";
    ImageView imgBtnBack;
    TextView tvNameCity;
    ListView lvWeather;
    WeatherAdapter adapter;
    ArrayList<Weather> weathers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Map();
        Intent intent = getIntent();
        String city = intent.getStringExtra("nameCity");

        //Log.d("ket qua", city);
        if(city.equals("")){
            nameCity = "Saigon";
            GetAnotherWeatherdays(MainActivity.removeAccents(nameCity));
        }
        else {
            nameCity = city;
            GetAnotherWeatherdays(MainActivity.removeAccents(nameCity));
        }

        imgBtnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void GetAnotherWeatherdays(String data){
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+data+"&appid=913675779f34438d3d3e584d70d6006c&lang=vi";
        RequestQueue requestQueue = Volley.newRequestQueue(SecondActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                String name = jsonObjectCity.getString("name");
                tvNameCity.setText(name);

                JSONArray jsonArray = jsonObject.getJSONArray("list");

                for(int i = 0; i < jsonArray.length(); i+=8){
                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                    String day = jsonObjectList.getString("dt");
                    long l = Long.valueOf(day);
                    //gia tri mili s (gia trị mặc định)
                    Date date = new Date(l*1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy");
                    String Day = simpleDateFormat.format(date);

                    JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                    String min = jsonObjectMain.getString("temp_min");
                    String max = jsonObjectMain.getString("temp_max");

                    Double dbTempMax = Double.valueOf(max)/10 ;
                    Double dbTempMin = Double.valueOf(min)/10;

                    String maxTemp = String.format(" %.2f", dbTempMax);
                    String minTemp = String.format(" %.2f",dbTempMin);

                    JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    String icon = jsonObjectWeather.getString("icon");

//                    String day, String status, String imgStatus, String maxTemp, String minTemp
                    weathers.add(new Weather(Day, description, icon, maxTemp, minTemp));
                }

                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {

        });

        requestQueue.add(stringRequest);
    }

    void Map(){
        imgBtnBack = (ImageView) findViewById(R.id.imgBack);
        tvNameCity = (TextView) findViewById(R.id.tvSecondNameCity);
        lvWeather = (ListView) findViewById(R.id.lvWeather);
        weathers = new ArrayList<Weather>();
        adapter = new WeatherAdapter(SecondActivity.this, weathers);
        lvWeather.setAdapter(adapter);
    }
}