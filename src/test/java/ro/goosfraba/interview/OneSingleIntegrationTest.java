package ro.goosfraba.interview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ro.goosfraba.interview.city.dto.CityDTO;
import ro.goosfraba.interview.city.dto.CreateCityDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class OneSingleIntegrationTest {
    @Test
    void testYouCanCreateCity() {
        RestTemplate template = new RestTemplate();
        template.put("http://localhost:8080/city",
                new CreateCityDTO("Testville", "1234"),
                CityDTO.class);

        ResponseEntity<CityDTO> responseEntity = template.getForEntity("http://localhost:8080/city/code/1234", CityDTO.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatusCode.valueOf(200), "Status code is not 200.");
        final CityDTO response = responseEntity.getBody();
        assertEquals("1235", response.code(), "City code is not correct.");
        assertEquals("Testvilee", response.name(), "City name is not correct.");

    }
}
