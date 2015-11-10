package ar.com.ramiro.endlessrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

public abstract class EndlessRecyclerViewAdapter<DVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_DATA = 0;
    private static final int TYPE_FOOTER = 1;
    private final LayoutInflater inflater;
    protected final ArrayList data;
    private LoadNextPageHandler nextPageHandler;

    protected EndlessRecyclerViewAdapter(LayoutInflater inflater, ArrayList data) {
        this.inflater = inflater;
        this.data = data;
    }

    public void setLoadNextPageHandler(LoadNextPageHandler handler) {
        this.nextPageHandler = handler;
    }

    private boolean hasFooter() {
        if (data != null && data.size() > 0) {
            return data.get(data.size() - 1) instanceof EndlessRecyclerViewAdapter.LoadingFooter;
        }
        return false;
    }

    public abstract DVH onCreateDataViewHolder(ViewGroup parent, int viewType);
    public abstract void onBindDataViewHolder(DVH holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.loading_footer_item, parent, false);
            return new FooterViewHolder(view);
        } else {
            return onCreateDataViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object object = data.get(position);
        if (!(object instanceof EndlessRecyclerViewAdapter.LoadingFooter)) {
            this.onBindDataViewHolder((DVH)holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
       if (data.get(position) instanceof EndlessRecyclerViewAdapter.LoadingFooter) {
            return TYPE_FOOTER;
        }
        return TYPE_DATA;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void prepareToLoadNextPage() {
        if (!hasFooter()) {
            data.add(new LoadingFooter());
            this.notifyItemInserted(data.size() - 1);
            nextPageHandler.readyToLoadNextPage();
        }
    }

    public void loadingNextPageFinished() {
        if (hasFooter()) {
            int lastIndex = data.size() - 1;
            data.remove(lastIndex);
            this.notifyItemRemoved(lastIndex);
        }
    }

    public static interface LoadNextPageHandler {
        void readyToLoadNextPage();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar getProgressBar() {
            return progressBar;
        }
        private ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.footer_progress_bar);
        }
    }

    public class LoadingFooter {}
}
