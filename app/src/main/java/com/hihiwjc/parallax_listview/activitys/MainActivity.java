package com.hihiwjc.parallax_listview.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.hihiwjc.parallax_listview.R;
import com.hihiwjc.parallax_listview.ui.ParallaxListView;

import java.util.UUID;

public class MainActivity extends Activity {
    /*MainListView*/
    private ParallaxListView parallaxListView_main = null;
    private View lv_main_header = null;
    private ImageView lv_main_header_image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fillData();
    }

    private void initView() {
        //initial the listView and header
        parallaxListView_main = (ParallaxListView) findViewById(R.id.listView_main);
        lv_main_header = (View) View.inflate(MainActivity.this, R.layout.view_mainlistiew_header, null);
        lv_main_header_image = (ImageView) lv_main_header.findViewById(R.id.listView_header_image);
        parallaxListView_main.addHeaderView(lv_main_header, null, false);
        parallaxListView_main.setOverScrollMode(View.OVER_SCROLL_NEVER);
        lv_main_header.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                parallaxListView_main.setmHeader(lv_main_header_image);
                lv_main_header.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


    }

    private void fillData() {
        String[] datas = new String[50];
        for (int i = 0; i < 50; i++) {
            datas[i] = UUID.randomUUID().toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, datas);
        parallaxListView_main.setAdapter(adapter);
    }
}
