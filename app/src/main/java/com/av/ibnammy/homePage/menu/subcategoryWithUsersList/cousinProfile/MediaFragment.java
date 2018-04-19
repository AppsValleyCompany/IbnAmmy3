package com.av.ibnammy.homePage.menu.subcategoryWithUsersList.cousinProfile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.av.ibnammy.R;
import com.av.ibnammy.homePage.menu.subcategoryWithUsersList.CousinAccount;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.av.ibnammy.networkUtilities.ApiClient.IMG_URL;
import static com.av.ibnammy.networkUtilities.ApiClient.WORK_MEDIA_URL;


public class MediaFragment extends Fragment {


    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SlideAdapter sliderPagerAdapter;
    ArrayList<String> slider_image_list;
    private TextView[] dots;
    int page_position = 0;
    private  CousinAccount cousinAccount;
    private TextView tvNoData;
    ArrayList<String> imgUrl = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_media, container, false);

        vp_slider =  v.findViewById(R.id.vp_slider);
        ll_dots   =  v.findViewById(R.id.ll_dots);
        tvNoData  =  v.findViewById(R.id.tv_no_data_found);

        // method for initialisation
        cousinAccount = (CousinAccount) getArguments().getSerializable("CousinData");

        if(!cousinAccount.getWork_IMG1().equals("")){
            imgUrl.add(cousinAccount.getWork_IMG1());
        }
        if(!cousinAccount.getWork_IMG2().equals("")){
            imgUrl.add(cousinAccount.getWork_IMG2());
        }


        if(!cousinAccount.getWork_IMG3().equals("")){
            imgUrl.add(cousinAccount.getWork_IMG3());
        }


        if(!cousinAccount.getWork_IMG4().equals("")){
            imgUrl.add(cousinAccount.getWork_IMG4());
        }


        if(!cousinAccount.getWork_IMG5().equals("")){
            imgUrl.add(cousinAccount.getWork_IMG5());
        }

        if(imgUrl.size()!=0){
            init();
            tvNoData.setVisibility(View.GONE);
        }else {
            tvNoData.setVisibility(View.VISIBLE);

        }





     return v;

}


    private void init() {


       // String workImageUrl = WORK_MEDIA_URL + cousinAccount.getWork_IMG1();

        slider_image_list = new ArrayList<>();

//Add few items to slider_image_list ,this should contain url of images which should be displayed in slider
// here i am adding few sample image links, you can add your o

     //   slider_image_list.add(imgUrl);
      //  slider_image_list.add("http://images.all-free-download.com/images/graphiclarge/bird_mountain_bird_animal_226401.jpg");


        sliderPagerAdapter = new SlideAdapter(getActivity(), imgUrl);
        vp_slider.setAdapter(sliderPagerAdapter);

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        addBottomDots(0);

    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[slider_image_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getContext());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
    }
}



