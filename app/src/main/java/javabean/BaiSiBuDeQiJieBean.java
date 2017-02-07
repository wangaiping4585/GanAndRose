package javabean;

import java.io.Serializable;

/**
 * Created by p on 2016/11/10.
 */
public class BaiSiBuDeQiJieBean implements Serializable{

    public String name;
    public String profile_image;
    public String create_time;
    public String text;
    public String weixin_url;
    public String video_uri;
    public String image0;
    public BaiSiBuDeQiJieBean() {
    }

    public BaiSiBuDeQiJieBean(String name, String profile_image, String create_time, String text, String weixin_url, String video_uri, String image0) {
        this.name = name;
        this.profile_image = profile_image;
        this.create_time = create_time;
        this.text = text;
        this.weixin_url = weixin_url;
        this.video_uri = video_uri;
        this.image0 = image0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWeixin_url() {
        return weixin_url;
    }

    public void setWeixin_url(String weixin_url) {
        this.weixin_url = weixin_url;
    }

    public String getVideo_uri() {
        return video_uri;
    }

    public void setVideo_uri(String video_uri) {
        this.video_uri = video_uri;
    }

    public String getImage0() {
        return image0;
    }

    public void setImage0(String image0) {
        this.image0 = image0;
    }

    @Override
    public String toString() {
        return "BaiSiBuDeQiJieBean{" +
                "name='" + name + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", create_time='" + create_time + '\'' +
                ", text='" + text + '\'' +
                ", weixin_url='" + weixin_url + '\'' +
                ", video_uri='" + video_uri + '\'' +
                ", image0='" + image0 + '\'' +
                '}';
    }
}