package com.av.ibnammy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Test
        // Edit by Aya
        // 12/14/2014
        //mina
    }

    void HiAya()
    {
        Toast.makeText(this,"Hi Aya",Toast.LENGTH_LONG).show();
    }

    void HiMina()
    {
        Toast.makeText(this,"Welcome Back Mina",Toast.LENGTH_LONG).show();
    }
    void HiAya2()
    {
        Toast.makeText(this,"Hi Aya",Toast.LENGTH_LONG).show();
    }

    void HiMina2()
    {
        Toast.makeText(this,"Welcome Back Mina",Toast.LENGTH_LONG).show();
    }
}
