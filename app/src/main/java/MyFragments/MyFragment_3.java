package MyFragments;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import film.ganandrose.SystemVideoPlayer;
import film.ganandrose.R;
import javabean.Location_Film_Bean;
import utils.Format_Times;

/**
 * Created by p on 2016/11/2.
 */
public class MyFragment_3 extends Fragment {

    public ListView localFilmList;
    public TextView txtNoFilm;
    public ProgressBar loaclProgressbar;
    public  Location_Film_Bean localFilm;
    public List<Location_Film_Bean> location_Film_List;
    public Format_Times formatHaoMiao;
    public LocalFilmListAdapter localFilmListAdapter;

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(location_Film_List!=null && location_Film_List.size()>0){
                //有数据  设置数据适配器，把TextView文本控件隐藏
                localFilmListAdapter = new LocalFilmListAdapter();
                localFilmList.setAdapter(localFilmListAdapter);
                txtNoFilm.setVisibility(View.GONE);

            }else {
                //没有数据
                txtNoFilm.setVisibility(View.VISIBLE);
            }
            //ProgressBar隐藏
            loaclProgressbar.setVisibility(View.GONE);
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment_3,null);
        localFilmList = (ListView) view.findViewById(R.id.localFilmList);
        txtNoFilm = (TextView) view.findViewById(R.id.txtNoFilm);
        loaclProgressbar = (ProgressBar) view.findViewById(R.id.loaclProgressbar);
        formatHaoMiao = new Format_Times();
        initDate();

        localFilmList.setOnItemClickListener(new MyItemOnClickListening());
        return view;
    }
    class MyItemOnClickListening implements AdapterView.OnItemClickListener{
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Location_Film_Bean film = location_Film_List.get(i);
            Toast.makeText(getActivity(),"点击我本地视频"+film.toString(),Toast.LENGTH_SHORT).show();
            //调用系统的播放器，隐身启动
//            Intent intent= new Intent();
//            intent.setDataAndType(Uri.parse(film.getFilmAddress()),"video/*");
//            getActivity().startActivity(intent);
            //调用自己写的播放器，显示启动
            Intent intent = new Intent(getActivity(), SystemVideoPlayer.class);
            intent.setDataAndType(Uri.parse(film.getFilmAddress()),"video/*");
            getActivity().startActivity(intent);
        }
    }

    //初始化数据
    private void initDate() {
        //调用获取数据的方法
        getDateFromLocal();

    }

    //从本地Sd卡读取数据视频，1.通过文件后缀名变了。2.通过内容提供者中的数据库获取
    //如果是Android-6.0-以上系统，需要添加动态获取读取内存卡的权限
    public void getDateFromLocal() {
        location_Film_List = new ArrayList<Location_Film_Bean>();
        //加载本地数据，是耗时间操作，在子线程中进行
        new Thread(){
            public void run() {
                ContentResolver contentResolver = getActivity().getContentResolver();//用上下文，获得内容解析者
                Uri url = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String [] filmInfo = {
                        MediaStore.Video.Media.DISPLAY_NAME,//视频文件在SD卡的名字
                        MediaStore.Video.Media.DURATION,//视频的总时间长
                        MediaStore.Video.Media.SIZE,//视频文件的大小
                        MediaStore.Video.Media.DATA,//视频在SD卡的决定路径地址
                        MediaStore.Video.Media.ARTIST//视频的作者
                };
                Cursor cursor = contentResolver.query(url, filmInfo, null, null, null);
                if(cursor != null){
                    while (cursor.moveToNext()){
                         localFilm = new Location_Film_Bean();
                        String filmName = cursor.getString(0);//视频的名字
                        localFilm.setFilmName(filmName);

                        long filmTime = cursor.getInt(1);//视频时长
                        localFilm.setFilmTime(filmTime);

                        long filmSize = cursor.getInt(2);//视频的大小
                        localFilm.setFilmSize(filmSize);

                        String filmAddress = cursor.getString(3);//视频的决定路径
                        localFilm.setFilmAddress(filmAddress);

                        String filmArtist = cursor.getString(4);//视频的作者
                        localFilm.setFilmArtist(filmArtist);
                        location_Film_List.add(localFilm);
                    }
                    cursor.close();
                }
                //发消息
                handler.sendEmptyMessage(0);
            }
        }.start();


    }

    //给listView设置适配器
    class LocalFilmListAdapter extends BaseAdapter{
        public int getCount() {
            return location_Film_List.size();
        }
        public Object getItem(int i) {
            return location_Film_List.get(i);
        }

        public long getItemId(int i) {
            return i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view ==null){
                holder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.my_fragment_3_listview_item,null);
                holder.film_Img = (ImageView) view.findViewById(R.id.filmImg);
                holder.film_Name = (TextView) view.findViewById(R.id.filmName);
                holder.film_Time = (TextView) view.findViewById(R.id.film_Time);
                holder.film_Size = (TextView) view.findViewById(R.id.film_Size);
                view.setTag(holder);

            }else{
                holder = (ViewHolder) view.getTag();
            }
            //给得到的控件赋值，列表中对应位置
            Location_Film_Bean film = location_Film_List.get(i);
            holder.film_Name.setText(film.getFilmName());
            holder.film_Time.setText(formatHaoMiao.changeHaoMiaoToString((int) film.getFilmTime()));
            holder.film_Size.setText(Formatter.formatFileSize(getActivity(),film.getFilmSize()));
            return view;
        }
         class ViewHolder{
             ImageView film_Img;
             TextView film_Name,film_Time,film_Size;
        }
    }

}
