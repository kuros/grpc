package in.kuros.blog.grpc.client;

import in.kuros.blog.grpc.AddResponse;
import in.kuros.blog.grpc.CalculatorGrpc;
import in.kuros.blog.grpc.CalculatorGrpc.CalculatorBlockingStub;
import in.kuros.blog.grpc.OperandRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

    public static void main(String[] args) {

        System.out.println("****** Admin Role ******");
        execute("admin_token");

        System.out.println("****** User Role ******");
        execute("user_token");

    }

    private static void execute(final String authToken) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .intercept(new AuthTokenProvideInterceptor(authToken))
                .build();

        final CalculatorBlockingStub blockingStub = CalculatorGrpc.newBlockingStub(channel);

        final AddResponse blockResponse = blockingStub.add(OperandRequest.newBuilder().setX(10).setY(20).build());

        System.out.println("blocking call result: " + blockResponse.getResult());

        channel.shutdown();
    }
}
