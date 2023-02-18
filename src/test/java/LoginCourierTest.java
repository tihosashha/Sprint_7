import client.CourierApiClient;
import courier.CourierCreateJson;
import courier.CourierLoginJson;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Авторизация курьера")
public class LoginCourierTest {

    CourierCreateJson courierCreateJson;
    CourierLoginJson courierLoginJson;
    CourierApiClient courierApiClient;

    @Before
    public void setUp() {
        courierApiClient = new CourierApiClient();
        courierCreateJson = GenerateData.generateAccount();
        courierLoginJson = new CourierLoginJson(courierCreateJson.getLogin(), courierCreateJson.getPassword());
        courierApiClient.createCourier(courierCreateJson);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    @Description("Проверяет авторизацию курьера по POST /api/v1/courier/login")
    public void whenPostLoginThenReturnSuccess() {
        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Неверный логин")
    @Description("Проверка что при неправильном логине возвращается ошибка - 404")
    public void whenPostLoginWithIncorrectLoginThenReturnError() {
        courierLoginJson.setLogin(RandomStringUtils.random(15, true, true));

        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Неверный пароль")
    @Description("Проверка что при неправильном пароле возвращается ошибка - 404")
    public void whenPostLoginWithIncorrectPasswordThenReturnError() {
        courierLoginJson.setPassword(RandomStringUtils.random(15, true, true));

        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Без поля Login")
    @Description("Проверка что при отсутствии поля Login возвращается ошибка - 400")
    public void whenPostLoginWithoutLoginThenReturnError() {
        courierLoginJson.setLogin(null);

        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Без поля Пароль")
    @Description("Проверка что при отсутствии поля Password возвращается ошибка - 400")
    public void whenPostLoginWithoutPasswordThenReturnError() {
        courierLoginJson.setPassword("");

        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Запрос возвращает правильный код ответа")
    @Description("Проверка что запрос на авторизацию возвращает правильный статус код - 200")
    public void whenPostLoginThenReturnId() {
        Response response = courierApiClient.loginCourier(courierLoginJson);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @After
    public void tearDown() {
        GenerateData.deleteAccount();
    }
}
