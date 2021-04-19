package com.binlee.thread.practice;

import com.binlee.util.FileUtil;
import com.binlee.util.Logger;
import com.binlee.util.NamedRunnable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/11/24
 */
public final class Downloads {

    private static final Logger sLogger = Logger.get(Downloads.class);
    private final String url;
    private final String file;
    private final int threads;
    private final AtomicInteger counter = new AtomicInteger(0);

    private Downloads(String url, String file) {
        this(url, file, 3);
    }

    private Downloads(String url, String file, int threads) {
        this.url = url;
        this.file = file;
        this.threads = threads;
    }

    public static void exec() {
        try {
            // String url = "https://github.com/agalwood/Motrix/releases/download/v1.5.15/Motrix_1.5.15_amd64.deb";
            String url = "http://dldir1.qq.com/qqfile/qq/QQ7.9/16621/QQ7.9.exe";
            String file = FileUtil.getRootDir() + "/build/" + FileUtil.guessName(url);
            new Downloads(url, file, 3).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        final int code = connection.getResponseCode();
        sLogger.i("start() response code: " + code);
        if (code == HttpURLConnection.HTTP_OK) {
            final int length = connection.getContentLength();
            sLogger.i("start() content length: " + length);
            if (length > 5 * 1024 * 1024) {
                // 获取文件长度
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                raf.setLength(length);
                raf.close();
                // 多线程下载
                int start, end, chunk = length / threads;
                for (int i = 0; i < threads; i++) {
                    start = i * chunk;
                    end = i == threads - 1 ? (length - 1) : ((i + 1) * chunk - 1);
                    sLogger.i("start() download chunk i: " + i + ", start: " + start + ", end: " + end);
                    new Thread(new Task(start, end, this, i)).start();
                }
            } else {
                download("DirectDownload", 0, length);
            }
        }
    }

    private void download(String tag, int start, int end) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
        sLogger.i("download() start to get response code...");
        final int code = connection.getResponseCode();
        sLogger.i("download() response code: " + code);
        if (code == HttpURLConnection.HTTP_PARTIAL) {
            RandomAccessFile raf = new RandomAccessFile(file, "rws");
            raf.seek(start);
            int read;
            final InputStream is = new BufferedInputStream(connection.getInputStream());
            byte[] buffer = new byte[1 << 20];
            while ((read = is.read(buffer)) != -1) {
                raf.write(buffer, 0, read);
            }
            // toString() + "下载完成"
            sLogger.i("download() " + tag + " completed");
            is.close();
            final int mark = counter.incrementAndGet();
            sLogger.i("download() completed threads: " + mark);
            raf.close();
            if (mark == threads) {
                // 所有的下载完成
                sLogger.i("download() finished");
            }
        }
    }

    private static class Task extends NamedRunnable {

        private final int start;
        private final int end;
        private final Downloads downloads;

        protected Task(int start, int end, Downloads downloads, int id) {
            super("Download", id);
            this.start = start;
            this.end = end;
            this.downloads = downloads;
        }

        @Override
        public void run() {
            sLogger.d("Task#run() " + this + ", start: " + start + ", end: " + end);
            try {
                downloads.download(toString(), start, end);
            } catch (IOException e) {
                sLogger.e("Task#run() error ", e);
            }
        }
    }

}
