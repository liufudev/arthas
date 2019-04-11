package com.taobao.arthas.core.server;

import java.lang.instrument.Instrumentation;
import java.nio.charset.Charset;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author liufu
 * @version 1.0.0
 * @since 1.0.0
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {
    private Instrumentation instrumentation;

    public FirstServerHandler(final Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    private static ByteBuf getByteBuf(final ChannelHandlerContext ctx) {
        final byte[] bytes = "你好，欢迎关注我的微信公众号，《闪电侠的博客》!".getBytes(Charset.forName("utf-8"));

        final ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) {
        final ByteBuf byteBuf = (ByteBuf) msg;
        String message = byteBuf.toString(Charset.forName("utf-8"));
        System.out.println(new Date() + ": 服务端读到数据 -> " + message);
        // 回复数据到客户端
        System.out.println(new Date() + ": 服务端写出数据");
        final ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }


    private void handle(String line) {
        // TermImpl term = new TermImpl();
        // Handler managerCompletionHandler = new CommandManagerCompletionHandler(commandManager);
        // RequestHandler handler = new RequestHandler(term, managerCompletionHandler);
    }
}
