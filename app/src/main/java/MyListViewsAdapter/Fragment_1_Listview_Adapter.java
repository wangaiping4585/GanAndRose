package MyListViewsAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import film.ganandrose.Pager_1_activity;
import film.ganandrose.R;
import javabean.BaiSiBuDeQiJieBean;
import javabean.Films;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by p on 2016/11/2.
 */
public class Fragment_1_Listview_Adapter extends BaseAdapter {

    private  Context mContext;
    private  int[] list;
    public List<BaiSiBuDeQiJieBean> filmsList;
    ViewHolder viewHolder =null ;
    public Fragment_1_Listview_Adapter(Context context, List<BaiSiBuDeQiJieBean> filmsList){
        this.mContext = context;
        this.list = list;
        this.filmsList = filmsList;
    }
    public int getCount() {
        return filmsList.size();
    }
    public Object getItem(int i) {
        return filmsList.get(i);
    }
    public long getItemId(int i) {
        return i;
    }
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if(view==null){
             viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.fragment_1_listview_item,null);
            viewHolder.imgTouXiang = (ImageView) view.findViewById(R.id.rentou);
            viewHolder.txtXingMing = (TextView) view.findViewById(R.id.txtXingMing);
            viewHolder.txtShiJian = (TextView) view.findViewById(R.id.shijian);
            viewHolder.txtGeYan = (TextView) view.findViewById(R.id.geyan);
            viewHolder.imgZhuTuPian = (ImageView) view.findViewById(R.id.zhuTuPian);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.txtXingMing.setText(filmsList.get(i).getName());
        viewHolder.txtShiJian.setText(filmsList.get(i).getCreate_time());
        viewHolder.txtGeYan.setText(filmsList.get(i).getText());


        new Thread(){
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(filmsList.get(i).getProfile_image()).build();
                try {
                    Response response = client.newCall(request).execute();
                    InputStream inputStream = response.body().byteStream();
                    //方法一，使用字节数组，给Bipmap添加图片
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int len = -1;
                    while ((len=inputStream.read(bytes))!=-1){
                        bos.write(bytes,0,len);
                    }
                    byte[] result = bos.toByteArray();
                    inputStream.close();
                    bos.close();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(result,0,result.length);
                   Message msg = new Message();
                           msg.obj = bitmap;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("====================fdsafadsf===================="+currentThread().getName());


            }
        }.start();
        Thread thread2 = new Thread(){
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request2 = new Request.Builder().url(filmsList.get(i).getProfile_image()).build();
                try {
                    Response response = client.newCall(request2).execute();
                    InputStream inputStream = response.body().byteStream();
                    //方法一，使用字节数组，给Bipmap添加图片
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int len = -1;
                    while ((len=inputStream.read(bytes))!=-1){
                        bos.write(bytes,0,len);
                    }
                    byte[] result = bos.toByteArray();
                    inputStream.close();
                    bos.close();
                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(result,0,result.length);
                    Message msg = new Message();
                    msg.obj = bitmap2;
                    handler2.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread2.start();
        System.out.println("=============我是适配器1==="+filmsList.get(i).toString());
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.out.println("===============你好，我是传值的========="+filmsList.get(i).toString());
                Intent intent = new Intent(mContext, Pager_1_activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("films",filmsList.get(i));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        return view;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           Bitmap bitmap = (Bitmap) msg.obj;
            viewHolder.imgTouXiang.setImageBitmap(bitmap);
        }
    };
    Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bitmap bitmap1 = (Bitmap) msg.obj;
            viewHolder.imgZhuTuPian.setImageBitmap(bitmap1);
        }
    };
   final class ViewHolder{
       ImageView imgTouXiang;
       TextView txtXingMing;
       TextView txtShiJian;
       TextView txtGeYan;
       ImageView imgZhuTuPian;
    }
}
