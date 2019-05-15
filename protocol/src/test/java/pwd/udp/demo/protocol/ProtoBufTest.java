package pwd.udp.demo.protocol;

import com.google.protobuf.ByteString;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pwd.udp.demo.protocol.PacketProto.Header;
import pwd.udp.demo.protocol.PacketProto.Header.Builder;
import pwd.udp.demo.protocol.PacketProto.Packet;

/**
 * pwd.udp.demo.protocol@pwd-udp-demo
 *
 * TODO what you want to do?
 *
 * date 2019-05-15 15:09
 *
 * @author DingPengwei[dingpengwei@eversec.cn]
 * @version 1.0.0
 * @since DistributionVersion
 */
public class ProtoBufTest {

  @NoArgsConstructor
  @AllArgsConstructor
  @Setter
  @Getter
  static class Body {
    private long id;
    private String name;
    private int age;
    private char gender;
    private boolean ready;
    private Map<String,Object> info;
  }

  @Before
  public void init(){

  }

  @Test
  public void coder() throws Exception {
    Builder headBuilder = Header.newBuilder();
    headBuilder.setCode(1234);
    headBuilder.setIndex(5678);
    headBuilder.setSession(1234L);
    headBuilder.setType(ByteString.copyFromUtf8("1"));
    headBuilder.setLength(5678);
    Header header = headBuilder.build();
    Packet.Builder packetBuilder = Packet.newBuilder();
    packetBuilder.setHeader(header);
    packetBuilder.setBody(ByteString.copyFrom("hello".getBytes()));
    Packet packet = packetBuilder.build();
    byte[] packetBytes = packet.toByteArray();
    Packet packetParsed = Packet.parseFrom(packetBytes);

    Header headerParsed = packetParsed.getHeader();
    int code = headerParsed.getCode();
    int index = headerParsed.getIndex();
    long session = headerParsed.getSession();
    ByteString type = headerParsed.getType();
    int length = headerParsed.getLength();

    ByteString bodyParsed = packetParsed.getBody();
    byte[] bodyByteArray = bodyParsed.toByteArray();
    String body = new String(bodyByteArray);

    Assert.assertEquals(1234,code);
    Assert.assertEquals(5678,index);
    Assert.assertEquals(1234L,session);
    Assert.assertEquals(5678,length);
    Assert.assertEquals("hello",body);
  }

}
