# Scatter & Gather
> scatter/gather用于描述从Channel（译者注：Channel在中文经常翻译为通道）中读取或者写入到Channel的操作

## scatter: 分散
- 将一个 channel 中的数据读取到多个 buffer 中

## gather：聚集
- 将多个 buffer 中的数据写入到一个 channel 中

## 应用场景
- scatter / gather经常用于需要将传输的数据分开处理的场合
- 例如：传输一个由消息头和消息体组成的消息，你可能会将消息体和消息头分散到不同的buffer中，这样你可以方便的处理消息头和消息体

## scatter 示例
```java
ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body   = ByteBuffer.allocate(1024);
// 读取时会先把 header 缓冲区填满然后才填充 body 缓冲区
// 使用于读取固定的数据
channel.read(new ByteBuffer[] {header, body});
```

## gather 示例
```java
ByteBuffer header = ByteBuffer.allocate(128);
ByteBuffer body   = ByteBuffer.allocate(1024);
// 只有 position 和 limit 之间的数据才会被写入
// 固定与非固定数据均适用
channel.write(new ByteBuffer[] {header, body});
```