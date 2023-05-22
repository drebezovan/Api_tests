package spec;

import dto.GetStatusOrder;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class GetStatusOrderSpec {
    public static GetStatusOrder getStatusOrder(String orderId) {

        return given()
                .spec(BaseSpec.getRequestSpec())
                .queryParam("orderId", orderId)
                .when()
                .contentType(ContentType.JSON)
                .get("/loan-service/getStatusOrder")
                .then()
                .spec(BaseSpec.getResponseSpec(200))
                .extract().body().jsonPath().getObject("data", GetStatusOrder.class);
    }
}