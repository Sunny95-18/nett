package com.szlx.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Sunny
 * @version 1.0
 * @date 2021/5/13 14:47
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request request = (Request) msg;



        System.out.println("Client Data:" +request.toString());

        Response response = checkOption(request.getRequestType(),request.getData());
        System.out.println("handle Data:" +response.toString());
        // client接收到信息后主动关闭掉连接
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Response checkOption(int type,byte[] data) throws IOException {
        Response response=null;
        switch (type){
            case 0:{
                System.out.println("0");
                response=new Response();
                response.setRequestId("0");
                response.setStatus(0);
//                User user=null;
//                System.out.println("00");
//                if(data instanceof User){
//                    System.out.println("000");
//                      user=(User)data;
//                     user.setAge(27);
//                }
                User user=(User)ByteUtls.bytesToObject(data);
                user.setAge(10);
                System.out.println("user:"+user.toString());
                response.setData(ByteUtls.objectToBytes(user));
                break;
            }
            case 1:{System.out.println("1");
                response=new Response();
                response.setRequestId("1");
                response.setStatus(1);
                response.setData("1".getBytes());break;}
            case 2:{System.out.println("2");
                response=new Response();
                response.setRequestId("2");
                response.setStatus(2);
                response.setData("2".getBytes());break;}
            case 3:{System.out.println("3");
                response=new Response();
                response.setRequestId("3");
                response.setStatus(3);
                response.setData("3".getBytes());break;}
            default:System.out.println("不支持");
        }
        return response;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
