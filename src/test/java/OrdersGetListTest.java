import client.OrderApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

@Feature("Получение заказов")
public class OrdersGetListTest {

    OrderApiClient orderApiClient;

    @Before
    public void setUp() {
        orderApiClient = new OrderApiClient();
    }

    @Test
    @DisplayName("Возвращается список заказов")
    @Description("Проверяет что возвращается список заказов с полями")
    public void whenGetOrdersListThenReturnValid() {
        Response response = orderApiClient.getListOrders();

        response.then()
                .assertThat()
                .statusCode(200)
                .body("orders", not(emptyArray()))
                .body("pageInfo", notNullValue())
                .body("availableStations", not(emptyArray()));
    }
}
