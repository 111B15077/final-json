package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName() + "My";
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catchData();
    }

    private void catchData() {
        String catchData = "https://data.moenv.gov.tw/api/v2/aqx_p_432?api_key=e8dd42e6-9b8b-43f8-991e-b3dee723a52d&limit=1000&sort=ImportDate%20desc&format=JSON";
        ProgressDialog dialog = ProgressDialog.show(this, "讀取中", "請稍候", true);
        new Thread(() -> {
            try {
                URL url = new URL(catchData);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    json.append(line);
                }

                JSONObject jsonObject = new JSONObject(json.toString());

                String sitename = jsonObject.getString("sitename");
                String county = jsonObject.getString("county");
                String aqi = jsonObject.getString("aqi");
                String status = jsonObject.getString("status");
                String publishtime = jsonObject.getString("publishtime");

                // 新增的數據
                String pollutant = jsonObject.getString("pollutant");
                String so2 = jsonObject.getString("so2");
                String co = jsonObject.getString("co");
                String o3 = jsonObject.getString("o3");
                String o3_8hr = jsonObject.getString("o3_8hr");
                String pm10 = jsonObject.getString("pm10");
                String pm25 = jsonObject.getString("pm2.5");
                String no2 = jsonObject.getString("no2");
                String nox = jsonObject.getString("nox");
                String no = jsonObject.getString("no");
                String wind_speed = jsonObject.getString("wind_speed");
                String wind_direc = jsonObject.getString("wind_direc");
                String co_8hr = jsonObject.getString("co_8hr");
                String pm25_avg = jsonObject.getString("pm2.5_avg");
                String pm10_avg = jsonObject.getString("pm10_avg");
                String so2_avg = jsonObject.getString("so2_avg");
                String longitude = jsonObject.getString("longitude");
                String latitude = jsonObject.getString("latitude");
                String siteid = jsonObject.getString("siteid");

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("sitename", sitename);
                hashMap.put("county", county);
                hashMap.put("aqi", aqi);
                hashMap.put("status", status);
                hashMap.put("publishtime", publishtime);

                // 新增的數據
                hashMap.put("pollutant", pollutant);
                hashMap.put("so2", so2);
                hashMap.put("co", co);
                hashMap.put("o3", o3);
                hashMap.put("o3_8hr", o3_8hr);
                hashMap.put("pm10", pm10);
                hashMap.put("pm2.5", pm25);
                hashMap.put("no2", no2);
                hashMap.put("nox", nox);
                hashMap.put("no", no);
                hashMap.put("wind_speed", wind_speed);
                hashMap.put("wind_direc", wind_direc);
                hashMap.put("co_8hr", co_8hr);
                hashMap.put("pm2.5_avg", pm25_avg);
                hashMap.put("pm10_avg", pm10_avg);
                hashMap.put("so2_avg", so2_avg);
                hashMap.put("longitude", longitude);
                hashMap.put("latitude", latitude);
                hashMap.put("siteid", siteid);

                arrayList.add(hashMap);

                Log.d(TAG, "catchData: " + arrayList);

                runOnUiThread(() -> {
                    dialog.dismiss();
                    RecyclerView recyclerView;
                    MyAdapter myAdapter;
                    recyclerView = findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
                    myAdapter = new MyAdapter();
                    recyclerView.setAdapter(myAdapter);
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }).start();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public class ViewHolder extends RecyclerView.ViewHolder {
            public BreakIterator tvMoreTime;
            TextView tvLoc, tvAr, tvDataTime, tvTemp;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvLoc = itemView.findViewById(R.id.tvMoreLoca);
                tvAr = itemView.findViewById(R.id.tvMoreArea);
                tvDataTime = itemView.findViewById(R.id.tvMoreTime);
                tvTemp = itemView.findViewById(R.id.tvMoreTempature);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_data_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HashMap<String, String> item = arrayList.get(position);
            holder.tvLoc.setText("測站名稱：" + item.get("sitename"));
            holder.tvAr.setText("縣市：" + item.get("county"));
            holder.tvDataTime.setText("空氣品質指標：" + item.get("aqi"));
            holder.tvTemp.setText("狀態：" + item.get("status"));
            holder.tvMoreTime.setText("時間：" + item.get("publishtime"));


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), more_info.class);
                    intent.putExtra("sitename", item.get("sitename"));
                    intent.putExtra("county", item.get("county"));
                    intent.putExtra("aqi", item.get("aqi"));
                    intent.putExtra("status", item.get("status"));
                    intent.putExtra("publishtime", item.get("publishtime"));
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }
}


