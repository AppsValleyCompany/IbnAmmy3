package com.av.ibnammy.homePage.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.av.ibnammy.R;

/**
 * Created by Aya on 1/1/2018.
 */

public class ChatFragment extends Fragment {

   //private SwipeMenuListView swipeChatList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rooView = inflater.inflate(R.layout.fragment_chat, container, false);
/*
        swipeChatList = rooView.findViewById(R.id.listChat);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {


                // create "Delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());

                deleteItem.setBackground(new ColorDrawable(getResources().getColor(R.color.bgSwipeMenuDelete)));
                deleteItem.setIcon(R.mipmap.ic_delete);
                deleteItem.setWidth(dp2px(60));
                menu.addMenuItem(deleteItem);

                // create "Block" item
                SwipeMenuItem blockItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());

                blockItem.setBackground(new ColorDrawable(getResources().getColor(R.color.bgSwipeMenuList)));
                blockItem.setIcon(R.mipmap.ic_block);
                blockItem.setWidth(dp2px(60));

                menu.addMenuItem(blockItem);

                // create "Call" item
                SwipeMenuItem callItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());

                callItem.setBackground(new ColorDrawable(getResources().getColor(R.color.bgSwipeMenuList)));
                callItem.setIcon(R.mipmap.ic_call);
                callItem.setWidth(dp2px(60));
                menu.addMenuItem(callItem);

                // create "Chat" item
                SwipeMenuItem chatItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());

                chatItem.setBackground(new ColorDrawable(getResources().getColor(R.color.bgSwipeMenuList)));
                chatItem.setIcon(R.mipmap.ic_chat);
                chatItem.setWidth(dp2px(60));
                menu.addMenuItem(chatItem);



            }
        };

        swipeChatList.setMenuCreator(creator);
        swipeChatList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View setView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_chat,viewGroup,false);

                return setView;
            }
        });

         // Left
        swipeChatList.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);*/


        return rooView;
    }


   /* private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }*/
}
