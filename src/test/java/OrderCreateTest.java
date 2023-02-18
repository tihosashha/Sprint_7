import client.OrderApiClient;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.OrderCreateJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.Locale;

import static org.hamcrest.Matchers.notNullValue;

@Feature("Создание заказов")
@RunWith(Parameterized.class)
public class OrderCreateTest {

    private final List<String> color;
    OrderApiClient orderApiClient;
    OrderCreateJson orderCreateJson;

    public OrderCreateTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {List.of("GREY")},
                {List.of("GREY", "BLACK")},
                {null}
        };
    }

    @Before
    public void setUp() {
        orderApiClient = new OrderApiClient();
        Faker faker = new Faker(new Locale("ru"));
        orderCreateJson = new OrderCreateJson(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.address().fullAddress(),
                faker.number().randomDigitNotZero(),
                faker.phoneNumber().cellPhone(),
                faker.number().randomDigitNotZero(),
                faker.business().creditCardExpiry(),
                faker.elderScrolls().quote(),
                color
        );
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверяет что тело заказа содержит поле Track")
    public void whenPostOrderThenReturnTrack() {
        Response response = orderApiClient.createOrder(orderCreateJson);

        response.then()
                .assertThat()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
