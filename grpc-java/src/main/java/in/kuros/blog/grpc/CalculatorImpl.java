package in.kuros.blog.grpc;

import in.kuros.grpc.AddResponse;
import in.kuros.grpc.CalculatorGrpc.CalculatorImplBase;
import in.kuros.grpc.OperandRequest;
import io.grpc.stub.StreamObserver;

public class CalculatorImpl extends CalculatorImplBase {

    @Override
    public void add(final OperandRequest request, final StreamObserver<AddResponse> responseObserver) {
        final long sum = request.getX() + request.getY();

        final AddResponse addResponse = AddResponse
                .newBuilder()
                .setResult(sum)
                .build();

        try {
            System.out.println("Sleeping to 5 sec");
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        responseObserver.onNext(addResponse);
        responseObserver.onCompleted();
    }
}
