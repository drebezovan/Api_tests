package spec;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class DeleteOrderSpec {
    public void deleteUserSuccess(String id) {
        given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .contentType(ContentType.JSON)
                .delete("/deleteOrder")
                .then()
                .spec(BaseSpec.getResponseSpec(204));
    }
}
