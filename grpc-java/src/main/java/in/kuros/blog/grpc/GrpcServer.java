package in.kuros.blog.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

    public static void main(String[] args) throws Exception {
        final Server server = ServerBuilder.forPort(8080)
                .addService(new CalculatorImpl())
                .build();

        server.start();
        server.awaitTermination();
    }
}

