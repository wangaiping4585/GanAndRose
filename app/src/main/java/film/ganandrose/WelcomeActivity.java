package film.ganandrose;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import MyViewPagersAdapter.Welcome_Activity_Adapter;

/**
 * Created by p on 2016/10/31.
 */
public class WelcomeActivity extends AppCompatActivity {
    public LinearLayout welcomeLinearLoyout;
    public ViewPager welcomeViewPager;
    public Button welcomeBtn;
    public ImageView imageViewPoint;
    public  SharedPreferences sp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        //初始化缓存
        sp = getSharedPreferences("config", MODE_PRIVATE);
        Boolean appState =  sp.getBoolean("falg",false);
        if(appState){
            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //初始化布局控件
        welcomeLinearLoyout = (LinearLayout) findViewById(R.id.welcomeLinearLayout);
        welcomeViewPager = (ViewPager) findViewById(R.id.welcomeViewPager);
        welcomeBtn = (Button) findViewById(R.id.comeinBtn);
        initView();//调用---初始化数据

    }
    //初始化数据
    public void initView(){
        final int[] images = {R.drawable.welcome_1,R.drawable.welcome_2,R.drawable.welcome_3,R.drawable.welcome_4};
        //初始化小点
        for(int i = 0 ;i<images.length;i++){
            imageViewPoint = new ImageView(this);
            if(i==0){
                imageViewPoint.setImageResource(R.drawable.point_huang);
            }else{
                imageViewPoint.setImageResource(R.drawable.point_lan);
            }
            //设置小点的边距和大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            params.setMargins(10,10,10,30);
            welcomeLinearLoyout.addView(imageViewPoint,params);
        }

        //给viewPager设置适配器
        welcomeViewPager.setAdapter(new Welcome_Activity_Adapter(this,images));
        //给ViewPager设置---滑动监听事件
        welcomeViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            public void onPageSelected(int position) {
                    //改变小点的颜色
                    for(int i = 0;i<welcomeLinearLoyout.getChildCount();i++){
                        ImageView viewPoint = (ImageView) welcomeLinearLoyout.getChildAt(i);
                        if(i==position){
                            viewPoint.setImageResource(R.drawable.point_huang);
                        }else {
                            viewPoint.setImageResource(R.drawable.point_lan);
                        }
                    }
                    //设置按钮的显示或隐藏
                    if(position==images.length-1){
                        welcomeBtn.setVisibility(View.VISIBLE);
                    }else {
                        welcomeBtn.setVisibility(View.GONE);
                    }
            }
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //Button点击事件，进入应用
    public void comeInApp(View view){
        System.out.println("======================进入应用======================");
        //进入app应用时，改变应用引导页的阅读状态
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("falg",true);
        editor.commit();
        Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onDestroy() {
        finish();
        super.onDestroy();

    }
}
