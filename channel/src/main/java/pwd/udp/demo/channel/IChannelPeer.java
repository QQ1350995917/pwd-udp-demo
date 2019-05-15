package pwd.udp.demo.channel;

import io.netty.channel.ChannelHandler;

/**
 * pwd.udp.data@parent
 *
 * TODO what you want to do?
 *
 * date 2019-05-11 17:47
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public interface IChannelPeer {

    void start();

    void send();

    void close();

    ChannelHandler getChannelHandler();

    String getLocalAddress();

    int getLocalPort();

    String getRemoteAddress();

    int getRemotePort();

    void getStatus();
}
