package MyFragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import MyListViewsAdapter.Fragment_1_Listview_Adapter;
import MyViewPagersAdapter.MyFragment_1_Viewpager_Adapter;
import film.ganandrose.R;
import first_Pager_Utils.Request_ZiFuChuan;
import first_Pager_Utils.ZiFuChuan_ToJson;
import javabean.BaiSiBuDeQiJieBean;
import javabean.MyFragment_Item_Bean;

/**
 * Created by p on 2016/11/2.
 */
public class MyFragment_1 extends Fragment {

    private  Context mContext;
    public  View view;
    public View headView;
    public  ListView firstFragmentListView;
    public ViewPager firstFragmentVp;
    public LinearLayout laynear;
    public List<MyFragment_Item_Bean> viewPagerList;
    private static final int AUTO_SCROLL = 0;
    public static final int First_Num = 1;
    public int currentNum;


    //用Handler处理接收的消息
    Handler handler = new Handler(){
        public void handleMessage(Message msg) {

            if(msg.what==AUTO_SCROLL){
                //获取当前viewpager所在索引位置
                int currentItemIndex = firstFragmentVp.getCurrentItem();
                //让当前viewpager索引值，自动加1
                currentItemIndex++;
                //然后重要设置，图片的位置
                firstFragmentVp.setCurrentItem(currentItemIndex);
                //从新调用，handler发送消息，使得延时轮播，继续，自己调自己
                handler.sendEmptyMessageDelayed(AUTO_SCROLL,2000);
            }
        }
    };
    Handler handler2 = new Handler(){
        public void handleMessage(Message msg) {

            final String filmsHome = (String) msg.obj;
            final List<BaiSiBuDeQiJieBean> filmListHome =new ZiFuChuan_ToJson().getJavaBean(filmsHome);
            firstFragmentListView.addHeaderView(headView);
            firstFragmentListView.setAdapter(new Fragment_1_Listview_Adapter(getActivity(),filmListHome));
//            firstFragmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    System.out.println("===============你好，我是传值的========="+filmListHome.get(i).toString());
//                    Intent intent = new Intent(getActivity(), Pager_1_activity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("augrment",filmListHome.get(i));
//                    intent.putExtras(bundle);
//                    getActivity().startActivity(intent);
//                }
//            });
        }
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_fragment_1,null);
        headView = inflater.inflate(R.layout.my_fragment_1_listview_headview,null);
        firstFragmentListView = (ListView) view.findViewById(R.id.firstFragmentListView);
        firstFragmentVp = (ViewPager) headView.findViewById(R.id.firstFragmentViewpager);
        laynear = (LinearLayout) headView.findViewById(R.id.laynear);
        //给ListView设置适配器
            getFilms();

        return view;

    }


    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);



        initDate();
        intiPoint();
        //给ViewPager设置适配器
        firstFragmentVp.setAdapter(new MyFragment_1_Viewpager_Adapter(getActivity(),viewPagerList,handler));
        //实现左右无限轮播方法一
        //secondPage.setCurrentItem(10000*list.size());

        //实现左右无限轮播方法二
        firstFragmentVp.setCurrentItem(First_Num);
        firstFragmentVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动过程中不停的调用
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //选中某一页的监听
            public void onPageSelected(int position) {
                if(position == viewPagerList.size()-1){
                    //currentNum是要设置的索引位置
                    currentNum = First_Num;
                    //secondPage.setCurrentItem(currentNum,false);
                }else if(position == 0){
                    currentNum = viewPagerList.size()-2;
                    //secondPage.setCurrentItem(currentNum,false);
                }else{
                    currentNum = position;
                }
                //调用让小点变色的方法
                changeColorPoint();
            }
            //滑动状态改变时调用，
            public void onPageScrollStateChanged(int state) {
                //如果是静止状态，将当前页替换
                if(state == firstFragmentVp.SCROLL_STATE_IDLE){
                    //System.out.println("=====================我已经静下来了");
                    firstFragmentVp.setCurrentItem(currentNum,false);
                }

            }
        });
        //当监听完，屏幕滑动事件后，用Handler发送消息，延时让其自动轮播
        handler.sendEmptyMessageDelayed(AUTO_SCROLL,2000);

    }
    public void getFilms(){
        new Thread(){
            public void run() {
                Request_ZiFuChuan ziFuChuan = new Request_ZiFuChuan();
                String films = ziFuChuan.getZiFuChuan("https://route.showapi.com/255-1?page=&showapi_appid=26156&showapi_timestamp=20161025124816&title=&type=&showapi_sign=84445e2f7c1830435f3f320f472b0b94");
                Message msg = new Message();
                msg.obj = films;
                handler2.sendMessage(msg);
            }
        }.start();
    }
    //让小点变色
    public void changeColorPoint(){
        //设置小点的下标
        int pointIndex = currentNum-1;
        for(int i=0;i<laynear.getChildCount();i++){
            ImageView imgView = (ImageView) laynear.getChildAt(i);
            if(i==pointIndex){
                //如果小点和图片的下标对应上，就取出小点，并让其图片更换
                imgView.setImageResource(R.drawable.auang);
            }else{
                imgView.setImageResource(R.drawable.alan);
            }
        }
    }
    public void intiPoint(){
        for(int i=0;i<viewPagerList.size()-2;i++){
            //实例化ImageView，实例化小点
            ImageView imageView = new ImageView(getActivity());
            if(i==0){
                imageView.setImageResource(R.drawable.alan);
            }else {
                imageView.setImageResource(R.drawable.auang);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            params.setMargins(10,10,10,10);
            laynear.addView(imageView,params);
        }


    }
    public void initDate(){
        viewPagerList = new ArrayList<MyFragment_Item_Bean>();
        MyFragment_Item_Bean bean1 = new MyFragment_Item_Bean(R.drawable.viewpager_1,"花样 年华、电影梦");
        MyFragment_Item_Bean bean2 = new MyFragment_Item_Bean(R.drawable.viewpager_2,"国际影城，会员办理火热进行中");
        MyFragment_Item_Bean bean3 = new MyFragment_Item_Bean(R.drawable.viewpager_3,"电影节，欢迎你");
        MyFragment_Item_Bean bean4 = new MyFragment_Item_Bean(R.drawable.viewpager_4,"网络神偷、犯罪高手！");
        viewPagerList.add(bean4);
        viewPagerList.add(bean1);
        viewPagerList.add(bean2);
        viewPagerList.add(bean3);
        viewPagerList.add(bean4);
        viewPagerList.add(bean1);
    }

    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);

    }

}
