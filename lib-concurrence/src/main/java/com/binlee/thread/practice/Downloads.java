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

    private final Logger logger;
    private final String url;
    private final String file;
    private final int threads;
    private final AtomicInteger counter = new AtomicInteger(0);
    private RandomAccessFile raf;

    private Downloads(String url, String file) {
        this(url, file, 3);
    }

    private Downloads(String url, String file, int threads) {
        this.url = url;
        this.file = file;
        this.threads = threads;
        this.logger = Logger.get(getClass());
    }

    public static void exec() {
        try {
            String url = "https://github.com/agalwood/Motrix/releases/download/v1.5.15/Motrix_1.5.15_amd64.deb";
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
        logger.i("start() response code: " + code);
        if (code == HttpURLConnection.HTTP_OK) {
            final int length = connection.getContentLength();
            logger.i("start() content length: " + length);
            if (length > 5 * 1024 * 1024) {
                // 多线程下载
                raf = new RandomAccessFile(file, "rw");
                raf.setLength(length);
                int start, end, chunk = length / threads;
                for (int i = 0; i < threads; i++) {
                    start = i * chunk;
                    end = i == threads - 1 ? (length - 1) : ((i + 1) * chunk);
                    logger.i("start() download chunk i: " + i + ", start: " + start + ", end: " + end);
                    new Thread(new Task(start, end, this, i)).start();
                }
            }
        }
    }

    private void download(String tag, int start, int end) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.addRequestProperty("Range", "byte=" + start + "-" + end);
        final int code = connection.getResponseCode();
        logger.i("download() response code: " + code);
        if (code == HttpURLConnection.HTTP_PARTIAL) {
            raf.seek(start);
            int read;
            final InputStream is = new BufferedInputStream(connection.getInputStream());
            byte[] buffer = new byte[1 << 20];
            while ((read = is.read(buffer)) != -1) {
                raf.write(buffer, 0, read);
            }
            // toString() + "下载完成"
            logger.i("download() " + tag + " completed");
            is.close();
            final int mark = counter.incrementAndGet();
            logger.i("download() completed threads: " + mark);
            if (mark == threads) {
                raf.close();
                // 所有的下载完成
                logger.i("download() finished");
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
            try {
                downloads.download(toString(), start, end);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
