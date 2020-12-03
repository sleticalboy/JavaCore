package com.binlee.util;

import java.io.*;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/12
 */
public final class FileUtil {

    private static final Logger LOGGER = Logger.get(FileUtil.class);

    private FileUtil() {
        //no instance
    }

    public static String getRootDir() throws IOException {
        // 默认情况下new File("")代表的目录为：System.getProperty("user.dir")
        return new File("").getCanonicalPath();
    }

    public static String getRootDir0() throws IOException {
        final InputStream is = Runtime.getRuntime().exec("pwd").getInputStream();
        int len;
        final byte[] buffer = new byte[1024];
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        is.close();
        baos.flush();
        baos.close();
        return baos.toString().trim();
    }

    public static String guessName(String url) {
        if (url == null || !url.contains("/")) {
            return null;
        }
        return url.substring(url.lastIndexOf('/') + 1);
    }

    public static void copy(String source, String destination) throws IOException {
        copy(new FileInputStream(source), new FileOutputStream(destination));
    }

    public static void copy(String source, File destination) throws IOException {
        copy(new FileInputStream(source), new FileOutputStream(destination));
    }

    public static void copy(File source, String destination) throws IOException {
        copy(new FileInputStream(source), new FileOutputStream(destination));
    }

    public static void copy(File source, File destination) throws IOException {
        copy(new FileInputStream(source), new FileOutputStream(destination));
    }

    public static void copy(InputStream source, String destination) throws IOException {
        copy(source, new FileOutputStream(destination));
    }

    public static void copy(InputStream source, File destination) throws IOException {
        if (destination.exists() && destination.delete()) {
            LOGGER.d("copy() delete " + destination);
        }
        copy(source, new FileOutputStream(destination));
    }

    public static void copy(InputStream source, OutputStream destination) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(source);
        BufferedOutputStream bos = new BufferedOutputStream(destination);
        byte[] buffer = new byte[8 * 1024];
        int len;
        while ((len = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
            bos.flush();
        }
        bos.close();
        bis.close();
    }

    public static File file(String file) throws IOException {
        return file(new File(file));
    }

    public static File file(File file) throws IOException {
        if (file == null) {
            throw new IOException("file is null.");
        }
        File parent = file.getParentFile();
        if (parent == null) {
            return file;
        }
        if (!parent.exists() && parent.mkdirs()) {
            LOGGER.d("create dirs: " + parent);
        }
        if (!file.exists() && file.createNewFile()) {
            LOGGER.d("create file: " + file);
        }
        return file;
    }

    public static String readAll(String file) throws IOException {
        return readAll(file(file));
    }

    private static String readAll(File file) throws IOException {
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line).append('\n');
        }
        reader.close();
        return buffer.toString();
    }

    public static void writeAll(byte[] data, String destination) throws IOException {
        copy(new ByteArrayInputStream(data), destination);
    }

    public static void writeAll(byte[] data, File destination) throws IOException {
        copy(new ByteArrayInputStream(data), destination);
    }

    public static void delete(String file, boolean recurse) {
        try {
            delete(file(file), recurse);
        } catch (IOException e) {
            LOGGER.e("delete() error", e);
        }
    }

    public static void delete(File file, boolean recurse) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                if (recurse) {
                    final File[] files = file.listFiles();
                    if (files != null && files.length != 0) {
                        for (File f : files) {
                            delete(f, true);
                        }
                    }
                }
            }
            if (file.delete()) {
                LOGGER.d("delete " + file);
            }
        }
    }
}
