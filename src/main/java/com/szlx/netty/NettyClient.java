package com.szlx.netty;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;


/**
 * @author Sunny
 * @version 1.0
 * @date 2021/5/13 14:53
 */
public class NettyClient extends SimpleChannelInboundHandler<Response> {
    private final String ip;
    private final int port;
    private Response response;

    public NettyClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
        this.response = response;
    }

    public Response client(Request request) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建并初始化 Netty 客户端 Bootstrap 对象
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();

                    pipeline.addLast(new RpcDecoder(Response.class));
                    pipeline.addLast(new RpcEncoder(Request.class));
                    pipeline.addLast(NettyClient.this);
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);

//            String[] discover = new Discover().discover("/yanzhenyidai/com.yanzhenyidai.server").split(":");

            // 连接 RPC 服务器
            ChannelFuture future = bootstrap.connect(ip, port).sync();

            // 写入 RPC 请求数据并关闭连接
            Channel channel = future.channel();

            channel.writeAndFlush(request).sync();
            channel.closeFuture().sync();
            return response;
        } finally {
            //关闭连接
//            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        Request request = new Request();
        request.setRequestId(UUID.randomUUID().toString());
        request.setRequestType(0);
        User user=new User();
        user.setId(1);
        user.setName("sunny");
        request.setData(ByteUtls.objectToBytes(user));
//        System.out.println("client:"+JSON.toJSONString(new NettyClient("127.0.0.1", 20000).client(request)));
        NettyClient nettyClient=new NettyClient("127.0.0.1",20000);
        Response response=nettyClient.client(request);
        User newUser=(User) ByteUtls.bytesToObject(response.getData());
        System.out.println("response:"+newUser.toString());
    }
}
