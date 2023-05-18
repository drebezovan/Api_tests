package spec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mts.creditapp.entity.tableEntities.Tariff;
import dto.GetTariffsResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetTariffsSpec {
    static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    static {
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
    }

    static File file = new File("src/test/java/testData/defaultTariffs.yml");

    public static List<Tariff> getTariffs() throws JsonProcessingException {

        String output = given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .contentType(ContentType.JSON)
                .get("/loan-service/getTariffs")
                .then()
                .spec(BaseSpec.getResponseSpec(200))
                .extract().body().toString();
        JsonNode jsonDataNode = mapper.readTree(output);
        JsonNode tariffsNode = jsonDataNode.get("data");
        return mapper.readValue(tariffsNode.asText(), new TypeReference<>() {});
    }

    public static void getYaml() throws IOException {
        System.out.println(mapper.readValue(file, GetTariffsResponse.class).getTariffs());
    }

    public static void assertGetTariff(GetTariffsResponse response) throws IOException {
        Assertions.assertEquals(mapper.readValue(file, GetTariffsResponse.class).getTariffs(), response.getTariffs());
    }
}
