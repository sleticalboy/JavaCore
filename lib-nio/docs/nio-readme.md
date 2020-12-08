# Java NIO 一览

## NIO 概述
### 核心组成
- Channels
    - FileChannel: 从文件中读写数据
    - DatagramChannel: 能通过UDP读写网络中的数据
    - SocketChannel: 能通过TCP读写网络中的数据
    - ServerSocketChannel: 可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel
- Buffers
    - ByteBuffer
    - CharBuffer
    - DoubleBuffer
    - FloatBuffer
    - IntBuffer
    - LongBuffer
    - ShortBuffer
    - MappedByteBuffer: 内存映射文件
- Selectors
    - 允许在单线程中处理多个 channel
    - 要使用 selector 需要先向其注册 channel，然后调用其 select() 方法
- 其他组件均是与这三个核心组件搭配使用，比如 `Pipe` 和 `FileLock`

### Channel 和 Buffer
> 基本上，所有的 IO 在NIO 中都从一个Channel 开始。Channel 有点象流。 数据可以从Channel读到Buffer中，也可以从Buffer 写到Channel中。

