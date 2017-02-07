package javabean;

/**
 * Created by p on 2016/10/29.
 */
public class MyFragment_Item_Bean {

    public int img;
    public String title;

    public MyFragment_Item_Bean(int img, String title) {
        this.img = img;
        this.title = title;
    }

    public MyFragment_Item_Bean() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
