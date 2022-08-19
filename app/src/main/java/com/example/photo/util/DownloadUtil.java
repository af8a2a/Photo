package com.example.photo.util;

import android.os.AsyncTask;

import androidx.loader.content.AsyncTaskLoader;
//没能力做下载进度条和查看了...
@Deprecated
public class DownloadUtil {
    public static final int SUCCESS=0;
    public static final int FAILED=1;
    public static final int PAUSED=2;
    private static final int CANCELED=3;
    private DownloadListener listener;
    private boolean isCanceled=false;
    private boolean isPaused=false;
    private int lastProgress;

}
