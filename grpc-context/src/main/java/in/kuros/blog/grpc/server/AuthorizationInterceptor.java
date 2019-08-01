package in.kuros.blog.grpc.server;

import in.kuros.blog.grpc.server.users.UserService;
import in.kuros.blog.grpc.server.users.UserServiceImpl;
import in.kuros.blog.grpc.server.users.model.UserInfo;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.Metadata.Key;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

public class AuthorizationInterceptor implements ServerInterceptor {

    public static final Context.Key<Object> USER_DETAILS = Context.key("user_details");

    private UserService userService;

    public AuthorizationInterceptor() {
        this.userService = new UserServiceImpl();
    }

    public <ReqT, RespT> Listener<ReqT> interceptCall(final ServerCall<ReqT, RespT> serverCall, final Metadata metadata, final ServerCallHandler<ReqT, RespT> serverCallHandler) {

        final String auth_token = metadata.get(Key.of("auth_token", Metadata.ASCII_STRING_MARSHALLER));

        final UserInfo userInfo = userService.validate(auth_token);

        Context context = Context.current()
                .withValue(USER_DETAILS, userInfo);

        return Contexts.interceptCall(context, serverCall, metadata, serverCallHandler);

    }
}
