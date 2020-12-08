# channel 之间传递数据
> 两个通道中有一个是FileChannel，那你可以直接将数据从一个channel（译者注：channel中文常译作通道）传输到另外一个channel

## transferFrom() 方法
```java
public void write() throws IOException {
    final FileChannel sourceChannel = new RandomAccessFile(source, "rw").getChannel();
    final FileChannel sinkChannel = new RandomAccessFile(sink, "rw").getChannel();
    sinkChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
    sourceChannel.close();
    sinkChannel.close();
}
```

## transferTo() 方法
```java
public void write2() throws IOException {
    final FileChannel sourceChannel = new RandomAccessFile(source, "rw").getChannel();
    final FileChannel sinkChannel = new RandomAccessFile(sink, "rw").getChannel();
    sourceChannel.transferTo(0, sourceChannel.size(), sinkChannel);
    sourceChannel.close();
    sinkChannel.close();
}
```