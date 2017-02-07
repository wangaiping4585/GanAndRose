package view_holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import javabean.Films;

/**
 * Created by p on 2016/11/13.
 */
public abstract class Base_View_Holder extends RecyclerView.ViewHolder {

    public Base_View_Holder(View itemView) {
        super(itemView);

    }
    public void setDate(Films film, Context context){
    }
}
