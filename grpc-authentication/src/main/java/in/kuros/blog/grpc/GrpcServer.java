package in.kuros.blog.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.File;

public class GrpcServer {

    public static void main(String[] args) throws Exception {

        final Server server = ServerBuilder.forPort(8443)
                .addService(new CalculatorImpl())

                //enable TLS
                .useTransportSecurity(
                        getFile("/my-public-key-cert.pem"),
                        getFile("/my-private-key.pem"))
                .build();

        server.start();
        server.awaitTermination();
    }

    private static File getFile(final String fileName) {
        return new File(GrpcServer.class.getResource(fileName).getFile());
    }
}


