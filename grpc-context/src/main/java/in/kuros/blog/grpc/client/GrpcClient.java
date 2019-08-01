package in.kuros.blog.grpc.client;

import in.kuros.blog.grpc.AddResponse;
import in.kuros.blog.grpc.CalculatorGrpc;
import in.kuros.blog.grpc.CalculatorGrpc.CalculatorBlockingStub;
import in.kuros.blog.grpc.OperandRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel adminChannel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .intercept(new AuthTokenProvideInterceptor("admin_token"))
                .build();

        ManagedChannel userChannel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .intercept(new AuthTokenProvideInterceptor("user_token"))
                .build();

        System.out.println("****** Admin ******");
        execute(adminChannel);

        System.out.println("****** User ******");
        execute(userChannel);

    }

    private static void execute(final ManagedChannel channel) {
        final CalculatorBlockingStub blockingStub = CalculatorGrpc.newBlockingStub(channel);

        final AddResponse blockResponse = blockingStub.add(OperandRequest.newBuilder().setX(10).setY(20).build());

        System.out.println("blocking call result: " + blockResponse.getResult());

        channel.shutdown();
    }
}
