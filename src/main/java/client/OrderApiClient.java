package client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import order.OrderCreateJson;

public class OrderApiClient extends RestAssuredClient {

    @Step("Отправка запроса на создание списка POST /api/v1/orders")
    public Response createOrder(OrderCreateJson jsonBody) {
        return reqSpec
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post("/api/v1/orders");
    }

    @Step("Отправка запроса на получение списка заказов GET /api/v1/orders")
    public Response getListOrders() {
        return reqSpec
                .get("/api/v1/orders");
    }
}
