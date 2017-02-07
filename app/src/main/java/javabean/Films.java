package javabean;

import java.io.Serializable;

/**
 * Created by p on 2016/11/5.
 */
public class Films implements Serializable{
    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    public String id;
    public String movieName;
    public String coverImg;
    public String movieId;
    public String url;
    public String hightUrl;
    public String videoTitle;
    public String videoLength;
    public String rating;
    public String summary;
    public StringBuffer stringBuffer;
    public int type;

    public Films() {
    }

    public Films(String id, String movieName, String coverImg, String movieId, String url, String hightUrl, String videoTitle, String videoLength, String rating, String summary, StringBuffer stringBuffer, int type) {
        this.id = id;
        this.movieName = movieName;
        this.coverImg = coverImg;
        this.movieId = movieId;
        this.url = url;
        this.hightUrl = hightUrl;
        this.videoTitle = videoTitle;
        this.videoLength = videoLength;
        this.rating = rating;
        this.summary = summary;
        this.stringBuffer = stringBuffer;
        this.type = type;
    }

    public static int getTypeOne() {
        return TYPE_ONE;
    }

    public static int getTypeTwo() {
        return TYPE_TWO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHightUrl() {
        return hightUrl;
    }

    public void setHightUrl(String hightUrl) {
        this.hightUrl = hightUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(String videoLength) {
        this.videoLength = videoLength;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public StringBuffer getStringBuffer() {
        return stringBuffer;
    }

    public void setStringBuffer(StringBuffer stringBuffer) {
        this.stringBuffer = stringBuffer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Films{" +
                "id='" + id + '\'' +
                ", movieName='" + movieName + '\'' +
                ", coverImg='" + coverImg + '\'' +
                ", movieId='" + movieId + '\'' +
                ", url='" + url + '\'' +
                ", hightUrl='" + hightUrl + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", videoLength='" + videoLength + '\'' +
                ", rating='" + rating + '\'' +
                ", summary='" + summary + '\'' +
                ", stringBuffer=" + stringBuffer +
                ", type=" + type +
                '}';
    }
}
