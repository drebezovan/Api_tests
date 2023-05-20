package spec;

import dto.DeleteOrderRequest;
import dto.ErrorDTO;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class DeleteOrderSpec {
    public static void deleteOrderSuccessful(long userId, String orderId) {
        DeleteOrderRequest body = new DeleteOrderRequest();
        body.setUserId(userId);
        body.setOrderId(orderId);
        given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .body(body)
                .contentType(ContentType.JSON)
                .delete("loan-service/deleteOrder")
                .then()
                .spec(BaseSpec.getResponseSpec(200));
    }
    public static ErrorDTO deleteNonexistentOrder(long userId, String orderId) {
        DeleteOrderRequest body = new DeleteOrderRequest();
        body.setUserId(userId);
        body.setOrderId(orderId);
        return given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .body(body)
                .contentType(ContentType.JSON)
                .delete("loan-service/deleteOrder")
                .then()
                .spec(BaseSpec.getResponseSpec(400))
                .extract().body().jsonPath().getObject("error", ErrorDTO.class);
    }
}
