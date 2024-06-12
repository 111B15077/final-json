package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class more_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String sitename = extras.getString("sitename");
            String county = extras.getString("county");
            String aqi = extras.getString("aqi");
            String status = extras.getString("status");
            String publishtime = extras.getString("publishtime");

            TextView tvSitename = findViewById(R.id.tvMoreLoca);
            TextView tvCounty = findViewById(R.id.tvMoreArea);
            TextView tvAqi = findViewById(R.id.tvMoreTime);
            TextView tvStatus = findViewById(R.id.tvMoreTempature);
            TextView tvPublishtime = findViewById(R.id.textView2);

            tvSitename.setText("測站名稱: " + sitename);
            tvCounty.setText("縣市: " + county);
            tvAqi.setText("空氣品質指標: " + aqi);
            tvStatus.setText("狀態: " + status);
            tvPublishtime.setText("時間: " + publishtime);
        }
    }

    public void goBack(View view) {
        finish();
    }
}