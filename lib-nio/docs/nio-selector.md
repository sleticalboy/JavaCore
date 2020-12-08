# Selector
> Selector（选择器）是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。
> 这样一个单独的线程可以管理多个channel，从而管理多个网络连接。

## 为什么使用 Selector
- 仅用单个线程来处理多个Channels的好处是，只需要更少的线程来处理通道 事实上，可以只用一个线程处理所有的通道 
- 对于操作系统来说，线程之间上下文切换的开销很大，而且每个线程都要占用系统的一些资源（如内存）因此，使用的线程越少越好

## 如何创建 Selector
- `Selector s = Selector.open();`

## 向 Selector 注册 channel
 ```java
channel.configureBlocking(false);
SlectionKey key = channel.register(s, SelectionKey.OP_READ);
 ```
- 与Selector一起使用时，Channel必须处于非阻塞模式下。 
- 不能将FileChannel与Selector一起使用，因为FileChannel不能切换到非阻塞模式。而套接字通道都可以
- 有四种不同的事件可以监听
    - connect -> SelectionKey.OP_CONNECT
    - accept -> SelectionKey.OP_ACCEPT
    - read -> SelectionKey.OP_READ
    - write -> SelectionKey.OP_WRITE
- 通道触发了一个事件意思是该事件已经就绪。所以： 
    - 某个channel成功连接到另一个服务器称为“连接就绪”
    - 一个server socket channel准备好接收新进入的连接称为“接收就绪”
    - 一个有数据可读的通道可以说是“读就绪”。等待写数据的通道可以说是“写就绪”
- 注册多个事件：`channel.register(s, SelectionKey.OP_READ | SelectionKey.OP_WRITE)`

## SelectionKey
- interest set: 
    - 通过 & 操作可以确定该 set 中有哪些事件：`set & SelectionKey.OP_CONNECT == SelectionKey.OP_CONNECT`
- ready set:
    - 检测方式同 interest set
    - key.isAcceptable() or key.isReadable() or key.isWritable() or key.isConnectable()
- channel: `channel = key.channel()`
- selector: `selector = key.selector()`
- optional: 附加对象
```java
key.attach(obj);
Object obj = key.attachment();
```

## 使用 select() 选择 channel

### select()
    - select()：一直阻塞到至少有一个通道在你注册的事件上就绪了
    - select(long timeout)：阻塞 (timeout 毫秒) 到至少有一个通道在你注册的事件上就绪了
    - selectNow()：不会阻塞，不管什么通道就绪都立刻返回
    - select() 方法返回的int值表示有多少通道已经就绪

### selectedKeys()
- 一旦调用了select()方法，并且返回值表明有一个或更多个通道就绪了，然后可以通过调用selector的selectedKeys()方法 
- 访问“已选择键集（selected key set）”中的就绪通道 `Set keys = s.selectedKeys()`
```java
Set selectedKeys = selector.selectedKeys();
Iterator keyIterator = selectedKeys.iterator();
while(keyIterator.hasNext()) {
    SelectionKey key = keyIterator.next();
    if(key.isAcceptable()) {
        // a connection was accepted by a ServerSocketChannel.
    } else if (key.isConnectable()) {
        // a connection was established with a remote server.
    } else if (key.isReadable()) {
        // a channel is ready for reading
    } else if (key.isWritable()) {
        // a channel is ready for writing
    }
    keyIterator.remove();
}
```

## wakeUp()
- 某个线程调用select()方法后阻塞了，即使没有通道已经就绪，也有办法让其从select()方法返回
- 只要让其它线程在第一个线程调用select()方法的那个对象上调用Selector.wakeup()方法即可。阻塞在select()方法上的线程会立马返回 
- 如果有其它线程调用了wakeup()方法，但当前没有线程阻塞在select()方法上，下个调用select()方法的线程会立即“醒来（wake up）”

## close()
- 用完Selector后调用其close()方法会关闭该Selector，且使注册到该Selector上的所有SelectionKey实例无效。通道本身并不会关闭

## 示例
```java
Selector selector = Selector.open();
channel.configureBlocking(false);
SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
while(true) {
  int readyChannels = selector.select();
  if(readyChannels == 0) continue;
  Set selectedKeys = selector.selectedKeys();
  Iterator keyIterator = selectedKeys.iterator();
  while(keyIterator.hasNext()) {
    SelectionKey key = keyIterator.next();
    if(key.isAcceptable()) {
        // a connection was accepted by a ServerSocketChannel.
    } else if (key.isConnectable()) {
        // a connection was established with a remote server.
    } else if (key.isReadable()) {
        // a channel is ready for reading
    } else if (key.isWritable()) {
        // a channel is ready for writing
    }
    keyIterator.remove();
  }
}
```