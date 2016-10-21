# endlessrecyclerviewadapter - endless recycler view adapter
Basic endless recycler view adapter implementation. Please feel free to contact me anytime you need: ramiro.do@gmail.com

![](https://github.com/ramirodo/endless-recycler-view-adapter/blob/master/preview.gif)

1.a Configure your gradle project file 
  ```groovy
  allprojects {
    repositories {
        jcenter()
        maven {
            url  "http://dl.bintray.com/ramiro/android"
        }
    }
  }
  ```
1.b Configure your gradle module file
  ```groovy
dependencies {
   ...
   compile 'com.rdo:endlessrecyclerviewadapter:1.0.1'
}
  ```
  
2. Extends EndlessRecyclerViewAdapter and implement onCreateDataViewHolder, onBindDataViewHolder, onCreateFooterViewHolder and onBindFooterViewHolder methods.
  ```java
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
  ```
3. Set EndlessRecyclerViewScrollListener to the recyclerView

  ```java
  
  this.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
            @Override
            public void onScrolledToBottom() {
                MainActivity.this.adapter.showLoadingIndicator();
                MainActivity.this.loadPage();
            }

            @Override
            public boolean endlessScrollEnabled() {
                return !MainActivity.this.adapter.isRefreshing() && !MainActivity.this.server.lastPageReached(); //Your flag used to check if there are more pages available
            }
  });
  ```
  
4. Do an async task and call addNewPage method
```java 
  private void loadPage() {
        server.getNextPage(new CallbackHandler() {
            @Override
            public void onNewPage(final List page) {
                MainActivity.this.adapter.addNewPage(page); //Call when the loading task finishes
            }
        });
  }
```

--------
# Sample
```java
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

```
