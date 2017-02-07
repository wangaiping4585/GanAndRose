package MyLayoutAnimation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by p on 2016/11/1.
 * 作用：给app主布局添加侧滑效果，滑动出现侧部---工具设置栏
 */
public class SlideLayoutAnim extends FrameLayout {

    public View contentMainView;
    public View rightView;
    public View leftView;
    public int contentMainWidth;//滑动布局的宽，主布局
    public int rightViewWidth;
    public int leftViewWidth;//滑动布局的宽，左布局
    public int viewHeight;//滑动布局的高，主布局和左布局的高相同
    public float startX;//X轴的开始位置
    public float startY;//Y轴的开始位置
    public Scroller scroll;//滚动器，用来滚动布局

    //带参构造方法
    public SlideLayoutAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
        //实例化滚动器
        scroll = new Scroller(context);
    }

    // 系统回调方法 ：在布局文件一加载好，就会被系统自动回调onFinishInflate方法，
    protected void onFinishInflate() {
        super.onFinishInflate();

        //得到主布局的View对象-mainView
        contentMainView = getChildAt(0);
        //得到右侧布局的View对象-LeftView
        rightView = getChildAt(1);
        //得到左侧布局的View对象-rightView
        leftView = getChildAt(2);
    }

    //回调方法 ：测量布局控件的宽、高的、等方法
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得主布局的宽度
        contentMainWidth = contentMainView.getMeasuredWidth();
        //获得左布局的宽度
        rightViewWidth = rightView.getMeasuredWidth();
        //获得左布局的宽度
        leftViewWidth = leftView.getMeasuredWidth();
        //System.out.println("======================左部件=="+leftViewWidth);
        //获得主控件和左控件的高度
        viewHeight = getMeasuredHeight();
    }

    //隐藏控件的方法 ：需要指定，要隐藏位置的，左上角和右下角的坐标（左上角，右下角）
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //指定右布局，要隐藏的位置,坐标（左上角，右下角）
        rightView.layout(contentMainWidth,0,contentMainWidth+leftViewWidth,viewHeight);
        //指定右布局，要隐藏的位置,坐标（左上角，右下角）
        leftView.layout(-leftViewWidth,0,0,viewHeight);
    }

    //重写父类的滑动触摸事件
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);自动生成时的样子，拆分后可做很多事情
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //记录按下开始时：X\Y轴的坐标
                startX = event.getX();
                startY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                //记录X/Y轴的移动坐标（结束坐标）
                float endX = event.getX();
                float endY = event.getY();
                //计算移动的距离（偏移量），getScrollY默认为0
                float distanceX = endX - startX;
                int toScrollX = (int) (getScrollX() - distanceX);
                if(toScrollX<-leftViewWidth){//如果移动 方向，小于X轴的左布局（X轴的负方向），就让位置等于最小值
                    toScrollX = -leftViewWidth;
                }else if(toScrollX>rightViewWidth){//如果移动方向，大于布局的长度，向右最（X轴的正方向），就让布局最大位置
                    toScrollX = leftViewWidth;
                }
                scrollTo(toScrollX,getScrollY());//设置两个空件一起移动scrollTo
                startX = event.getX();//重新赋值
                startY = event.getY();//重新赋值
                break;

            case MotionEvent.ACTION_UP:
                //让滑动结束后自动回位
                //当手指滑动结束时，获取当前的偏移量
                int totalScrollX = getScrollX();
                if(getScrollX()>8 || -getScrollX()>8){
                    //System.out.println("=======总共位移======="+totalScrollX);
                    if(totalScrollX>0 && totalScrollX<rightViewWidth/2){
                        //调用关闭方法，复位控件原来位置
                        closeMenu();
                    }else{
                        //调用打开方法，控件要滑动到的位置
                        openMenu();
                    }
                    if(totalScrollX<0 && -totalScrollX<leftViewWidth/2){
                        //关闭控件
                       // System.out.println("=====是左移动，并且小于布局一半======");
                        int distanceLeftX = 0-getScrollX();
                        //System.out.println("=====distanceLeft====="+distanceLeftX);
                        scroll.startScroll(getScrollX(),getScrollY(),distanceLeftX,getScrollY());
                        invalidate();//强制刷新,调用computeScroll方法

                    }
                    if(totalScrollX<0 && -totalScrollX>leftViewWidth/2){
                        //System.out.println("=====是左移动，并且  小于布局一半======");
                        //展开控件
                        int distanceLeftX =-leftViewWidth-getScrollX();
                        System.out.println("=====distanceLeft====="+distanceLeftX);
                        scroll.startScroll(getScrollX(),getScrollY(),distanceLeftX,getScrollY());
                        invalidate();//强制刷新,调用computeScroll方法
                    }
                }



                break;
        }
        return true;



    }

    private void closeMenu() {
            //目标--->到0，减掉-起始
        int distanceX = 0 - getScrollX();
        scroll.startScroll(getScrollX(),getScrollY(),distanceX,getScrollY());
        invalidate();//强制刷新,调用computeScroll方法

    }

    public void computeScroll() {
        super.computeScroll();
        if(scroll.computeScrollOffset()){
            scrollTo(scroll.getCurrX(),scroll.getCurrY());
            invalidate();
        }
    }

    private void openMenu() {
        //目标--->到0，减掉-起始
        int distanceX = rightViewWidth - getScrollX();
        scroll.startScroll(getScrollX(),getScrollY(),distanceX,getScrollY());
        invalidate();//强制刷新,调用computeScroll方法


    }




}
