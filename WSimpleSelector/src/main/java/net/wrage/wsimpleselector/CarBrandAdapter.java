package net.wrage.wsimpleselector;

/**
 * Created by Lenovo on 2017/12/7.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import me.yokeyword.indexablerv.IndexableAdapter;


/**
 * 车牌适配器
 * Created by xyl on 2017/8/2.
 */

public class CarBrandAdapter extends IndexableAdapter<CardBrandBean> {
    private LayoutInflater mInflater;
    Context context;
    public CarBrandAdapter(Context context) {

        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_brand, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_brand, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, CardBrandBean entity) {
        ContentVH vh = (ContentVH) holder;

        vh.tv.setText(entity==null?"":entity.getName());
        /*if(entity!=null) {
            Picasso.with(context)
                    .load(entity.getLogo())
                    .into(vh.iv);
        }*/

    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;


        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView iv;
        public ContentVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_name);
            iv = (ImageView) itemView.findViewById(R.id.iv_name);
        }
    }
}