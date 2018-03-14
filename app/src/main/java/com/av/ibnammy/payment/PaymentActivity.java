package com.av.ibnammy.payment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.av.ibnammy.R;

public class PaymentActivity extends AppCompatActivity {

    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        set_fragment(PaymentFragment1.class);
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void set_fragment(Class fragmentClass)
    {
        if (fragmentClass!=null) {
            try {
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_holder, (Fragment) fragmentClass.newInstance()).commit();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }
}
