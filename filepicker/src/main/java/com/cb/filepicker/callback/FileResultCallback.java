package com.cb.filepicker.callback;

import java.util.List;

public interface FileResultCallback<T> {
    void onResultCallback(List<T> files);
  }