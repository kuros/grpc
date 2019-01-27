package in.kuros.blog.grpc;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;

import javax.net.ssl.SSLException;
import java.io.File;

public class GrpcNewClient {

    public static void main(String[] args) throws SSLException {

        final ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 8443)
                .sslContext(GrpcSslContexts
                        .forClient()
                        .trustManager(getFile("/my-public-key-cert.pem"))
                        .build())
                .build();

        final CalculatorGrpc.CalculatorBlockingStub blockingStub = CalculatorGrpc.newBlockingStub(channel);

        System.out.println("Making call");
        final AddResponse blockResponse = blockingStub.add(OperandRequest.newBuilder().setX(10).setY(20).build());
        System.out.println("call result: " + blockResponse.getResult());

        channel.shutdown();

    }

    private static File getFile(final String fileName) {
        return new File(GrpcNewClient
                .class
                .getResource(fileName)
                .getFile());
    }
}
