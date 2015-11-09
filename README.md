# endlessrecyclerview - endless recycler view
Basic endless recycler view implementation (preliminary version). Please feel free to contact me anytime you need: ramiro.do@gmail.com

1. Get the files
2. Extends EndlessRecyclerViewAdapter and implement onCreateDataViewHolder and onBindDataViewHolder methods
3. Add new EndlessRecyclerViewScrollListener to the recyclerView

  ```java
  recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
            @Override
            public void onScrolledToBottom() {
                if (!isLastPageReached()) { //Whatever flag used to get if new page is available
                    adapter.prepareToLoadNextPage();
                }
            }
        });
  ```
4. Set LoadNextPageHandler in your adapter
  
  ```java
adapter.setLoadNextPageHandler(new EndlessRecyclerViewAdapter.LoadNextPageHandler() {
            @Override
            public void readyToLoadNextPage() {
                getNextPage(); //Whatever you do to get a new page
            }
        });
```        
5. Do an async task and call 
```java 
adapter.loadingNextPageFinished(); 
```
when the task finishes

--------
# Sample
```java
public class SampleAdapter extends EndlessRecyclerViewAdapter {
    public SampleAdapter(ArrayList list, Context context) {
        super(LayoutInflater.from(context), list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        //To be implemented (add here the same as in onCreateViewHolder)
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        //To be implemented (add here the same as in onBindViewHolder)
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
```
```java
public class Fragment {
    RecyclerView recyclerView;
    private SampleAdapter adapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        .....
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener() {
            @Override
            public void onScrolledToBottom() {
                if (!isLastPageReached()) { //Whatever flag to get if new page is available
                    adapter.prepareToLoadNextPage();
                }
            }
        });
        getData();
        return view;
    }

    private void getData() {
        adapter = new CardBenefitAdapter(arrayList, this.getContext());
        adapter.setLoadNextPageHandler(new EndlessRecyclerViewAdapter.LoadNextPageHandler() {
            @Override
            public void readyToLoadNextPage() {
                getNextPage(); //Whatevet you do to get a new page
            }
        });
        recyclerView.setAdapter(adapter);
        ..Do an async task and call adapter.loadingNextPageFinished(); when the task finishes
}
```
