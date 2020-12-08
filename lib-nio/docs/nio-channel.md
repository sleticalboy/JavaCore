# Channel

## NIO 中的 channel 类似 stream，但又有些不同：
 1. 既可以从通道中读取数据，又可以写数据到通道。但流的读写通常是单向的
 2. 通道可以异步地读写
 3. 通道中的数据总是要先读到一个Buffer，或者总是要从一个Buffer中写入

## 实例
```java
public void run() throws IOException {
   final String path = System.getProperty("user.home")
            + "/code/github/java/JavaCore/lib-nio/src/main/resources/nio-data.txt";
   final RandomAccessFile raf = new RandomAccessFile(path, "rw");
   final FileChannel channel = raf.getChannel();
   // 分配缓冲区
   final ByteBuffer buffer = ByteBuffer.allocate(48);
   // 1. 不停地将数据读取到缓冲区中
   while (channel.read(buffer) != -1) {
      // 2. 反转缓冲区，开始读取数据
      buffer.flip();
      // 3. 缓冲区有数据，就不停地取出 buffer.get()
      while (buffer.hasRemaining()) {
         // 注意此处不能直接用 buffer.getChar()
         System.out.print(((char) buffer.get()));
      }
      // 4. 清空 buffer，再读
      buffer.clear();
   }
   raf.close();
}
```

## channel 中三个重要的属性

### capacity
- 缓冲区容量，一旦缓冲区满了，需要清除数据才能重新写入
### position
- 读模式
   - 从某个指定的位置读取，表示当前可读取数据的位置
   - 当缓冲区切换到写模式时，position 会被重置为 0
- 写模式
   - 表示当前可写入数据的位置，最大值为 capacity - 1
### limit
- 读模式
   - 表示能从缓冲区读取多少数据；当切换Buffer到读模式时，limit 会被设置成写模式下的 position 值
- 写模式
   - 表示能向缓冲区中写入多少数据，此时 limit = capacity
   
## channel 中的一些

### 读取
- 从 channel 读取数据到 buffer：`channel.read(buffer)`
- 从 buffer 中读取数据：`buffer.get()`
- 从 buffer 中读取数据到 channel： `channel.write(buffer)`

### 写入
- 向缓冲区写入数据`buffer.put("data")`

### flip()
- 将缓冲取从写模式切换到读模式

### rewind()
- 将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）

### clear() 与 compact()
- 一旦读完Buffer中的数据，需要让Buffer准备好再次被写入。可以通过clear()或compact()方法来完成
- clear:
   - position将被设回0，limit被设置成 capacity的值，即：换句话说，Buffer 被清空了 
   - Buffer中的数据并未清除，只是这些标记告诉我们可以从哪里开始往Buffer里写数据
- compact:
   - 将所有未读的数据拷贝到Buffer起始处，然后将position设到最后一个未读元素正后面
   - limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了，但是不会覆盖未读的数据
   
### mark() 与 reset()
- mark: 可以标记Buffer中的一个特定position
- reset: 恢复到这个 position