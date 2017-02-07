package first_Pager_Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by p on 2016/11/10.
 */
public class ParseImage {

    public static Bitmap ParsesImages(InputStream inputStream){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        try {
            while ((len=inputStream.read(bytes))!=-1){
                bos.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] imgByte = bos.toByteArray();
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
        return imgBitmap;
    }
}
