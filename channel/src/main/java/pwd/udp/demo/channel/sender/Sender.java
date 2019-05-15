package pwd.udp.demo.channel.sender;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import pwd.udp.demo.channel.ChannelPeer;

/**
 * cn.eversec.udp.data.sender@pwd-udp-reliable
 *
 * TODO what you want to do?
 *
 * date 2019-05-08 10:41
 *
 * @author DingPengwei[dingpengwei@eversec.cn]
 * @version 1.0.0
 * @since DistributionVersion
 */
public class Sender extends ChannelPeer {

    private static final String LOCAL_ADDRESS = "192.168.31.72";
    private static final String REMOTE_ADDRESS = "192.168.31.17";

    private static final int LOCAL_PORT = 6666;
    private static final int REMOTE_PORT = 6666;

    public Sender() {

    }

    @Override
    public void send() {
        super.send();
//        map.put(message.getHeader(), message);
//        sendCounter.incrementAndGet();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public ChannelHandler getChannelHandler() {
        return new SenderHandler();
    }

    @Override
    public String getLocalAddress() {
        return LOCAL_ADDRESS;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public String getRemoteAddress() {
        return REMOTE_ADDRESS;
    }

    @Override
    public int getRemotePort() {
        return REMOTE_PORT;
    }

    class SenderHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        @Override
        public void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String response = msg.content().toString(CharsetUtil.UTF_8);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            super.channelReadComplete(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
        }
    }
}
