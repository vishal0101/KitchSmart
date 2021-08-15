package com.example.kitchsmart;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static com.example.kitchsmart.R.drawable.ricegraphcolor;
import static com.example.kitchsmart.R.drawable.sugargraphcolor;

public class graphactivity extends AppCompatActivity {


    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;


    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;

    TextView itemName,usedData,remainData,feedback;

    String name;

    float wt,used,maxwt;
    int per,color;

    float pt1,pt2,pt3,pt4,pt5,pt6,pt7,pt8,pt9,pt10;

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphactivity);

        barChart = findViewById(R.id.barChart);
        itemName = findViewById(R.id.itemName);
        usedData = findViewById(R.id.usedData);
        remainData = findViewById(R.id.remainData);
        feedback = findViewById(R.id.feedback);

        lineChart = findViewById(R.id.lineChart);

        drawGraph();

       // refershGraph(1000);

    }

    private void drawGraph(){
        maxwt = getIntent().getFloatExtra("MAXWT",0);
        wt = getIntent().getFloatExtra("WT",0);
        name = getIntent().getStringExtra("NAME");
        color = getIntent().getIntExtra("COLOR",0);

        pt1 = getIntent().getFloatExtra("PT1",0);
        pt2 = getIntent().getFloatExtra("PT2",0);
        pt3 = getIntent().getFloatExtra("PT3",0);
        pt4 = getIntent().getFloatExtra("PT4",0);
        pt5 = getIntent().getFloatExtra("PT5",0);
        pt6 = getIntent().getFloatExtra("PT6",0);
        pt7 = getIntent().getFloatExtra("PT7",0);
        pt8 = getIntent().getFloatExtra("PT8",0);
        pt9 = getIntent().getFloatExtra("PT9",0);
        pt10 = getIntent().getFloatExtra("PT10",0);

        used = maxwt - wt;

        per = (int) (((wt)/maxwt)*100);

        if(per >= 70){
            feedback.setText("Filled (" + per + "%)");
        }

        else  if(per >= 30 && per <70){
            feedback.setText("Sufficient (" + per + "%)");
        }

        else  if(per < 30){
            feedback.setText("Refill Required! (" + per + "%)");
        }

        itemName.setText(name);
        usedData.setText("Used = "+ used +"g");
        remainData.setText("Remaining = "+ wt +"g");

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setDrawGridBackground(false);
        barChart.setTouchEnabled(false);

        getEntries();

        barDataSet = new BarDataSet(barEntries,"Data Set");
        barData = new BarData(barDataSet);

        barData.setBarWidth(0.9f);

        barChart.setData(barData);

        lineDataSet = new LineDataSet(barEntries,"Data Set");
        lineData = new LineData(lineDataSet);

        lineChart.setTouchEnabled(false);

        lineChart.setData(lineData);

        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueTextSize(16f);


        if(color == 1) {
            barDataSet.setColor(Color.rgb(62,202,0));
        }
        else if(color == 2){
            barDataSet.setColor(Color.rgb(62,202,0));
        }
        else if(color == 3){
            barDataSet.setColor(Color.rgb(62,202,0));
        }
        else{
            Toast.makeText(this, "No Color", Toast.LENGTH_SHORT).show();
        }
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(16f);
    }

    private void getEntries(){

        barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1f,pt1));
        barEntries.add(new BarEntry(2f,pt2));
        barEntries.add(new BarEntry(3f,pt3));
        barEntries.add(new BarEntry(4f,pt4));
        barEntries.add(new BarEntry(5f,pt5));
        barEntries.add(new BarEntry(6f,pt6));
        barEntries.add(new BarEntry(7f,pt7));
        barEntries.add(new BarEntry(8f,pt8));
        barEntries.add(new BarEntry(9f,pt9));
        barEntries.add(new BarEntry(10f,pt10));

    }

    public void refershGraph(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
                drawGraph();
            }
        };
        handler.postDelayed(runnable,milliseconds);
    }

}