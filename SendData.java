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
import java.net.URLConnection;
//import javax.mail.Session;

//Class is extending AsyncTask because this class is going to perform a networking operation
public class SendData extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Context context;
    //private Session session;

    //Information to send email
    private String data,email2;
    int flag=1, x=0;
    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public SendData(Context context, String data, String emailid){
        //Initializing variables
        this.context = context;
        this.data = data;
        this.email2=emailid;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while uploading data
        progressDialog = ProgressDialog.show(context,"Registering ","Please wait...",false,false);
        flag=0;

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

            URL url = new URL("https://pruthveshdesai.000webhostapp.com/add_info.php");
            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            // Get the server response
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }
            reader.close();
            wr.close();
            flag=1;
            return null;
        }
        catch (IOException e) {
            flag=0;
            e.printStackTrace();
        }
        return null;
    }
}