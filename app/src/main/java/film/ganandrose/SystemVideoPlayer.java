package film.ganandrose;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

/**
 * Created by p on 2016/11/14.
 * 系统视频播放器
 */
public class SystemVideoPlayer extends Activity{

    public Uri uri;
    public VideoView videoPlayer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_activity_video_player);
        videoPlayer = (VideoView) findViewById(R.id.videoPlayer);
        //设置底层准备好播放的监听
        videoPlayer.setOnPreparedListener(new MyOnpreparedListener());
        //设置播放，出错的监听
        videoPlayer.setOnErrorListener(new MyOnErrorListener());
        //设置播放，完成的监听
        videoPlayer.setOnCompletionListener(new MyOnCompletionListener());
        //设置播放地址
        uri = getIntent().getData();
        if(uri != null){
            videoPlayer.setVideoURI(uri);
        }
        //MediaControl媒体控制器
        videoPlayer.setMediaController(new MediaController(SystemVideoPlayer.this));
    }
    //实现了，准备好的监听
    class MyOnpreparedListener implements MediaPlayer.OnPreparedListener{
        public void onPrepared(MediaPlayer mediaPlayer) {
            videoPlayer.start();//开始播放
        }
    }
    //实现了，出错的监听
    class MyOnErrorListener implements MediaPlayer.OnErrorListener{
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            Toast.makeText(SystemVideoPlayer.this,"播放视频出错了！",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //实现了，播发视频完成的监听
    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener{
        public void onCompletion(MediaPlayer mediaPlayer) {
            Toast.makeText(SystemVideoPlayer.this,"播放完成！"+uri,Toast.LENGTH_SHORT).show();
        }
    }
}
