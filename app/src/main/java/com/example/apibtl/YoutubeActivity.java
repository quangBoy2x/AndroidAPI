package com.example.apibtl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class YoutubeActivity extends YouTubeBaseActivity  {
    public static String API_KEY = "AIzaSyCBBGGHZPPOnjbJhdy1Cuwb8dw7Pps7Oqw";
    String LIST_ID = "PLDtIPrfEs9CyV5Bo6traxGcuxv2lI92_1";
    ListView lvYoutube;
    ArrayList<VideoYoutube> videoYoutubes;
    YoutubeAdapter adapter;
//    YouTubePlayerView youTubePlayerView;
    int REQUEST_CODE = 123;
    String urlGetJson = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+LIST_ID+"&key="+API_KEY+"&maxResults=15";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Map();
        videoYoutubes = new ArrayList<>();
        adapter = new YoutubeAdapter(this, R.layout.row_video_youtube, videoYoutubes);
        lvYoutube.setAdapter(adapter);

        GetJsonYoutube(urlGetJson);
//        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.myYouyube);

//        youTubePlayerView.initialize(API_KEY, this);

        lvYoutube.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, PlayVidActivity.class);
            intent.putExtra("idVid", videoYoutubes.get(position).getIdVideo());
            startActivity(intent);
        });
    }

    void Map()
    {
        lvYoutube = (ListView) findViewById(R.id.lvYoutube);
    }

    private void GetJsonYoutube(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, URL, null, response -> {
            try {
                JSONArray jsonItems = response.getJSONArray("items");
                String title = "", url = "", idVideo = "";

                for(int i = 0; i < jsonItems.length(); i++){
                    JSONObject jsonObjectItem = jsonItems.getJSONObject(i);
                    JSONObject jsonObjectSnippet = jsonObjectItem.getJSONObject("snippet");
                    title = jsonObjectSnippet.getString("title");

                    JSONObject jsonObjectThumbnail = jsonObjectSnippet.getJSONObject("thumbnails");

                    JSONObject jsonObjectMedium = jsonObjectThumbnail.getJSONObject("medium");

                    url = jsonObjectMedium.getString("url");

                    JSONObject jsonObjectResourceID = jsonObjectSnippet.getJSONObject("resourceId");

                    idVideo = jsonObjectResourceID.getString("videoId");

                    videoYoutubes.add(new VideoYoutube(title, url, idVideo));
                }

                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        },error -> {
            Toast.makeText(this,"ERROR!!", Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(jsonObject);
    }

//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//        youTubePlayer.cueVideo("da85qAflQoo");
//    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//        if(youTubeInitializationResult.isUserRecoverableError()){
//            youTubeInitializationResult.getErrorDialog(YoutubeActivity.this, REQUEST_CODE);
//            }
//        else {
//            Toast.makeText(this, "ERROR!!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == 123){
//            youTubePlayerView.initialize(API_KEY, this);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}