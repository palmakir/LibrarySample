package com.example.hp.internship_library;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URLEncoder;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    //Declaring EditText
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;

    //Send button
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button b5=(Button)findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //Initializing the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextName = (EditText) findViewById(R.id.editTextName);

        buttonSend = (Button) findViewById(R.id.buttonSend);

        //Adding click listener
        buttonSend.setOnClickListener(this);
    }

    void uploaddata(String name,String email, String pass)throws Exception
    {

        // Create data variable for sent values to server
        String data = URLEncoder.encode("name", "UTF-8")
                + "=" + URLEncoder.encode(name, "UTF-8");

        data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(email, "UTF-8");

        data += "&" + URLEncoder.encode("pass", "UTF-8")
                + "=" + URLEncoder.encode(pass, "UTF-8");
        // Send data
        try
        {

            SendData sm = new SendData(this,data,email){
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    //Showing a success message
                    if(flag==1) {
                        Toast.makeText(Main2Activity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(Main2Activity.this,"Sign Up Error! Kindly try again",Toast.LENGTH_LONG).show();
                    }
                }

            };
            sm.execute();
        }
        catch(Exception ex)
        {
            System.out.print("Background Task Error!");//Debugging assistance
        }

    }

    @Override
    public void onClick(View v) {

        try
        {

            CheckData ch = new CheckData(this,editTextEmail.getText().toString().trim()){
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    //Showing a success message
                    if(x==1) {
                        Toast.makeText(getApplicationContext(), "Already registered. Try logging in!", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        try{
                        uploaddata(editTextName.getText().toString(), editTextEmail.getText().toString(), editTextPassword.getText().toString());
                        }
                        catch (Exception e){
                            Toast.makeText(Main2Activity.this, "Error uploading data", Toast.LENGTH_LONG).show();
                        }
                        onBackPressed();
                    }
                }

            };
            ch.execute();
        }
        catch(Exception ex)
        {
            System.out.print("Background Task Error!");//Debugging assistance
        }
    }
}