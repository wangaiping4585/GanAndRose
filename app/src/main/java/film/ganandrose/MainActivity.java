package film.ganandrose;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lzy.okhttputils.OkHttpUtils;

import java.util.List;

import MyFragments.MyFragment_1;
import MyFragments.MyFragmentTwo;
import MyFragments.MyFragment_3;
import MyFragments.MyFragment_4;
import first_Pager_Utils.WiFi_IsOrNot;
import javabean.Films;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends FragmentActivity {

    public ViewPager mainViewpager;
    public RadioGroup radioGroup;
    public String films;
    public OkHttpClient client;
    public Request request;
    public Response response;
    public String filmJson;
    public List<Films> filmListHome;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpUtils.init(this);
        int netType = WiFi_IsOrNot.GetNetype(MainActivity.this);;
        System.out.println("==========当前网络状态是：============="+netType);
        if(netType == -1){
            Toast.makeText(this,"当前无网络，请检查，网络状态",Toast.LENGTH_SHORT).show();
        }else{
            initView();

        }


    }

    public void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        mainViewpager = (ViewPager) findViewById(R.id.mainViewPager);
        mainViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {//给Viewpager添加适配器
            public Fragment getItem(int position) {
               Fragment fragment = null;
                switch (position){
                    case 0:
                        fragment = new MyFragment_1();
                        break;
                    case 1:
                      fragment = new MyFragmentTwo();
                        break;
                    case 2:
                        fragment = new MyFragment_3();
                        break;
                    case 3:
                        fragment = new MyFragment_4();
                        break;
                }
                return fragment;
            }
            public int getCount() {
                return 4;
            }
        });
        mainViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            public void onPageSelected(int position) {
                    System.out.println("=====当前页数下标==="+position);
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                    radioButton.setChecked(true);
            }
            public void onPageScrollStateChanged(int state) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.RBtn1:
                        mainViewpager.setCurrentItem(0);
                        break;
                    case R.id.RBtn2:
                        mainViewpager.setCurrentItem(1);
                        break;
                    case R.id.RBtn3:
                        mainViewpager.setCurrentItem(2);
                        break;
                    case R.id.RBtn4:
                        mainViewpager.setCurrentItem(3);
                        break;

                }
            }
        });

    }

}
