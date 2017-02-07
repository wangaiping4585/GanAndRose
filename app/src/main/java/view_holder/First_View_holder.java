package view_holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import Second_Pager_Utils.RequestInputStream;
import film.ganandrose.Pager_2_Item_Acticity;
import film.ganandrose.R;
import first_Pager_Utils.ParseImage;
import javabean.Films;

/**
 * Created by p on 2016/11/14.
 */
public class First_View_holder extends Base_View_Holder {

    public ImageView mainImg;
    public TextView filmType;
    public TextView filmName;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap imgBitmap = (Bitmap) msg.obj;
            mainImg.setImageBitmap(imgBitmap);

        }
    };

    public First_View_holder(View itemView) {
        super(itemView);
        mainImg = (ImageView) itemView.findViewById(R.id.RC_id);
        filmType= (TextView) itemView.findViewById(R.id.RC_leixing_text);
        filmName = (TextView) itemView.findViewById(R.id.RC_pianming_text);

    }

    public void setDate(final Films film, final Context context) {
        super.setDate(film,context);
        itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(context,"你好啊",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Pager_2_Item_Acticity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("film",film);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        filmType.setText(film.getStringBuffer().toString());
        filmName.setText(film.getMovieName());
        new Thread(){
            public void run() {
                String imageUrl = film.getCoverImg();
                InputStream imgInputStream = new RequestInputStream().getImages(imageUrl);
                Bitmap imgBitmap = ParseImage.ParsesImages(imgInputStream);
                Message msg = new Message();
                msg.obj = imgBitmap;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
