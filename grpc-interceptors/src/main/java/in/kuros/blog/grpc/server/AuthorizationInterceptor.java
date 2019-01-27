package in.kuros.blog.grpc.server;

import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class AuthorizationInterceptor implements ServerInterceptor {

    public <ReqT, RespT> Listener<ReqT> interceptCall(final ServerCall<ReqT, RespT> serverCall, final Metadata metadata, final ServerCallHandler<ReqT, RespT> serverCallHandler) {

        final String auth_token = metadata.get(Key.of("auth_token", Metadata.ASCII_STRING_MARSHALLER));

        if (auth_token == null || !auth_token.equals("valid_token")) {
            throw new StatusRuntimeException(Status.FAILED_PRECONDITION);
        }

        return serverCallHandler.startCall(serverCall, metadata);
    }
}
