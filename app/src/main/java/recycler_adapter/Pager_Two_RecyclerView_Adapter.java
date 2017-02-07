package recycler_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import film.ganandrose.R;
import javabean.Films;
import view_holder.Base_View_Holder;
import view_holder.First_View_holder;
import view_holder.Second_View_Holder;

/**
 * Created by p on 2016/11/13.
 */
public class Pager_Two_RecyclerView_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Films> filmList;
    private final Context mContext;
    public First_View_holder first_view_holder;
    public  Second_View_Holder secondViewHolder;

    public Pager_Two_RecyclerView_Adapter(Context context, List<Films> filmsList){
        this.mContext = context;
        this.filmList = filmsList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 1:
                secondViewHolder = new Second_View_Holder(LayoutInflater.from(mContext).inflate(R.layout.second_pager_item, null, false));
                return secondViewHolder;
            case 2:
                first_view_holder = new First_View_holder(LayoutInflater.from(mContext).inflate(R.layout.my_fragment_2_recycle_view_item, null, false));
                return first_view_holder;
        }
            return null;
    }
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Base_View_Holder)holder).setDate(filmList.get(position),mContext);
    }
    public int getItemCount() {
        return filmList.size();
    }
    public int getItemViewType(int position) {
        return filmList.get(position).getType();
    }
}
