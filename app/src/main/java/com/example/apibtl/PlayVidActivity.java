 package com.example.apibtl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

 public class PlayVidActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerView youTubePlayerView;
    String id = "";
    int REQUEST_CODE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_vid);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.myYoutubePlay);

        Intent intent = getIntent();
        id = intent.getStringExtra("idVid");
        youTubePlayerView.initialize(YoutubeActivity.API_KEY, this);

    }

     @Override
     public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(id);
        youTubePlayer.setFullscreen(true);
     }

     @Override
     public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(PlayVidActivity.this, REQUEST_CODE);

        }
        else{
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            youTubePlayerView.initialize(YoutubeActivity.API_KEY, this);
        }
        super.onActivityResult(requestCode, resultCode, data);
     }
 }