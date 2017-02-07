package MyFragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Second_Pager_Utils.ParseJsonToBean;
import Second_Pager_Utils.RequestJsonString;
import film.ganandrose.R;
import javabean.Films;
import recycler_adapter.Pager_Two_RecyclerView_Adapter;

/**
 * Created by p on 2016/11/2.
 */
public class MyFragmentTwo extends Fragment {
    public RecyclerView rcyclerView;
    List<Films> filmsList;

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            filmsList = (List<Films>) msg.obj;
            System.out.println("==========handler中第二页的每个电影对象内容是============"+filmsList.get(1).toString());
            final GridLayoutManager glm = new GridLayoutManager(getActivity(),2);
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    int type = rcyclerView.getAdapter().getItemViewType(position);
                    if(type == 1){
                        return glm.getSpanCount();//就是横跨两列
                    }else{
                        return 1;
                    }

                }
            });
            rcyclerView.setLayoutManager(glm);
            //rcyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            rcyclerView.setAdapter(new Pager_Two_RecyclerView_Adapter(getActivity(),filmsList));
        }
    };
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initDate();
        View view = inflater.inflate(R.layout.my_fragment_2,null);
        rcyclerView = (RecyclerView) view.findViewById(R.id.recyclView);
        return view;
    }
    private void initDate() {
        new Thread(){
            public void run() {
                String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
                String reponseString  = new RequestJsonString().getZiFuChuan(url);
                System.out.println("==========第二页的线程总字符串============"+reponseString);
                filmsList = new ParseJsonToBean().parseJson(reponseString);
                //System.out.println("==========第二页的每个电影对象内容是============"+filmsList.get(1).toString());
                Message msg = new Message();
                msg.obj = filmsList;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
