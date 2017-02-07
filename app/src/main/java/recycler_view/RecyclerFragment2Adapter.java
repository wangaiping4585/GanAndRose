package recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import film.ganandrose.R;
import javabean.Films;

/**
 * Created by p on 2016/11/7.
 */
public class RecyclerFragment2Adapter extends RecyclerView.Adapter {
    public ViewHolder viewHolder;
    private  Context mContext;
    private  List<Films> filmList;

    public RecyclerFragment2Adapter(Context context, List<Films> filmsList){
        this.mContext = context;
        this.filmList = filmsList;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_fragment_2_recycle_view_item,parent,false);
        viewHolder = new ViewHolder(view);
        viewHolder.imgFilm = (ImageView) view.findViewById(R.id.RC_id);
        viewHolder.txtLeixing = (TextView) view.findViewById(R.id.RC_leixing_text);
        viewHolder.txtPianming = (TextView) view.findViewById(R.id.RC_pianming_text);

        return viewHolder;
    }
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    public int getItemCount() {
        return filmList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgFilm;
        TextView txtLeixing,txtPianming;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
