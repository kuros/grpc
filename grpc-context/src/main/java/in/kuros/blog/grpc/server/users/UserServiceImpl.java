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
            throw new StatusRuntimeException(Status.UNAUTHENTICATED);
        }

        return loadUserByAuthToken(authToken);
    }

    private UserInfo loadUserByAuthToken(final String authToken) {
        // Fetch from DB, here I am validating in line

        if (authToken.equals("admin_token")) {
            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            roles.add("USER");

            return new UserInfo("Rohit", roles);
        }

        List<String> roles = new ArrayList<>();
        roles.add("USER");

        return new UserInfo("John", roles);
    }
}
