
package com.example.kitchsmart;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    Button btn1,btn2,btn3,btnRefresh;

    ProgressBar pb1,pb2,pb3;

    //Vishal Phone Vishal PC
      String url = "http://192.168.43.212:7882/Android/readData.php";

      //Sohan Phone Vishal PC
  //  String url = "http://192.168.43.82:7882/Android/readData.php";

    //Sohan Phone Sohan Pc
    //  String url = "http://192.168.43.8:7882/Android/readData.php";

    //Vishal Phone Sohan PC
   // String url = "http://192.168.43.184:7882/Android/readData.php";

    //Dinesh phone Dinesh PC
   // String url = "http://192.168.43.77/testcode/readData.php";


    String noti = "";

    int pro1, pro2,pro3;
    int len = 1;
    Float wt1,wt2,wt3,maxwt;
    private long backPressedtime;
    private Toast backToast;

    Float[] rice = new Float[20];
    Float[] dal = new Float[20];
    Float[] sugar = new Float[20];


    //main function
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Kitchsmart Notification","Kitchsmart Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager =getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        for(int i = 0;i<20;i++){
            rice[i]=0f;
            dal[i]=0f;
            sugar[i]=0f;
        }

       // getJSON("http://192.168.43.212:7882/Android/readData.php");
        getJSON(url);


        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btnRefresh = findViewById(R.id.btnRefresh);

        pb1 = findViewById(R.id.pb1);
        pb2 = findViewById(R.id.pb2);
        pb3 = findViewById(R.id.pb3);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGraph1();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGraph2();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGraph3();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getJSON("http://192.168.43.212:7882/Android/readData.php");
                getJSON(url);
                liveData();
            }
        });

        refershLive(1000);
        refershGraph(1000);

    }

    //graph1
        public void openGraph1() {
            Intent intent = new Intent(this,graphactivity.class);

            intent.putExtra("WT",wt1);
            intent.putExtra("MAXWT",maxwt);
            intent.putExtra("NAME","Rice");
            intent.putExtra("COLOR",1);

            intent.putExtra("PT1",rice[1]);
            intent.putExtra("PT2",rice[3]);
            intent.putExtra("PT3",rice[5]);
            intent.putExtra("PT4",rice[7]);
            intent.putExtra("PT5",rice[9]);
            intent.putExtra("PT6",rice[11]);
            intent.putExtra("PT7",rice[13]);
            intent.putExtra("PT8",rice[15]);
            intent.putExtra("PT9",rice[17]);
            intent.putExtra("PT10",rice[19]);

//            intent.putExtra("PT1",0.050);
//            intent.putExtra("PT2",0.078);
//            intent.putExtra("PT3",0.088);
//            intent.putExtra("PT4",0.012);
//            intent.putExtra("PT5",0.035);
//            intent.putExtra("PT6",0.055);
//            intent.putExtra("PT7",0.027);
//            intent.putExtra("PT8",0.098);
//            intent.putExtra("PT9",0.065);
//            intent.putExtra("PT10",0.075);

            startActivity(intent);
        }

        //graph2
    public void openGraph2() {
        Intent intent = new Intent(this,graphactivity.class);

        intent.putExtra("WT",wt2);
        intent.putExtra("MAXWT",maxwt);
        intent.putExtra("NAME","Dal");
        intent.putExtra("COLOR",2);


        intent.putExtra("PT1",dal[1]);
        intent.putExtra("PT2",dal[3]);
        intent.putExtra("PT3",dal[5]);
        intent.putExtra("PT4",dal[7]);
        intent.putExtra("PT5",dal[9]);
        intent.putExtra("PT6",dal[11]);
        intent.putExtra("PT7",dal[13]);
        intent.putExtra("PT8",dal[15]);
        intent.putExtra("PT9",dal[17]);
        intent.putExtra("PT10",dal[19]);

        startActivity(intent);
    }

    //graph3
    public void openGraph3() {
        Intent intent = new Intent(this,graphactivity.class);

        intent.putExtra("WT",wt3);
        intent.putExtra("MAXWT",maxwt);
        intent.putExtra("NAME","Sugar");
        intent.putExtra("COLOR",3);


        intent.putExtra("PT1",sugar[1]);
        intent.putExtra("PT2",sugar[3]);
        intent.putExtra("PT3",sugar[5]);
        intent.putExtra("PT4",sugar[7]);
        intent.putExtra("PT5",sugar[9]);
        intent.putExtra("PT6",sugar[11]);
        intent.putExtra("PT7",sugar[13]);
        intent.putExtra("PT8",sugar[15]);
        intent.putExtra("PT9",sugar[17]);
        intent.putExtra("PT10",sugar[19]);

        startActivity(intent);
    }

    //getting json
    private void getJSON(final String urlWebService){

        class GetJSON extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
              //  Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {

                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;

                    while ((json = bufferedReader.readLine()) != null){
                        sb.append(json + "\n");
                    }

                    return  sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }
            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }

    //making array
    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        len = jsonArray.length();
        for(int i = 0;i < jsonArray.length();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            rice[i] = Float.parseFloat(obj.getString("RICE"))*1000;
        }


        for(int i = 0;i < jsonArray.length();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            dal[i] = Float.parseFloat(obj.getString("DAL"))*1000;
        }


        for(int i = 0;i < jsonArray.length();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            sugar[i] = Float.parseFloat(obj.getString("SUGAR"))*1000;
        }

    }

        @SuppressLint("SetTextI18n")
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        void liveData(){

        wt1 = rice[len-1];
        wt2 = dal[len-1];
        wt3 = sugar[len-1];
        maxwt = 2000f;

        pro1 = (int) (((wt1)/maxwt)*100);
        pro2 = (int) (((wt2)/maxwt)*100);
        pro3 = (int) (((wt3)/maxwt)*100);

        btn1.setText("Rice "+"("+wt1+"g"+")");
        btn2.setText("Dal "+"("+wt2+"g"+")");
        btn3.setText("Sugar "+"("+wt3+"g"+")");

        if(pro1 < 10 )
        {
            noti = "For Rice";
        }
        if(pro2 < 10 )
        {
            noti = "For Dal";
        }
        if(pro3 < 10 )
        {
            noti = "For Sugar";
        }

        if(pro1 < 10 && pro2 < 10)
        {
           noti = "For Rice and Dal";
        }
        if(pro1 < 10 && pro3 < 10)
        {
            noti = "For Rice and Sugar";
        }
        if(pro2 < 10 && pro3 <10 )
        {
            noti = "For Dal and Sugar";
        }

        if(pro1 < 10 && pro2 <10 && pro3 < 10)
        {
            noti = "For Rice, Dal and Sugar";
        }

        //wt1
        if (pro1 >= 70) {
            pb1.setProgressDrawable(getResources().getDrawable(R.drawable.progresscolor1));
            pb1.setProgress(pro1);
        } else if (pro1 >= 10 && pro1 < 70) {
            if (pro1 == 50) {
                pro1 = 49;
            }
            pb1.setProgressDrawable(getResources().getDrawable(R.drawable.progresscolor2));
            pb1.setProgress(pro1);
        } else if (pro1 < 10) {
            if (pro1 <= 0) {
                pro1 = 1;
            }
            pb1.setProgressDrawable(getResources().getDrawable(R.drawable.progresscolor3));
            pb1.setProgress(pro1);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"Kitchsmart Notification");
            builder.setContentTitle("Refill Required!");
            builder.setContentText(noti);
            builder.setSmallIcon(R.drawable.kitchsmart_icon);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());

        }

        //wt2
        if (pro2 >= 70) {
            pb2.setProgressDrawable(getResources().getDrawable(R.drawable.progresscolor1));
            pb2.setProgress(pro2);
        } else if (pro2 >= 10 && pro2 < 70) {
            if (pro2 == 50) {
                pro2 = 49;
            }
            pb2.setProgressDrawable(getResources().getDrawable(R.drawable.progresscolor2));
            pb2.setProgress(pro2);
        } else if (pro2 < 10) {
            if (pro2 <= 0) {
                pro2 = 1;
            }
            pb2.setProgressDrawable(getResources().getDrawable(R.drawable.progresscolor3));
            pb2.setProgress(pro2);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"Kitchsmart Notification");
            builder.setContentTitle("Refill Required!");
            builder.setContentText(noti);
            builder.setSmallIcon(R.drawable.kitchsmart_icon);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());
        }

        //wt3
        if (pro3 >= 70) {
            pb3.setProgressDrawable(getResources().getDrawable(R.drawable.progresscolor1));
            pb3.setProgress(pro3);
        } else if (pro3 >= 10 && pro3 < 70) {
            if (pro3 == 50) {
                pro3 = 49;
            }
            pb3.setProgressDrawable(getResources().getDrawable(R.drawable.progresscolor2));
            pb3.setProgress(pro3);
        } else if (pro3 < 10) {
            if (pro3 <= 0) {
                pro3 = 1;
            }
            pb3.setProgressDrawable(getResources().getDrawable(R.drawable.progresscolor3));
            pb3.setProgress(pro3);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"Kitchsmart Notification");
            builder.setContentTitle("Refill Required!");
            builder.setContentText(noti);
            builder.setSmallIcon(R.drawable.kitchsmart_icon);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
            managerCompat.notify(1,builder.build());
        }
    }

    //timing function
    public void refershLive(int milliseconds){
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                 liveData();
            }
        };
        handler.postDelayed(runnable,milliseconds);
    }


    public void refershGraph(int milliseconds){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            public void run() {
               // getJSON("http://192.168.43.212:7882/Android/readData.php");
                getJSON(url);
            }
        };
        handler.postDelayed(runnable,milliseconds);
    }
    /*@Override
    public void onBackPressed() {

        if(backPressedtime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            backToast = Toast.makeText(this, "Press again", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedtime = System.currentTimeMillis();

    }*/
}