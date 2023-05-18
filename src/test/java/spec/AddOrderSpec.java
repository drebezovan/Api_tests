package spec;

import dto.GetTariffsResponse;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class AddOrderSpec {
    public static GetTariffsResponse addOrder() {

        return given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .body("hhh")
                .contentType(ContentType.JSON)
                .post("/loan-service/order")
                .then()
                .spec(BaseSpec.getResponseSpec(200))
                .extract().body().jsonPath().getObject(".", GetTariffsResponse.class);
    }
}
