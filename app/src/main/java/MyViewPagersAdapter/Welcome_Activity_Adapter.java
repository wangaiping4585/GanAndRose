package MyViewPagersAdapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 应用程序欢迎页，的ViewPager适配器
 */
public class Welcome_Activity_Adapter extends PagerAdapter {

    private final Context mContext;
    private final int[] imgList;

    public Welcome_Activity_Adapter(Context context, int[] imgList){
        this.mContext = context;
        this.imgList = imgList;
    }
    public int getCount() {
        return imgList.length;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imgList[position]);
        container.addView(imageView);
        return imageView;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
