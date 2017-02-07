package film.ganandrose;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okhttpserver.download.DownloadService;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.request.GetRequest;

import java.io.InputStream;

import Second_Pager_Utils.RequestInputStream;
import first_Pager_Utils.ParseImage;
import javabean.Films;
import utils.AppCacheUtils;

/**
 * Created by p on 2016/11/14.
 */
public class Pager_2_Item_Acticity extends AppCompatActivity {

    public  Films film;
    public ImageView playImg;
    public TextView txtDownLoad,txtPianMing,txtLiexing,txtMiaoSu;
    private com.lzy.okhttpserver.download.DownloadManager downloadManager;

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.obj;
            playImg.setImageBitmap(bitmap);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_2_item_activity);
        //得到传来的数据
        Intent intent = getIntent();
        film = (Films) intent.getSerializableExtra("film");
        System.out.println("======你好我是电影第二页===="+film.toString());
        //初始化控件
       downloadManager = DownloadService.getDownloadManager();
        initView();
        initData();
        //给播放图片设置点击事件
        playImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(Pager_2_Item_Acticity.this,"播发视频-图片",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Pager_2_Item_Acticity.this,SystemVideoPlayer.class);
                intent.setDataAndType(Uri.parse(film.getUrl()),"video/*");
                Pager_2_Item_Acticity.this.startActivity(intent);
            }
        });
        //给下载按钮添加点击事件
        txtDownLoad.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (downloadManager.getDownloadInfo(film.getUrl()) != null) {
                    Toast.makeText(Pager_2_Item_Acticity.this,"已添加到，下载列队",Toast.LENGTH_SHORT).show();
                } else {
                    GetRequest request = OkHttpUtils.get(film.getUrl())//
                            .headers("headerKey1", "headerValue1")//
                            .headers("headerKey2", "headerValue2")//
                            .params("paramKey1", "paramValue1")//
                            .params("paramKey2", "paramValue2");
                    downloadManager.addTask(film.getUrl(), request, null);
                    AppCacheUtils.getInstance(Pager_2_Item_Acticity.this).put(film.getUrl(), film);
                    txtDownLoad.setText("已在队列");
                    txtDownLoad.setEnabled(false);
                }

            }
        });
    }

    private void initView() {
        playImg = (ImageView) findViewById(R.id.playImg);
        txtDownLoad = (TextView) findViewById(R.id.txtXiaZai);
        txtPianMing = (TextView) findViewById(R.id.txtPianMing);
        txtLiexing = (TextView) findViewById(R.id.txtleixing);
        txtMiaoSu = (TextView) findViewById(R.id.txtMiaoshu);
        txtPianMing.setText(film.getMovieName());
        txtLiexing.setText(film.getStringBuffer().toString());
        txtMiaoSu.setText(film.getSummary());
    }
    public void initData(){
        new Thread(){
            public void run() {
                String imgUrl = film.getCoverImg();
                InputStream imgStream = new RequestInputStream().getImages(imgUrl);
                Bitmap imgBitmap = ParseImage.ParsesImages(imgStream);
                Message msg = new Message();
                msg.obj = imgBitmap;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
