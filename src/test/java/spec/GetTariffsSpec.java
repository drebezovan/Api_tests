package spec;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mts.creditapp.entity.tableEntities.Tariff;
import dto.GetTariffsResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTariffsSpec {
    static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    static {
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
    }

    static File file = new File("src/test/java/testData/defaultTariffs.yml");

    public static Map<String, Tariff> getTariffsMap() {
        List<Tariff> tariffs = given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .contentType(ContentType.JSON)
                .get("/loan-service/getTariffs")
                .then()
                .spec(BaseSpec.getResponseSpec(200))
                .extract().body().jsonPath().getObject("data", GetTariffsResponse.class).getTariffs();
        Map<String, Tariff> hashMap = new HashMap<>();
        for (Tariff tariff : tariffs) {
            hashMap.put(tariff.getType(), tariff);
        }
        return hashMap;
    }

    public static Map<String, Tariff> getReferenceTariffsMap() throws IOException {
        List<Tariff> tariffs = mapper.readValue(file, GetTariffsResponse.class).getTariffs();
        Map<String, Tariff> hashMap = new HashMap<>();
        for (Tariff tariff : tariffs) {
            hashMap.put(tariff.getType(), tariff);
        }
        return hashMap;
    }

    public static void assertMapEquals(Map<String, Tariff> map1, Map<String, Tariff> map2) {
        List<Executable> assertsList = new ArrayList<>();
        assertsList.add(() -> assertEquals(map1.size(), map2.size()));
        for (String key : map1.keySet()) {
            assertsList.add(() -> assertTariffs(map1.get(key), map2.get(key)));
        }
        Assertions.assertAll(assertsList);
    }

    public static void assertTariffs(Tariff tariff, Tariff tariff1) {
        assertAll(
                () -> assertEquals(tariff.getId(), tariff1.getId()),
                () -> assertEquals(tariff.getType(), tariff1.getType()),
                () -> assertEquals(tariff.getInterestRate(), tariff1.getInterestRate())
        );
    }
}