package diplom.API.steps;

import diplom.API.client.UserClient;
import diplom.API.dto.UserCreateRequest;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserSteps {
    private final UserClient userClient;

    public UserSteps(UserClient userClient) {
        this.userClient = userClient;
    }

    @Step
    public ValidatableResponse create(String email, String name, String password) {
        UserCreateRequest requestBody = new UserCreateRequest();
        requestBody.setEmail(email);
        requestBody.setName(name);
        requestBody.setPassword(password);
        return  userClient.create(requestBody)
                .then();
    }

    @Step
    public ValidatableResponse delete(String accessToken) {
        return userClient.delete(accessToken)
                .then();
    }

    @Step
    public ValidatableResponse login(String email, String password) {
        UserCreateRequest requestBody = new UserCreateRequest();
        requestBody.setEmail(email);
        requestBody.setPassword(password);
        return userClient.login(requestBody)
                .then();
    }
}
