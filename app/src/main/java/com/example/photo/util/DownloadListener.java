package com.example.photo.util;
@Deprecated
public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onCanceled();
    void onPaused();
}
