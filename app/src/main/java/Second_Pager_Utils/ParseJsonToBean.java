package Second_Pager_Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javabean.Films;

/**
 * Created by p on 2016/11/5.
 */
public class ParseJsonToBean {

    List<Films> filmsList;
    StringBuffer stringBuffer;

    public  List<Films> parseJson(String jsonString) {
        filmsList = new ArrayList<Films>();
        //的到asssets文件夹中的json文件流
        try {
            JSONObject jsonOb = new JSONObject(jsonString);
            JSONArray trailers = jsonOb.getJSONArray("trailers");
            System.out.println("=======================长度是=="+trailers.length());
            for(int i=0;i<trailers.length();i++){
                Films film = new Films();
                JSONObject jsonObject = trailers.getJSONObject(i);
                String id = jsonObject.optString("id");
                String movieName = jsonObject.optString("movieName");
                String coverImg = jsonObject.optString("coverImg");
                String movieId = jsonObject.optString("movieId");
                String url = jsonObject.optString("url");
                String hightUrl = jsonObject.optString("hightUrl");
                String videoTitle = jsonObject.optString("videoTitle");
                String videoLength = jsonObject.optString("videoLength");
                String rating = jsonObject.optString("rating");
                String summary = jsonObject.optString("summary");
                film.setId(id);
                film.setMovieName(movieName);
                film.setCoverImg(coverImg);
                film.setMovieId(movieId);
                film.setUrl(url);
                film.setHightUrl(hightUrl);
                film.setVideoTitle(videoTitle);
                film.setVideoLength(videoLength);
                film.setRating(rating);
                film.setSummary(summary);
                if(i == 0){
                    film.setType(1);
                }else {
                    film.setType(2);
                }
                JSONArray typeList = jsonObject.getJSONArray("type");
                System.out.println("中括号的长度类型是======================"+typeList.length());
                for(int j=0;j<typeList.length();j++){
                    stringBuffer = new StringBuffer();
                    stringBuffer.append(typeList);

                }
                film.setStringBuffer(stringBuffer);
                filmsList.add(film);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return filmsList;
    }
}
