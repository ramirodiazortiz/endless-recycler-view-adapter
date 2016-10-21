/**
 * Created by Ramiro Diaz Ortiz on 8/29/16.
 */

package com.rdo.example;

import java.util.List;

public interface CallbackHandler<T> {
    void onNewPage(List<T> page);
}
