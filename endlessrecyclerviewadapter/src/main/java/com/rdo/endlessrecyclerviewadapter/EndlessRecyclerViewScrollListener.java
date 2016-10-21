/**
 * Created by Ramiro Diaz Ortiz on 8/29/16.
 */

package com.rdo.endlessrecyclerviewadapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    // Use this property to trigger the refresh event before reaching the last element. By default, this value is 2
    // The event to get the next page will be triggered when the item at index "data.size() - preloadOffset" is completely visible
    private int preloadOffset = 2;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (this.endlessScrollEnabled() && allElementsAreVisibleOrLastElementReached(recyclerView) && dy >= 0) {
            this.onScrolledToBottom();
        }
    }

    private boolean allElementsAreVisibleOrLastElementReached(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int completelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        return itemCount - completelyVisibleItemPosition <= preloadOffset;
    }

    public int getPreloadOffset() {
        return preloadOffset;
    }

    public void setPreloadOffset(int preloadOffset) {
        this.preloadOffset = preloadOffset;
    }

    public abstract void onScrolledToBottom();
    public abstract boolean endlessScrollEnabled();
}
