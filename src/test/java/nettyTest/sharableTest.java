package nettyTest;

import netty.shareableCase.NettyClient;
import org.junit.Test;

public class sharableTest {
    @Test
    public void test1() throws Exception {
        new NettyClient().connect(8080, "127.0.0.1");
    }
}
