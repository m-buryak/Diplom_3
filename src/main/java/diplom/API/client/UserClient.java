package diplom.API.client;

import diplom.API.dto.UserCreateRequest;
import io.restassured.response.Response;

public class UserClient extends RestClient{

    public Response create(UserCreateRequest userCreateRequest) {
        return getDefaultRequestSpecification()
                .body(userCreateRequest)
                .when()
                .post("auth/register");
    }

    public Response delete(String accessToken) {
        return getDefaultRequestSpecification()
                .header("Authorization", accessToken)
                .when()
                .delete("auth/user");
    }

    public Response login(UserCreateRequest userCreateRequest) {
        return getDefaultRequestSpecification()
                .body(userCreateRequest)
                .when()
                .post("auth/login");
    }
}

