package spec;

import com.mts.creditapp.entity.tableEntities.Tariff;
import constants.TariffType;
import dto.CreateOrderRequest;
import dto.CreateOrderResponse;
import dto.ErrorDTO;
import io.restassured.http.ContentType;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateOrderSpec {

    public static String createOrderSuccessful(TariffType tariffType, Map<String, Tariff> tariffMap, long userId) {
        CreateOrderRequest body = new CreateOrderRequest();
        body.setTariffId((int) tariffMap.get(tariffType.name()).getId());
        body.setUserId(userId);
        return given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .body(body)
                .contentType(ContentType.JSON)
                .post("/loan-service/order")
                .then()
                .spec(BaseSpec.getResponseSpec(200))
                .extract().body().jsonPath().getObject("data", CreateOrderResponse.class).getOrderId();
    }

    public static ErrorDTO createOrderFailure() {
        CreateOrderRequest body = new CreateOrderRequest();
        body.setTariffId(-1);
        body.setUserId(2L);
        return given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .body(body)
                .contentType(ContentType.JSON)
                .post("/loan-service/order")
                .then()
                .spec(BaseSpec.getResponseSpec(400))
                .extract().body().jsonPath().getObject("error", ErrorDTO.class);
    }

    public static ErrorDTO createOrderError(TariffType tariffType, Map<String, Tariff> tariffMap, long userId) {
        CreateOrderRequest body = new CreateOrderRequest();
        body.setTariffId((int) tariffMap.get(tariffType.name()).getId());
        body.setUserId(userId);
        return given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .body(body)
                .contentType(ContentType.JSON)
                .post("/loan-service/order")
                .then()
                .spec(BaseSpec.getResponseSpec(400))
                .extract().body().jsonPath().getObject("error", ErrorDTO.class);
    }
}