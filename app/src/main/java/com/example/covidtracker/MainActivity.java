package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.covidtracker.api.ApiUtilities;
import com.example.covidtracker.api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView totalRecovered, totalConfirmed, totalTests, totalDeath, totalActive, updatedAt, tvUsername;
    private List<CountryData> list;
    private PieChart pieChart;
    ImageView hospitalImage, oxygenImage, pharmacyImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        totalConfirmed = findViewById(R.id.totalConfirm);
        totalActive = findViewById(R.id.totalActive);
        totalDeath = findViewById(R.id.totalDeath);
        totalTests = findViewById(R.id.totalTests);
        totalRecovered = findViewById(R.id.totalRecovered);
        pieChart = findViewById(R.id.piechart);
        updatedAt = findViewById(R.id.updatedAt);
        tvUsername = findViewById(R.id.tvUsername);
        pharmacyImage = findViewById(R.id.pharmacyImage);
        hospitalImage = findViewById(R.id.hospitalImage);
        oxygenImage = findViewById(R.id.oxygenImage);

        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        String value = getShared.getString("str", "");
        tvUsername.setText(value);

        ApiUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        list.addAll(response.body());
                        for(int i=0;i<list.size(); i++){
                            if(list.get(i).getCountry().equals("India")){
                                int confirmNo = Integer.parseInt(list.get(i).getCases());
                                int activeNo = Integer.parseInt(list.get(i).getActive());
                                int recoveredNo = Integer.parseInt(list.get(i).getRecovered());
                                int deathNo = Integer.parseInt(list.get(i).getDeaths());
                                int testNo = Integer.parseInt(list.get(i).getTests());

                                Log.d("Nitesh2", String.valueOf(confirmNo));
                                totalConfirmed.setText(NumberFormat.getInstance().format(confirmNo));
                                totalActive.setText(NumberFormat.getInstance().format(activeNo));
                                totalRecovered.setText(NumberFormat.getInstance().format(recoveredNo));
                                totalDeath.setText(NumberFormat.getInstance().format(deathNo));
                                totalTests.setText(NumberFormat.getInstance().format(testNo));

                                setText(list.get(i).getUpdated());

                                pieChart.addPieSlice(new PieModel("Confirm", confirmNo, Color.parseColor("#ffa500")));
                                pieChart.addPieSlice(new PieModel("Active", activeNo, Color.parseColor("#0080ff")));
                                pieChart.addPieSlice(new PieModel("Death", deathNo, Color.parseColor("#ff0000")));
                                pieChart.addPieSlice(new PieModel("Recovered", recoveredNo, Color.parseColor("#008000")));

                                pieChart.startAnimation();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        hospitalImage.setOnClickListener(v -> {
         Intent i2 = new Intent(getApplicationContext(), WebActivity.class);
         startActivity(i2);

        });

        oxygenImage.setOnClickListener(v -> {
            Intent i3 = new Intent(getApplicationContext(), WebActivity2.class);
            startActivity(i3);

        });
        pharmacyImage.setOnClickListener(v -> {
            Intent i4 = new Intent(getApplicationContext(), WebActivity3.class);
            startActivity(i4);

        });
    }

    private void setText(String updated) {
    DateFormat dateFormat = new SimpleDateFormat("dd MMM HH:mm aa");
    long milliseconds = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        updatedAt.setText("Last updated at "+dateFormat.format(calendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1 :
                refreshData();
                Toast.makeText(getApplicationContext(), "Data refreshed Successfully", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item2 :
                SharedPreferences shrd = getSharedPreferences("demo", MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putString("str", "");
                editor.apply();
                Intent i = new Intent(MainActivity.this, FirstScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

                Toast.makeText(getApplicationContext(), "Logout Successfully" , Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshData() {
        ApiUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        list.clear();
                        pieChart.clearChart();
                        list.addAll(response.body());
                        for(int i=0;i<list.size(); i++){
                            if(list.get(i).getCountry().equals("India")){
                                int confirmNo = Integer.parseInt(list.get(i).getCases());
                                int activeNo = Integer.parseInt(list.get(i).getActive());
                                int recoveredNo = Integer.parseInt(list.get(i).getRecovered());
                                int deathNo = Integer.parseInt(list.get(i).getDeaths());
                                int testNo = Integer.parseInt(list.get(i).getTests());

                                Log.d("Nitesh2", String.valueOf(confirmNo));
                                totalConfirmed.setText(NumberFormat.getInstance().format(confirmNo));
                                totalActive.setText(NumberFormat.getInstance().format(activeNo));
                                totalRecovered.setText(NumberFormat.getInstance().format(recoveredNo));
                                totalDeath.setText(NumberFormat.getInstance().format(deathNo));
                                totalTests.setText(NumberFormat.getInstance().format(testNo));

                                setText(list.get(i).getUpdated());
                          
                                pieChart.addPieSlice(new PieModel("Confirm", confirmNo, Color.parseColor("#ffa500")));
                                pieChart.addPieSlice(new PieModel("Active", activeNo, Color.parseColor("#0080ff")));
                                pieChart.addPieSlice(new PieModel("Death", deathNo, Color.parseColor("#ff0000")));
                                pieChart.addPieSlice(new PieModel("Recovered", recoveredNo, Color.parseColor("#008000")));
                                pieChart.startAnimation();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}