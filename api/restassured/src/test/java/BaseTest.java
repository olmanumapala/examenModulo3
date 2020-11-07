import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    public RequestSpecification request;

    @BeforeClass
    @Parameters("baseUrl")
    public void SetRestAssured(@Optional String baseUrl)
    {
        if(baseUrl == null){
            baseUrl = "https://api-coffee-testing.herokuapp.com";
        }
        RestAssured.baseURI = baseUrl;
        request = RestAssured.given();
        System.out.println(String.format("Running on %s environment", baseUrl));
    }

    @BeforeMethod
    public void Before()
    {
        request = RestAssured.given();
    }
}
