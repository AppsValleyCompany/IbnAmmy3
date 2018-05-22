package com.av.ibnammy.payment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.av.ibnammy.R;

public class PaymentFragment1 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayout add_relative_layout;
    Button add_relative_btn,btn_add,btn1_pay_dutes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_payment_fragment1, container, false);
        recyclerView=v.findViewById(R.id.recycle_view);

        final PaymentListAdapter adapter=new PaymentListAdapter();
        mLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        add_relative_layout=v.findViewById(R.id.add_relative_layout);

        add_relative_btn=v.findViewById(R.id.btn_add_relative_pay_dues);
        add_relative_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_relative_layout.setVisibility(View.VISIBLE);
            }
        });

        btn_add=v.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_relative_layout.setVisibility(View.GONE);

            }
        });
        btn1_pay_dutes=v.findViewById(R.id.btn1_pay_dutes);
        btn1_pay_dutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_fragment(PaymentFragment2.class);
            }
        });
        return v;
    }

    public void set_fragment(Class fragmentClass)
    {
        if (fragmentClass!=null) {
            try {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frag_holder, (Fragment) fragmentClass.newInstance()).addToBackStack("pay").commit();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

}
