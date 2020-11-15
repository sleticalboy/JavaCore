package com.binlee.design.builder;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/11/15
 */
public final class HttpUrl {

    private String schema;
    private String host;
    private int port;
    private String path;

    private HttpUrl(Builder builder) {
        this.schema = builder.schema;
        this.host = builder.host;
        this.port = builder.port;
        this.path = builder.path;
    }

    public String schema() {
        return schema;
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }

    public String path() {
        return path;
    }

    @Override
    public String toString() {
        return "HttpUrl: " + schema + "://" + host + ":" + port + path;
    }

    public static HttpUrl parse(String url) {
        return new Builder(url).build();
    }

    public static class Builder {

        private String schema;
        private String host;
        private int port;
        private String path;
        
        public Builder() {
        }

        public Builder(String url) {
        }

        public Builder schema(String schema) {
            this.schema = schema;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public HttpUrl build() {
            return new HttpUrl(this);
        }
    }
}
