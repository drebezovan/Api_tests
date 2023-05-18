package spec;

import dto.GetTariffsResponse;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class GetStatusOrderSpec {
    public static GetTariffsResponse getStatusOrder() {

        return given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .contentType(ContentType.JSON)
                .get("/loan-service/getStatusOrder")
                .then()
                .spec(BaseSpec.getResponseSpec(200))
                .extract().body().jsonPath().getObject(".", GetTariffsResponse.class);
    }
}
