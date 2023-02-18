import client.CourierApiClient;
import courier.CourierCreateJson;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Создание курьера")
public class CreateCourierTest {

    CourierCreateJson courierCreateJson;
    CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        courierApiClient = new CourierApiClient();
        courierCreateJson = GenerateData.generateAccount();
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверяет создание курьера по POST /api/v1/courier")
    public void whenPostCreateThenReturnSuccess() {
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .statusCode(201)
                .body("ok", notNullValue());
    }

    @Test
    @DisplayName("Двойное создание курьера")
    @Description("Проверяет что нельзя создать курьера дважды с одними данными")
    public void whenIdenticallyPostCreateThenReturnError() {
        //Отправили первый запрос на создание, он успешный
        courierApiClient.createCourier(courierCreateJson);
        //Отправили второй запрос на создание, он ошибочный
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Обязательные поля для создания")
    @Description("Проверяет,что для создания нужно передать обязательные поля Login и Password, без Firstname")
    public void whenPostCreateWithoutNameThenReturnValidBody() {
        courierCreateJson.setFirstName(null);
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .statusCode(201)
                .body("ok", notNullValue());
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа")
    @Description("Проверка что запрос на создание возвращает правильный статус код - 201")
    public void whenPostCreateThenReturnValidStatusCode() {
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .statusCode(201);
    }

    @Test
    @DisplayName("Запрос возвращает правильное тело")
    @Description("Проверка что запрос на создание возвращает правильное тело - ok: true")
    public void whenPostCreateThenReturnValidBody() {
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать login")
    @Description("Проверяет, что без передачи поля Login создания не происходит - 400")
    public void whenPostCreateWithoutLoginThenReturnError() {
        courierCreateJson.setLogin(null);
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Чтобы создать курьера, нужно передать password")
    @Description("Проверяет, что без передачи поля Password создания не происходит - 400")
    public void whenPostCreateWithoutPasswordThenReturnError() {
        courierCreateJson.setPassword(null);
        Response response = courierApiClient.createCourier(courierCreateJson);

        response.then()
                .assertThat()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        GenerateData.deleteAccount();
    }
}
