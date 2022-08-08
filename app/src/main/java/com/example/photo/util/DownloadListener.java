package com.example.photo.util;

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onCanceled();
    void onPaused();
}
