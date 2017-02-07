package Second_Pager_Utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by p on 2016/11/5.
 */
public class RequestJsonString {


    Response response;
    String ziFuChuan;
    public String getZiFuChuan(String pathUrl){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(pathUrl).build();
        try {
            response = client.newCall(request).execute();
            ziFuChuan =  response.body().string();
            System.out.println("===========请求的总字符串是：============="+ziFuChuan);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ziFuChuan;
    }
}
