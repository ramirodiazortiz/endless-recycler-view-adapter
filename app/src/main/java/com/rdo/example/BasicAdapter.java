/**
 * Created by Ramiro Diaz Ortiz on 8/29/16.
 */

package com.rdo.example;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdo.endlessrecyclerviewadapter.EndlessRecyclerViewAdapter;

import java.util.ArrayList;

public class BasicAdapter extends EndlessRecyclerViewAdapter<String, BasicAdapter.ViewHolder, BasicAdapter.FooterViewHolder> {

    protected BasicAdapter(ArrayList data, Context context) {
        super(data, context);
    }

    @Override
    public BasicAdapter.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View view = this.inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindDataViewHolder(ViewHolder holder, int position) {
        String item = this.data.get(position);
        holder.text.setText(item);
    }

    @Override
    public BasicAdapter.FooterViewHolder onCreateFooterViewHolder(ViewGroup parent, BasicAdapter.FooterViewHolder reusableFooterHolder) {
        if (reusableFooterHolder == null) {
            View view = inflater.inflate(R.layout.loading_footer_item, parent, false);
            return new FooterViewHolder(view);
        }
        return reusableFooterHolder;
    }

    @Override
    public void onBindFooterViewHolder(BasicAdapter.FooterViewHolder holder, int position) {
        holder.text.setText("Loading from item " + String.valueOf(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        private final TextView text;

        public FooterViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.footer_text);
        }
    }
}
