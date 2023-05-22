package spec;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dto.GetTariffsResponse;
import dto.TariffDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
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

    public static Map<String, TariffDTO> getTariffsMap() {
        List<TariffDTO> tariffs = given()
                .spec(BaseSpec.getRequestSpec())
                .when()
                .contentType(ContentType.JSON)
                .get("/loan-service/getTariffs")
                .then()
                .spec(BaseSpec.getResponseSpec(200))
                .extract().body().jsonPath().getObject("data", GetTariffsResponse.class).getTariffs();
        Map<String, TariffDTO> hashMap = new HashMap<>();
        for (TariffDTO tariff : tariffs) {
            hashMap.put(tariff.getType(), tariff);
        }
        return hashMap;
    }

    public static Map<String, TariffDTO> getReferenceTariffsMap() throws IOException {
        List<TariffDTO> tariffs = mapper.readValue(file, GetTariffsResponse.class).getTariffs();
        Map<String, TariffDTO> hashMap = new HashMap<>();
        for (TariffDTO tariff : tariffs) {
            hashMap.put(tariff.getType(), tariff);
        }
        return hashMap;
    }

    public static void assertMapEquals(Map<String, TariffDTO> map1, Map<String, TariffDTO> map2) {
        List<Executable> assertsList = new ArrayList<>();
        assertsList.add(() -> assertEquals(map1.size(), map2.size()));
        for (String key : map1.keySet()) {
            assertsList.add(() -> assertTariffs(map1.get(key), map2.get(key)));
        }
        Assertions.assertAll(assertsList);
    }

    public static void assertTariffs(TariffDTO tariff, TariffDTO tariff1) {
        assertAll(
                () -> assertEquals(tariff.getType(), tariff1.getType()),
                () -> assertEquals(tariff.getInterestRate(), tariff1.getInterestRate())
        );
    }
}