package pwd.udp.demo.channel;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;

/**
 * pwd.udp.data@parent
 *
 * TODO what you want to do?
 *
 * date 2019-05-11 22:09
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public abstract class ChannelPeer implements IChannelPeer, Runnable {

    protected EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    protected Bootstrap bootstrap = new Bootstrap();
    protected Channel channel;

    public ChannelPeer() {
        bootstrap.group(eventLoopGroup)
            .channel(NioDatagramChannel.class)
            .option(ChannelOption.SO_BROADCAST, true)
            .handler(this.getChannelHandler());

    }

    @Override
    public void start() {
        this.run();
    }

    @Override
    public void send() {
        try {
            channel.writeAndFlush(new DatagramPacket(
                Unpooled.copiedBuffer(JSON.toJSONString(""), CharsetUtil.UTF_8),
                new InetSocketAddress(getRemoteAddress(), getRemotePort()))).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            channel = bootstrap.bind(this.getLocalPort()).sync().channel();
            channel.closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() {
        eventLoopGroup.shutdownGracefully();
    }


    @Override
    public void getStatus() {

    }
}
