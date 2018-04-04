package co.example.um2.aigle.alo.Common.News;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.example.um2.aigle.alo.Common.News.Model.RSSObject;
import co.example.um2.aigle.alo.R;

/**
 * Created by L'Albatros on 3/11/2018.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedViewHoler> {

    private RSSObject rssObject;
    private Context mContext;
    private LayoutInflater layoutInflater;

    public FeedAdapter(RSSObject rssObject, Context mContext) {
        this.rssObject = rssObject;
        this.mContext = mContext;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public FeedViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.cel_news, parent, false);
        return new FeedViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(FeedViewHoler holder, int position) {
        holder.txtTitle.setText(rssObject.getItems().get(position).getTitle());
        holder.txtDate.setText(rssObject.getItems().get(position).getPubDate());
        holder.txtContent.setText(rssObject.getItems().get(position).getContent());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!isLongClick){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.getItems().get(position).getLink()));
                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(browserIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rssObject.items.size();
    }
}

class FeedViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

    public TextView txtTitle, txtDate, txtContent;
    private ItemClickListener itemClickListener;

    public FeedViewHoler(View itemView){
        super(itemView);

        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        txtDate = (TextView) itemView.findViewById(R.id.txtDate);
        txtContent = (TextView) itemView.findViewById(R.id.txtContent);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}
