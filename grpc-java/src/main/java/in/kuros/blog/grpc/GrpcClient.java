package in.kuros.blog.grpc;

import in.kuros.grpc.AddResponse;
import in.kuros.grpc.CalculatorGrpc;
import in.kuros.grpc.OperandRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.TimeUnit;

public class GrpcClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        final CalculatorGrpc.CalculatorBlockingStub blockingStub = CalculatorGrpc.newBlockingStub(channel);

        System.out.println("Making blocking call");
        final AddResponse blockResponse = blockingStub.add(OperandRequest.newBuilder().setX(10).setY(20).build());
        System.out.println("blocking call result: " + blockResponse.getResult());


        final CalculatorGrpc.CalculatorStub asyncStub = CalculatorGrpc.newStub(channel);

        StreamObserver<AddResponse> streamObserver = new StreamObserver<AddResponse>() {
            public void onNext(final AddResponse addResponse) {
                System.out.println("async call result: " + addResponse.getResult());
            }

            public void onError(final Throwable throwable) {
                System.out.println(throwable);
            }

            public void onCompleted() {
                System.out.println("Async call stopped listening");
            }
        };


        System.out.println("Making blocking call");
        asyncStub.add(OperandRequest.newBuilder().setX(10).setY(20).build(), streamObserver);
        System.out.println("Async invoked" + blockResponse.getResult());

        channel.awaitTermination(10, TimeUnit.SECONDS);

    }
}
