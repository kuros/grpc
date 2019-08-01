package in.kuros.blog.grpc.server.users;

import in.kuros.blog.grpc.server.users.model.UserInfo;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public UserInfo validate(final String authToken) {

        if (authToken == null) {
            throw new StatusRuntimeException(Status.FAILED_PRECONDITION);
        }

        return loadUserByAuthToken(authToken);
    }

    private UserInfo loadUserByAuthToken(final String authToken) {
        // Fetch from DB, here I am validating in line

        if (!authToken.equals("valid_token")) {
            throw new StatusRuntimeException(Status.FAILED_PRECONDITION);
        }

        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        roles.add("USER");

        return new UserInfo("Rohit", roles);
    }
}
