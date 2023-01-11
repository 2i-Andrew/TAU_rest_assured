import com.tngtech.java.junit.dataprovider.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;
import static io.restassured.RestAssured.*;

@RunWith(DataProviderRunner.class)
public class Chapter1Test {

    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.zippopotam.us").
                build();
    }

    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createResponseSpecification() {

        responseSpec = new ResponseSpecBuilder().
            expectStatusCode(200).
            expectContentType(ContentType.JSON).
            build();
    }

    @Test
    public void requestUsZipCode9021_checkStatusCode_expectHttp200() {

        given().
            spec(requestSpec).
        when()
            .get("us/90210").
        then().
            spec(responseSpec).
        and().
            assertThat().
            statusCode(200);
    }

    @Test
    public void requestUsZipCode90210_checkContentType_expectApplicationJson() {
        given().
            spec(requestSpec).
        when().
            get("us/90210").
        then().
            spec(responseSpec).
        and().
            assertThat().
            contentType(ContentType.JSON);
//            contentType("application/json");
    }

    @Test
    public void requestUsZipCode90210_logRequestAndResponseDetails() {
        given().
            log().all().
        when().
            get("http://zippopotam.us/us/90210").
        then().
            log().body();
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {

        given().
        when().
            get("http://zippopotam.us/us/90210").
        then().
            assertThat().
            body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @DataProvider
    public static Object[][] zipCodesAndPlaces(){
        return new Object[][] {
                { "us", "90210", "Beverly Hills" },
                { "us", "12345", "Schenectady" },
                { "ca", "B2R", "Waverly" }
        };
    }

    @Test
    @UseDataProvider("zipCodesAndPlaces")
    public void requestZipCodesFromCollection_checkPlaceNamInResponseBody_expectSpecifiedPlaceName(String countryCode, String zipCode, String expectedPlaceName) {

        given().
            spec(requestSpec).
            pathParam("countryCode", countryCode).
            pathParam("zipCode", zipCode).
        when().
            get("{countryCode}/{zipCode)").
        then().
            assertThat().
            body("places[0].'place name'", equalTo(expectedPlaceName));
    }

    @Test
    public void requestUsZipCode90210_extractPlaceNameFromResponseBody_assertEqualToBeverlyHills() {

        String placeName =

        given().
            spec(requestSpec).
        when().
            get("us/90210").
        then().
            extract().
            path("places[0].'place_name'");

        Assert.assertEquals(placeName, "Beverly Hills");

    }





}
