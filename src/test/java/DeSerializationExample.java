import dataentities.Location;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class DeSerializationExample {

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        Location location =

        given().
        when().
            get("http://api.zippopotam.us/us/90210").
        as(Location.class);

        Assert.assertEquals(
            "Beverly Hills", location.getPlaces().get(0).getPlaceName()
        );


    }
}
