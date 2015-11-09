package ar.com.ramiro.endlessrecyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (allElementsAreVisibleOrLastElementReached(recyclerView)) {
            this.onScrolledToBottom();
        }
    }

    private boolean allElementsAreVisibleOrLastElementReached(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int completelyVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        return itemCount - completelyVisibleItemPosition <= 2;
    }

    public abstract void onScrolledToBottom();
}
