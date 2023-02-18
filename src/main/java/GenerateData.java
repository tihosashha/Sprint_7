import client.CourierApiClient;
import com.github.javafaker.Faker;
import courier.CourierCreateJson;
import courier.CourierLoginJson;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class GenerateData {

    private static String login;
    private static String password;
    private static String firstName;

    @Step("Создание JSON Логин, Пароль, Имя")
    public static CourierCreateJson generateAccount() {
        createAccountData();
        return new CourierCreateJson(login, password, firstName);
    }

    @Step("Генерация логина и пароля")
    public static void createAccountData() {
        Faker faker = new Faker();
        login = faker.name().username();
        password = faker.artist().name() + faker.number().digits(5);
        firstName = faker.name().firstName();
    }

    //Удалить аккаунт, если он был создан
    @Step("Удаление аккаунта, если он был создан")
    public static void deleteAccount() {
        CourierApiClient courierApiClient = new CourierApiClient();
        CourierLoginJson courierLoginJson = new CourierLoginJson(login, password);
        Response responseLogin = courierApiClient.loginCourier(courierLoginJson);
        if (responseLogin.statusCode() == 200) {
            String id = responseLogin.then().extract().path("id").toString();
            courierApiClient.deleteCourier(id);
        } else {
            System.out.println("Аккаунт не был создан");
        }
    }
}
