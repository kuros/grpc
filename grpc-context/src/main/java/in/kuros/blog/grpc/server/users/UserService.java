package in.kuros.blog.grpc.server.users;

import in.kuros.blog.grpc.server.users.model.UserInfo;

public interface UserService {

    UserInfo validate(String authToken);
}
