package pwd.udp.demo.channel.receiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import java.util.concurrent.atomic.AtomicInteger;
import pwd.udp.demo.channel.ChannelPeer;

/**
 * cn.eversec.udp.data.receiver@pwd-udp-reliable
 *
 * TODO what you want to do?
 *
 * date 2019-05-08 10:41
 *
 * @author DingPengwei[dingpengwei@eversec.cn]
 * @version 1.0.0
 * @since DistributionVersion
 */
public class Receiver extends ChannelPeer {

    private static final String LOCAL_ADDRESS = "192.168.31.17";
    private static final String REMOTE_ADDRESS = "192.168.31.72";

    private static final int LOCAL_PORT = 6666;
    private static final int REMOTE_PORT = 6666;


    private static AtomicInteger counterOk = new AtomicInteger();
    private static AtomicInteger counterError = new AtomicInteger();
    private static AtomicInteger counterException = new AtomicInteger();

    public Receiver() {

    }

    @Override
    public ChannelHandler getChannelHandler() {
        return new ReceiverHandler();
    }

    @Override
    public String getLocalAddress() {
        return LOCAL_ADDRESS;
    }


    @Override
    public int getLocalPort() {
        return LOCAL_PORT;
    }

    @Override
    public String getRemoteAddress() {
        return REMOTE_ADDRESS;
    }

    @Override
    public int getRemotePort() {
        return REMOTE_PORT;
    }

    class ReceiverHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        @Override
        public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
            throws Exception {
            String contentString = packet.content().toString(CharsetUtil.UTF_8);
            try {
                Message message = JSON.parseObject(contentString, Message.class);
                String content = message.getContent();
                if (content.endsWith("finish")) {
                    Header header = message.getHeader();
                    ACK ack = new ACK(header);
                    DatagramPacket data = new DatagramPacket(
                        Unpooled.copiedBuffer(JSON.toJSONString(ack), CharsetUtil.UTF_8),
                        packet.sender());
                    ctx.writeAndFlush(data);
                    ReportProxy.recordDuration(message);
                } else if (content.endsWith("report")) {
                    ReportProxy.report();
                } else if (content.endsWith("reset")) {
                    ReportProxy.reset();
                }
            } catch (JSONException e) {
                ReportProxy.recordHalf(contentString);
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            counterException.incrementAndGet();
            cause.printStackTrace();
        }
    }
}
