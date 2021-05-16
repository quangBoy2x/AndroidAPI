package com.example.apibtl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    EditText edtSearch;
    Button  btnAnotherDays, btnYoutube;
    TextView tvCityName, tvCountryName, tvTemp, tvStatus, tvHumid, tvCloud, tvWind, tvDay, tvDetailStatus, tvTempFeel;
    ImageView imgMain, btnSearch, imgCity;
    String City = "";
    String lat="", lon = "";
//    SharedPreferences sharedPreferences;
//    String API_YTB = "AIzaSyBLvJXpAOOQ1zkAXEVUA4X857NlaXu7mtk";
//    int REQUEST_VID = 123;

//    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map();
//        sharedPreferences = getSharedPreferences("nameCity", MODE_PRIVATE);
//        tvCityName.setText(sharedPreferences.getString("nameCity",""));


        GetCurrentWeatherData("Saigon");
        btnSearch.setOnClickListener(v -> {
            String city = removeAccents(edtSearch.getText().toString().trim());

            if(city.equals("")){
                City = "Saigon";
                GetCurrentWeatherData(City);
            }
            else{
                City = city;
                GetCurrentWeatherData(City);
            }

        });

        btnAnotherDays.setOnClickListener(v -> {
            String city = edtSearch.getText().toString().trim();
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("nameCity", city);
            startActivity(intent);
        });

        btnYoutube.setOnClickListener(v -> {
            Intent intent = new Intent(this, YoutubeActivity.class);
            startActivity(intent);
        });

        imgCity.setOnClickListener(v -> {
            Intent intent = new Intent(this, LocationCity.class);
            intent.putExtra("lat", lat);
            intent.putExtra("lon", lon);
            startActivity(intent);

        });

    }

    //xóa bỏ unicode
    public static String removeAccents(String s){
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return s;
    }

    public void GetCurrentWeatherData(String data){
        //thuc thi cac request gui di

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=913675779f34438d3d3e584d70d6006c&lang=vi";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
             //Log.d("ket qua: ",response);
            try {
                //lay json ket qua thoi tiet
                JSONObject jsonObject = new JSONObject(response);
                String day = jsonObject.getString("dt");
                String nameCity = jsonObject.getString("name");
                tvCityName.setText(nameCity);

                long l = Long.valueOf(day);
                //gia tri mili s (gia trị mặc định)
                Date date = new Date(l*1000L);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH-mm");
                String Day = simpleDateFormat.format(date);

                tvDay.setText(Day);
                JSONArray jsonArray = jsonObject.getJSONArray("weather");
                JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                String status = jsonObjectWeather.getString("main");
                String imgStatus = jsonObjectWeather.getString("icon");
                String description = jsonObjectWeather.getString("description");

                //dung thu vien picasso de load hinh anh vao dien thoai
                Picasso.with(this).load("http://openweathermap.org/img/wn/"+imgStatus+".png").into(imgMain);
                tvStatus.setText(status);
                tvDetailStatus.setText(description);

                //lấy kinh độ, vĩ độ ra để tìm kiếm tp trên bản đồ
                JSONObject jsonObjectCoord = jsonObject.getJSONObject("coord");
                lon = jsonObjectCoord.getString("lon");
                lat = jsonObjectCoord.getString("lat");


                JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                String temperature = jsonObjectMain.getString("temp");
                String temperatureFeel = jsonObjectMain.getString("feels_like");
                String humid = jsonObjectMain.getString("humidity");

                Double dbTemp = Double.valueOf(temperature);
                Double dbTempFeel = Double.valueOf(temperatureFeel);
                String Temperature = String.valueOf(dbTemp);
                String TemperatureFeel = String.valueOf(dbTempFeel);

                tvTemp.setText("Nhiệt độ: "+Temperature+"°C");
                tvTempFeel.setText("Cảm thấy: "+TemperatureFeel+"°C");

                tvHumid.setText(humid+"%");

                JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                String wind = jsonObjectWind.getString("speed");
                tvWind.setText(wind+"m/s");

                JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                String cloud = jsonObjectCloud.getString("all");
                tvCloud.setText(cloud+"%");

                JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                String country = jsonObjectSys.getString("country");
                tvCountryName.setText(country);

                //luu vao sharedPreferences
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("nameCity", nameCity);
//                editor.putString("nameCountry", country);
//                editor.putString("day", Day);
//                editor.putString("detail", description);
//                editor.putString("temp", Temperature);
//                editor.putString("tempFeel", TemperatureFeel);
//                editor.putString("status", status);
//                editor.putString("sttWind", wind);
//                editor.putString("sttCloud", cloud);
//                editor.putString("sttHumid", humid);
//                //xac nhan luu trang thai thoi tiet
//                editor.commit();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> {
            Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
        });

        requestQueue.add(stringRequest);

    }




    public void Map(){
//        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.myYoutube);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        btnSearch = (ImageView) findViewById(R.id.btnSearch);
        btnAnotherDays = (Button) findViewById(R.id.btnAnotherDays);
        tvCityName = (TextView) findViewById(R.id.tvCityName);
        tvCountryName = (TextView) findViewById(R.id.tvCountryName);
        tvTemp = (TextView) findViewById(R.id.tvTemperature);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvHumid = (TextView) findViewById(R.id.tvStatusHumid);
        tvWind = (TextView) findViewById(R.id.tvStatusWind);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvCloud = (TextView) findViewById(R.id.tvStatusCloud);
        tvDetailStatus = (TextView) findViewById(R.id.tvDetailStatus);
        tvTempFeel = (TextView) findViewById(R.id.tvTemperatureFeel);
        imgMain = (ImageView) findViewById(R.id.imgMain);
        btnYoutube = (Button) findViewById(R.id.btnYoutube);
        imgCity = (ImageView) findViewById(R.id.imgCityLoc);

    }


}