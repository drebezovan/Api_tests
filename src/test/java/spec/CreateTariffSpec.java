package spec;

import dto.NewTariffInputDTO;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class CreateTariffSpec {
    public static void createTariffSuccessful(String type, String interestRate) {
        NewTariffInputDTO body = new NewTariffInputDTO();
        body.setType(type);
        body.setInterestRate(interestRate);
        given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .body(body)
                .contentType(ContentType.JSON)
                .post("/loan-service-admin/addTariff")
                .then()
                .spec(BaseSpec.getResponseSpec(200));
    }

    public static void createTariffError(String type, String interestRate) {
        NewTariffInputDTO body = new NewTariffInputDTO();
        body.setType(type);
        body.setInterestRate(interestRate);
        given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .body(body)
                .contentType(ContentType.JSON)
                .post("/loan-service-admin/addTariff")
                .then()
                .spec(BaseSpec.getResponseSpec(400));
    }
}