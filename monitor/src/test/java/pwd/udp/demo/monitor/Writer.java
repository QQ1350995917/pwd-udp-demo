package pwd.udp.demo.monitor;

import java.io.File;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.StandardOpenOption;

/**
 * pwd.udp.demo.monitor@parent
 *
 * TODO what you want to do?
 *
 * date 2019-05-15 22:49
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public class Writer {

    private static String FILE_NAME = "/Users/pwd/workspace/dingpw/pwd-udp-demo/log/mapper";

    public static void main(String[] args) throws Throwable {
        File f = new File(FILE_NAME);
        FileChannel channel = FileChannel
            .open(f.toPath(), StandardOpenOption.READ, StandardOpenOption.WRITE,
                StandardOpenOption.CREATE);
        MappedByteBuffer b = channel.map(MapMode.READ_WRITE, 0, 64);
        CharBuffer charBuf = b.asCharBuffer();
        int index = 0;
        while (true) {
            charBuf.clear();
            String content = "Hello client " + index;
            char[] string = content.toCharArray();
            charBuf.put(string);
            index++;
            System.out.println(content);
            Thread.sleep(100);
        }
    }

}
