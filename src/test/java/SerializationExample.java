import dataentities.Location;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class SerializationExample {

    @Test
    public void sendLvZipCode1050_checkStatusCode_expect200() {

        Location location = new Location();

        given().
            contentType(ContentType.JSON).
            body(location).
            log().body().
        when().
            post("http://").
        then().
            assertThat().
            statusCode(200);
    }


}
