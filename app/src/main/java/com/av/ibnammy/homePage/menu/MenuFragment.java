package com.av.ibnammy.homePage.menu;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.av.ibnammy.R;
import com.av.ibnammy.databinding.FragmentMapBinding;
import com.av.ibnammy.databinding.FragmentMenuBinding;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {


    private FragmentMenuBinding fragmentMenuBinding ;
    private  TabLayout   tabLayout;
    private  ViewPager   viewPager;   // for swipe tap (DELETE) (0)
    private  ProgressBar loadingData;
    private  TextView    errorMessage;

    ArrayList<CategoryList> categoryListArrayList;
    CategoryList categoryList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMenuBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_menu,container,false);
        View rooView = fragmentMenuBinding.getRoot();

        //   viewPager = rooView.findViewById(R.id.viewpager);          // for swipe tap (DELETE) (1)
        //   tabLayout.setupWithViewPager(viewPager);                   //for swipe tap  (DELETE)  (2)
        //   setupViewPager(viewPager);                                 //for swipe tap  (DELETE)  (3)

        GetAllCategories();

        fragmentMenuBinding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showProgressBar();
                if(categoryList!=null){
                    hideProgressBar();
                    categoryList = categoryListArrayList.get(fragmentMenuBinding.tabs.getSelectedTabPosition());
                    changeFragmentForTab(new CategoriesFragment(),categoryList.getCategoryArrayList(),fragmentMenuBinding.tabs.getSelectedTabPosition());
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rooView;
    }

    private void GetAllCategories() {
      showProgressBar();
      MenuModel.GetAllCategory(new GetCallBack.CategoryCallBack() {
            @Override
            public void onSuccess(ArrayList<CategoryType> categoryTypeArrayList,ArrayList<CategoryList> categoryLists) {
                hideProgressBar();
                if(categoryTypeArrayList!=null){
                    for(int i=0;i<categoryTypeArrayList.size();i++){
                        CategoryType categoryType = categoryTypeArrayList.get(i);
                        fragmentMenuBinding.tabs.addTab(fragmentMenuBinding.tabs.newTab().setText(categoryType.getCategoryType()),i);
                    }
                }

                if(categoryLists!=null){
                        hideProgressBar();
                        categoryListArrayList = categoryLists;
                        categoryList = categoryLists.get(fragmentMenuBinding.tabs.getSelectedTabPosition());
                        changeFragmentForTab(new CategoriesFragment(),categoryList.getCategoryArrayList(),fragmentMenuBinding.tabs.getSelectedTabPosition());

                }


            }

            @Override
            public void onFailure(String throwable) {
               if(getActivity()!=null)
               {
                    fragmentMenuBinding.txtNoData.setText(getString(R.string.error_no_data_found));
                    hideProgressBar();
               }

            }

        });
    }

     //  for swipe tap (DELETE)  (4)
     /*   private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(new CategoriesFragment(), "موظفين",1);
            adapter.addFragment(new CategoriesFragment(), "غير موظفين",2);
            adapter.addFragment(new CategoriesFragment(), "اصحاب اعمال/حرف",3);
            viewPager.setAdapter(adapter);
      }

        class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        public void addFragment(Fragment fragment, String title,int type) {
            Bundle bundle = new Bundle();
            bundle.putInt("CategoryType",type);
            fragment.setArguments(bundle);
            mFragmentTitleList.add(title);
            mFragmentList.add(fragment);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
     */
   private void changeFragmentForTab(Fragment targetFragment,ArrayList<Category> categories,int tabPosition) {
        try {

            Bundle bundle = new Bundle();
            bundle.putInt("CategoryType",tabPosition);
            bundle.putSerializable("CategoryList",categories);
            targetFragment.setArguments(bundle);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, targetFragment, "fragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showProgressBar(){
       fragmentMenuBinding.pbLoading.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
       fragmentMenuBinding.pbLoading.setVisibility(View.GONE);
    }
}
