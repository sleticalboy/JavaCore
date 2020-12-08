package com.binlee.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/12/3
 */
public final class Migrate {

    private static final Logger LOGGER = Logger.get(Migrate.class);
    private static final String ROOT = "/home/binli/code/sleticalboy.github.io";
    private static final String _POST = "/home/binli/code/sleticalboy.github.io/_posts/";
    private final Map<String, Posts> posts = new HashMap<>();

    public void exec(String[] args) {
        String[] articleDir = {"sql", "java", "linux", "git", "book", "android", "english-learning"};
        for (String art : articleDir) {
            parse(new File(ROOT + "/" + art));
        }
        doMigrate();
        // remove all old files
        // for (String key : posts.keySet()) {
        //     FileUtil.delete(key, false);
        // }
    }

    // public static void main(String[] args) {
    //     new Migrate().exec(args);
    // }

    private void doMigrate() {
        File _posts = new File(_POST);
        FileUtil.delete(_posts, true);
        _posts.mkdirs();
        // 读取原文件
        // 输入到新文件中
        for (String key : posts.keySet()) {
            final Posts post = posts.get(key);
            LOGGER.i("\nold: " + key + "\nnew: " + post.fileName());
            try {
                final File destination = new File(_POST, post.fileName());
                if (destination.exists() && destination.delete()) {
                    LOGGER.i("doMigrate() remove old file " + destination);
                }
                final String content = post.toString() + FileUtil.readAll(key);
                FileUtil.writeAll(content.getBytes(StandardCharsets.UTF_8), destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parse(File file) {
        if (file == null) {
            return;
        }
        if (file.isDirectory()) {
            final File[] files = file.listFiles();
            if (files != null && files.length != 0) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        parse(f);
                        continue;
                    }
                    parsePost(f);
                }
            }
        } else {
            parsePost(file);
        }
    }

    private void parsePost(File file) {
        final String full = file.getAbsolutePath();
        // System.out.println(full);
        if (!full.endsWith(".md")) {
            return;
        }
        final String path = full.replace(ROOT + "/", "");
        // android  /framework/components /at-service-lifecycle.md
        //    ^             ^                     ^
        //    |             |                     |
        // category        tags                 title
        String title, category, tags, name;
        final String[] pieces = path.split("/");
        if (pieces[0] == null || pieces[0].trim().length() == 0 && pieces.length > 1) {
            category = pieces[1];
        } else {
            category = pieces[0];
        }
        tags = path.substring(path.indexOf(category) + category.length(), path.lastIndexOf("/"))
                .replace('/', ' ').trim();
        if (tags.trim().length() == 0) {
            tags = category;
        }
        name = pieces[pieces.length - 1];
        title = name.substring(0, name.lastIndexOf('.'));

        if (name.toUpperCase().startsWith("README")) {
            String parent = file.getParent();
            parent = parent.substring(parent.lastIndexOf('/') + 1);
            title = parent + "-" + name.replace(".md", "");
        }
        // System.out.println("title: " + title + ", category: " + category + ", tags: " + tags + " -> " + name);
        final Posts post = new Posts.Builder()
                .title(title)
                .date(FORMAT.format(file.lastModified()))
                .category(category)
                .tags(tags)
                .build();
        // System.out.println(post.toString());
        posts.put(full, post);
    }

    private static class Posts {

        String layout = "post";
        String title;
        String author = "sleticalboy";
        String date;
        String category = "[article]";
        String tags = "[technology]";

        public Posts(Builder builder) {
            title = builder.title;
            if (builder.layout != null && builder.layout.trim().length() != 0) {
                layout = builder.layout;
            }
            if (builder.author != null && builder.author.trim().length() != 0) {
                author = builder.author;
            }
            if (builder.date != null && builder.date.trim().length() != 0) {
                date = builder.date;
            } else {
                date = defaultDate();
            }
            if (builder.category != null && builder.category.trim().length() != 0) {
                category = builder.category;
            }
            if (builder.tags != null && builder.tags.trim().length() != 0) {
                tags = builder.tags;
            }
        }

        public String layout() {
            return layout;
        }

        public String title() {
            return title;
        }

        public String author() {
            return author;
        }

        public String date() {
            return date;
        }

        public String getCategory() {
            return category;
        }

        public String tags() {
            return tags;
        }

        public String fileName() {
            return date.substring(0, date.indexOf(' ')) + "-" + title + ".md";
        }

        @Override
        public String toString() {
            return "---\n" +
                    "layout: " + layout + '\n' +
                    "title: " + title + '\n' +
                    "author: " + author + '\n' +
                    "date: " + date + '\n' +
                    "category: " + category + '\n' +
                    "tags: " + wrappedTags(tags) + '\n' +
                    "---\n\n";
        }

        private String wrappedTags(String tags) {
            if (tags == null || tags.trim().length() == 0) {
                return "[]";
            }
            if (tags.startsWith("[") && tags.endsWith("]")) {
                return tags;
            }
            final String[] pieces = tags.split(" ");
            if (pieces.length == 1) {
                return "[" + tags + "]";
            }
            final StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < pieces.length; i++) {
                if (i == 0) {
                    buffer.append('[').append(pieces[i]).append(", ");
                } else if (i == pieces.length - 1) {
                    buffer.append(pieces[i]).append(']');
                } else {
                    buffer.append(pieces[i]).append(", ");
                }
            }
            return buffer.toString();
        }

        public static class Builder {
            String layout;
            String title;
            String author;
            String date;
            String category;
            String tags;

            public Builder() {
            }

            public Builder tags(String tags) {
                this.tags = tags;
                return this;
            }

            public Builder category(String category) {
                this.category = category;
                return this;
            }

            public Builder date(String date) {
                this.date = date;
                return this;
            }

            public Builder author(String author) {
                this.author = author;
                return this;
            }

            public Builder title(String title) {
                if (title == null || title.trim().length() == 0) {
                    throw new IllegalArgumentException("title = " + title);
                }
                this.title = title;
                return this;
            }

            public Builder layout(String layout) {
                this.layout = layout;
                return this;
            }

            public Posts build() {
                return new Posts(this);
            }
        }
    }

    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private static String defaultDate() {
        return FORMAT.format(System.currentTimeMillis());
    }
}
