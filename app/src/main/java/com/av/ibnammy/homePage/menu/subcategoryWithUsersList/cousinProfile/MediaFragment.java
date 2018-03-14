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

import java.util.ArrayList;


public class MediaFragment extends Fragment {


    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SlideAdapter sliderPagerAdapter;
    ArrayList<String> slider_image_list;
    private TextView[] dots;
    int page_position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_media, container, false);

        vp_slider = (ViewPager) v.findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) v.findViewById(R.id.ll_dots);
        // method for initialisation
        init();

// method for adding indicators
        addBottomDots(0);

       /* final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);

*/
    return v;

}


    private void init() {



        slider_image_list = new ArrayList<>();

//Add few items to slider_image_list ,this should contain url of images which should be displayed in slider
// here i am adding few sample image links, you can add your own

        slider_image_list.add("http://images.all-free-download.com/images/graphiclarge/mountain_bongo_animal_mammal_220289.jpg");
        slider_image_list.add("http://images.all-free-download.com/images/graphiclarge/bird_mountain_bird_animal_226401.jpg");
        slider_image_list.add("http://images.all-free-download.com/images/graphiclarge/mountain_bongo_animal_mammal_220289.jpg");
        slider_image_list.add("http://images.all-free-download.com/images/graphiclarge/bird_mountain_bird_animal_226401.jpg");


        sliderPagerAdapter = new SlideAdapter(getActivity(), slider_image_list);
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



