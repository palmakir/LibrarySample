package com.example.hp.internship_library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        int[] pics={R.drawable.img1,R.drawable.img2,R.drawable.img3};
        String books[]={"Book1","Book2","Book3"};
        ListAdapter bookadap=new CustomAdapter(this, books,pics);
        ListView booklist=(ListView)findViewById(R.id.booklist);
        booklist.setAdapter(bookadap);
    }
}
