package com.example.hp.internship_library;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
public class CheckData extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Context context;

    //Information to send email
    private String data,email2;
    int flag=1, x=0;
    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public CheckData(Context context, String emailid){
        //Initializing variables
        this.context = context;
        this.email2=emailid;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while uploading data
        progressDialog = ProgressDialog.show(context,"Checking credentials ","Please wait...",false,false);
        x=0;flag=1;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            URL url = new URL("https://pruthveshdesai.000webhostapp.com/receive.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb2 = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String json;
            while ((json = bufferedReader.readLine()) != null) {
                sb2.append(json + "\n");
                System.out.println(json.toString());
            }
            try{
                JSONArray jsonArray = new JSONArray(sb2.toString());
                String[] email = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    email[i]=obj.getString("email");
                    if(email[i].equalsIgnoreCase(email2))
                    {
                        x=1;
                        x=1/0;
                        break;
                    }
                }}
            catch (Exception e)
            {
                System.out.println(""+e);
            }
            return null;
        }
        catch (IOException e) {
            flag=0;
            e.printStackTrace();
        }
        return null;
    }
}