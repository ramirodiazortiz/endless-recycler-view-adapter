/**
 * Created by Ramiro Diaz Ortiz on 8/30/16.
 */

package com.rdo.example;

import java.util.ArrayList;

public class MockServer {

    private int pageNumber = 0;
    private int pageSize = 0;
    private int pageCount = 0;

    public MockServer(int pageSize, int pageCount) {
        this.pageSize = pageSize;
        this.pageCount = pageCount;
    }

    public void getNextPage(final CallbackHandler callbackHandler) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                doFakeWork();
                callbackHandler.onNewPage(MockServer.this.createPage());
                MockServer.this.pageNumber++;
            }
        };
        new Thread(runnable).start();
    }

    public boolean lastPageReached() {
        return MockServer.this.pageNumber == this.pageCount;
    }

    private ArrayList<String> createPage() {
        ArrayList<String> page = new ArrayList<>(this.pageSize);
        for (int i = 0; i < this.pageSize; i++) {
            page.add("Item " + String.valueOf(i + this.pageNumber * this.pageSize));
        }
        return page;
    }

    // Simulating something timeconsuming
    private void doFakeWork() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}





