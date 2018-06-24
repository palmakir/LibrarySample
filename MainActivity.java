package com.example.hp.internship_library;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button b1;
    TextView t1,t2,err;
    EditText e1,e2;
    String ee1,ee2;
    String datae,datap,datan;
    private ProgressDialog progressDialog;
    private PopupWindow mPopupWindow3;
    private RelativeLayout mRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        b1=findViewById(R.id.b1);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        err=findViewById(R.id.err);
        e1=findViewById(R.id.e1);
        e2=findViewById(R.id.e2);
        mRelativeLayout=findViewById(R.id.mRelativeLayout);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sign Up
                Intent i=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Forgot Password
                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.custom_layout,null);
                mPopupWindow3 = new PopupWindow(
                        customView,
                        ActionBarOverlayLayout.LayoutParams.WRAP_CONTENT,
                        ActionBarOverlayLayout.LayoutParams.WRAP_CONTENT
                );

                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow3.setElevation(5.0f);
                }
                mPopupWindow3.setFocusable(true);
                mPopupWindow3.update();

                mPopupWindow3.showAtLocation(mRelativeLayout, Gravity.CENTER, 0, -140);
                // Get a reference for the custom view close button
                Button sendp = customView.findViewById(R.id.sendp);
                Button canp = customView.findViewById(R.id.canp);
                final EditText fpem=customView.findViewById(R.id.fpem);
                canp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPopupWindow3.dismiss();
                    }
                });

                sendp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try
                        {

                            CheckData ch = new CheckData(MainActivity.this,fpem.getText().toString().trim()){
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);
                                    if(x==1) {
                                        //Toast.makeText(getApplicationContext(), "Sending logging details to you!", Toast.LENGTH_LONG).show();
                                        datae=fpem.getText().toString();
                                        getJSON("https://pruthveshdesai.000webhostapp.com/receive.php",1);
                                    }
                                    else
                                    {
                                            Toast.makeText(MainActivity.this, "Email ID not registered yet", Toast.LENGTH_LONG).show();
                                    }
                                }

                            };
                            ch.execute();
                        }
                        catch(Exception ex)
                        {
                            Toast.makeText(getApplicationContext(),ex+"",Toast.LENGTH_LONG).show();
                            System.out.print("Background Task Error!");//Debugging assistance
                        }


                    mPopupWindow3.dismiss();
                    }
                });
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sign In
                try {
                    ee1=e1.getText().toString();
                    ee2=e2.getText().toString();
                    getJSON("https://pruthveshdesai.000webhostapp.com/receive.php",0);
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this,"Internet Connection not available",Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    private void getJSON(final String urlWebService,final int i) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if(i==0)
                progressDialog = ProgressDialog.show(MainActivity.this,"Logging In ","Please wait...",false,false);
                else
                    progressDialog = ProgressDialog.show(MainActivity.this,"Fetching Data ","Please wait...",false,false);

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    if(s!=null){
                        if(i==0)
                        datainput(s);
                        else
                            senddata(s);
                    }
                    else
                        System.out.println(1/0);//To throw exception on purpose
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Data connection not available", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
                progressDialog.dismiss();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                        System.out.println(json.toString());
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void datainput(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] names = new String[jsonArray.length()];
        String[] email = new String[jsonArray.length()];
        String[] pass = new String[jsonArray.length()];
        int flag=0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            names[i] = obj.getString("name");
            email[i]=obj.getString("email");
            pass[i]=obj.getString("pass");
            if(email[i].equalsIgnoreCase(ee1)&&pass[i].equals(ee2))
            {
                flag=1;
                Toast.makeText(getApplicationContext(), "Valid Credentials", Toast.LENGTH_LONG).show();
                Intent i2=new Intent(MainActivity.this,Main3Activity.class);
                startActivity(i2);
                break;
            }
        }
        if(flag==0) {
            Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
            err.setText(" Invalid Credentials");
        }
    }



    private void sendEmail(String email,String pass,String name) {
        SendMail sm = new SendMail(this,email,pass,name){
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //Showing a success message
                if(flag==1) {
                    Toast.makeText(MainActivity.this, "Credentials Sent", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Error while sending credentials!",Toast.LENGTH_LONG).show();
                }
            }

        };
        sm.execute();

    }


    private void senddata(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] names = new String[jsonArray.length()];
        String[] email = new String[jsonArray.length()];
        String[] pass = new String[jsonArray.length()];
        int flag=0;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            names[i] = obj.getString("name");
            email[i]=obj.getString("email");
            pass[i]=obj.getString("pass");
            if(email[i].equalsIgnoreCase(datae))
            {
                flag=1;
                datae=email[i];
                datap=pass[i];
                datan=names[i];
                break;
            }
        }
        if(flag==0) {
            Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
            err.setText(" Invalid Credentials");
        }
        else
        {
            Toast.makeText(getApplicationContext(),datan+", we are sending you your credentials",Toast.LENGTH_LONG).show();
            sendEmail(datae,datap,datan);
            err.setText("Check your mail for credentials");
        }
    }

}



