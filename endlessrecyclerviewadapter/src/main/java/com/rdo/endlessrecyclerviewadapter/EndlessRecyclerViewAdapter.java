/**
 * Created by Ramiro Diaz Ortiz on 8/29/16.
 */

package com.rdo.endlessrecyclerviewadapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public abstract class EndlessRecyclerViewAdapter<T, VHT extends RecyclerView.ViewHolder, FHT extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DATA = 0;
    private static final int TYPE_FOOTER = 1;
    protected final LayoutInflater inflater;
    protected final List<T> data;
    private final Handler mainHandler;
    private FHT footerViewHolder;

    private boolean footerVisible = false;

    protected EndlessRecyclerViewAdapter(List<T> data, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public abstract FHT onCreateFooterViewHolder(ViewGroup parent, FHT reusableFooterHolder);
    public abstract void onBindFooterViewHolder(FHT holder, int position);

    public abstract VHT onCreateDataViewHolder(ViewGroup parent, int viewType);
    public abstract void onBindDataViewHolder(VHT holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            this.footerViewHolder = onCreateFooterViewHolder(parent, this.footerViewHolder);
            return this.footerViewHolder;
        }
        return onCreateDataViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == this.data.size()) {
            this.onBindFooterViewHolder((FHT) holder, position);
        } else {
            this.onBindDataViewHolder((VHT) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + (this.footerVisible ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == this.data.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_DATA;
    }

    public void showLoadingIndicator() {
        this.footerVisible = true;
        this.mainHandler.post(new Runnable() {
            public void run() {
                EndlessRecyclerViewAdapter.this.notifyItemInserted(data.size());
            }
        });
    }

    public void removeLoadingIndicator() {
        this.footerVisible = false;
        this.mainHandler.post(new Runnable() {
            public void run() {
                EndlessRecyclerViewAdapter.this.notifyItemRemoved(data.size());
            }
        });
    }

    public boolean isRefreshing() {
        return footerVisible;
    }

    public void addNewPage(List<T> page) {
        this.footerVisible = false;
        this.data.addAll(page);
        this.mainHandler.post(new Runnable() {
            public void run() {
                EndlessRecyclerViewAdapter.this.notifyDataSetChanged();
            }
        });
    }

}