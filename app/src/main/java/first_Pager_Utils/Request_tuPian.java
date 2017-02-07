package first_Pager_Utils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by p on 2016/11/10.
 */
public class Request_tuPian {

    OkHttpClient client = new OkHttpClient();
    Request request;
    Response response;
    InputStream inputStream;
    public InputStream getImages(String string){
         request = new Request.Builder().url(string).build();
        try {
            response = client.newCall(request).execute();
             inputStream = response.body().byteStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

}
