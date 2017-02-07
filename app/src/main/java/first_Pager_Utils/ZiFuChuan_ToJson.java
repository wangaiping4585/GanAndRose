package first_Pager_Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javabean.BaiSiBuDeQiJieBean;


/**
 * Created by p on 2016/11/10.
 */
public class ZiFuChuan_ToJson {

    BaiSiBuDeQiJieBean bbqjb ;
    List<BaiSiBuDeQiJieBean> beanList = new ArrayList<BaiSiBuDeQiJieBean>();

     public List<BaiSiBuDeQiJieBean> getJavaBean(String string){
         try {
             JSONObject jsonObject = new JSONObject(string);
             String bodyString = jsonObject.optString("showapi_res_body");
             JSONObject pageBeanJsonObject = new JSONObject(bodyString);
             String pageBeanString =  pageBeanJsonObject.optString("pagebean");
             JSONObject jsonListJsonObject = new JSONObject(pageBeanString);
             String jsonListString = jsonListJsonObject.optString("contentlist");
             JSONArray jsonArray = new JSONArray(jsonListString);
             for(int i = 0;i<jsonArray.length();i++){
                 bbqjb = new BaiSiBuDeQiJieBean();
                 JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                 bbqjb.setName(jsonObject1.optString("name"));
                 bbqjb.setProfile_image(jsonObject1.optString("profile_image"));
                 bbqjb.setCreate_time( jsonObject1.optString("create_time"));
                 bbqjb.setText( jsonObject1.optString("text"));
                 bbqjb.setWeixin_url(jsonObject1.optString("weixin_url"));
                 bbqjb.setVideo_uri(jsonObject1.optString("video_uri"));
                 bbqjb.setImage0(jsonObject1.optString("image3"));
                 beanList.add(bbqjb);
             }
         } catch (JSONException e) {
             e.printStackTrace();
         }
         return beanList;
     }
}
