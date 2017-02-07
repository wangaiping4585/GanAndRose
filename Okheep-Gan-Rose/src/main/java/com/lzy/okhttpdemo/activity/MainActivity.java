package com.lzy.okhttpdemo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lzy.okhttpdemo.R;
import com.lzy.okhttpdemo.fragment.DownloadFragment;
import com.lzy.okhttpdemo.fragment.OkhttpFragment;
import com.lzy.okhttpdemo.fragment.UploadFragment;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.widget.tab.PagerSlidingTabStrip;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Fragment> fragmentList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerSlidingTabStrip tab = (PagerSlidingTabStrip) findViewById(R.id.tab);//实例化（交互式-页面指示器）

        //把（一般请求Fragment）（下载管理Fragment）（上传管理Fragment），添加在集合
        fragmentList = new ArrayList<>();
        fragmentList.add(new OkhttpFragment());
        fragmentList.add(new DownloadFragment());
        fragmentList.add(new UploadFragment());
        //给ViewPager设置适配器
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tab.setViewPager(viewPager);//给页面指示器，添加ViewPager
    }
    //VeiwPager的适配器
    private class MyAdapter extends FragmentPagerAdapter {

        private String[] titles = {"一般请求", "下载管理", "上传管理"};

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        public CharSequence getPageTitle(int position) {//给页面指示器添加标题
            return titles[position];
        }
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        public int getCount() {
            return fragmentList.size();
        }
    }
    //当程序退出时，取消网络请求
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
