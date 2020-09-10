package netty.sharableCase;

public class SharableCaseTest {
    public static void main(String[] args) throws Exception {
        new NettyClient().connect(8080, "127.0.0.1");
    }
}
