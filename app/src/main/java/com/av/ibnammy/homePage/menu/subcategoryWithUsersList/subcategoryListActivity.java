package com.av.ibnammy.homePage.menu.subcategoryWithUsersList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.menu.MenuListAdapter;


public class subcategoryListActivity extends AppCompatActivity {



    TextView cTitle;
    ImageView cBack;
    RecyclerView recyclerViewCategory;
    MenuListAdapter menuListAdapter;
    static FragmentManager manager= null;
    static String setTagOfFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
         manager=getSupportFragmentManager();

        setTagOfFragment = getIntent().getStringExtra("TypeOfCategory");

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
       // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        cTitle = findViewById(R.id.txt_title);
        cBack = findViewById(R.id.tv_back);
        cBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
        cTitle.setText(getIntent().getStringExtra("CategoryName"));

       changeFragmentForSideMenu(new SubCategoryFragment());

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    static public void changeFragmentForSideMenu(Fragment targetFragment){
        // setTypeOfCategory
        Bundle bundle_category = new Bundle();
        bundle_category.putString("typeOfCategory",setTagOfFragment);
        targetFragment.setArguments(bundle_category);
        manager.beginTransaction()
                .replace(R.id.sub_cat_container,targetFragment, setTagOfFragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

                .commit();
    }
}
