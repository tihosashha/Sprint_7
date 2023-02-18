package client;

import courier.CourierCreateJson;
import courier.CourierLoginJson;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CourierApiClient extends RestAssuredClient {
    @Step("Отправка запроса на создание курьера POST /api/v1/courier | Логин = {jsonBody.login} Пароль = {jsonBody.password}")
    public Response createCourier(CourierCreateJson jsonBody) {
        return reqSpec
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("/api/v1/courier");
    }

    @Step("Отправка запроса на авторизацию курьера POST /api/v1/courier/login | Логин = {jsonBody.login} Пароль = {jsonBody.password}")
    public Response loginCourier(CourierLoginJson jsonBody) {
        return reqSpec
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("/api/v1/courier/login");
    }

    @Step("Отправка запроса на удаление курьера DELETE /api/v1/courier/{id}")
    public void deleteCourier(String id) {
        reqSpec.delete("/api/v1/courier/" + id);
    }
}
