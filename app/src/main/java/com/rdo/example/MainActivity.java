/**
 * Created by Ramiro Diaz Ortiz on 8/29/16.
 */

package com.rdo.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rdo.endlessrecyclerviewadapter.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private BasicAdapter adapter;
    private MockServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.server = new MockServer(5, 8); //5 = number of items per page, 8 = number of pages
        this.data = new ArrayList<>();
        this.adapter = new BasicAdapter(this.data, this);

        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
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
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(adapter);

        // Make your async task
        this.loadPage();
    }

    private void loadPage() {
        server.getNextPage(new CallbackHandler() {
            @Override
            public void onNewPage(final List page) {
                MainActivity.this.adapter.addNewPage(page); //Call when the loading task finishes
            }
        });
    }
}