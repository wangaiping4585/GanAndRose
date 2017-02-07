package MyViewPagersAdapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import film.ganandrose.R;
import javabean.MyFragment_Item_Bean;

public class MyFragment_1_Viewpager_Adapter extends PagerAdapter {

    private List<MyFragment_Item_Bean> list;
    private Handler handler;
    private Context mContext;

    public MyFragment_1_Viewpager_Adapter(Context context, List<MyFragment_Item_Bean> list , Handler handler){
        this.mContext=context;
        this.list = list;
        this.handler = handler;
    }
    public int getCount() {
        //实现左右无限轮播方法一
        // return Integer.MAX_VALUE;

        //实现左右无限轮播方法二
        return list.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_fragment_1_listview_headview_viewpager_item,null);
        ImageView imgItem1 = (ImageView) view.findViewById(R.id.imgitem1);
        //给imageview设置点击事件
        imgItem1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //System.out.println("==================我是图片，我被点击了！======================");
            }
        });
        //给ImageView设置触摸监听事件
        imgItem1.setOnTouchListener(new View.OnTouchListener() {
            public static final int AUTO_SCROLL = 0;

            public boolean onTouch(View view, MotionEvent motionEvent) {
                //true代表按到当前imageView上面是，消费触摸事件，不会返回给上一级。false代表不消费触摸事件，继续向上传递，执行父控件的触摸方法
                switch (motionEvent.getAction()){
                    //按下时,手指触摸到屏幕时
                    case MotionEvent.ACTION_DOWN:
                        //取消Handler自动轮播认为
                        handler.removeCallbacksAndMessages(null);
                        break;

                    //手指在屏幕上移动时
                    case MotionEvent.ACTION_MOVE:
                        break;

                    //手指离开屏幕时
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        //从新启动自动轮播任务
                        handler.sendEmptyMessageDelayed(AUTO_SCROLL,2000);
                        break;
                }
                return false;
            }
        });
        //ImageView imgItem2 = (ImageView) view.findViewById(R.id.imgitem2);
        TextView txtItem = (TextView) view.findViewById(R.id.textitem);
        imgItem1.setImageResource(list.get(position).getImg());
        txtItem.setText(list.get(position).getTitle());
        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
