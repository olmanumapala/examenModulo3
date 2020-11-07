import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class RestAssuredTest extends BaseTest {

    private static final String RESOURCESTATUS = "/v1/examen/status";
    private static final String RESOURCEUPDATE = "/v1/examen/updateName";
    private static final String RESOURCERANDOM = "/v1/examen/randomName";
    private static final String RESOURCESAME = "/v1/examen/sameName";

    @Test
    public void Status_Test(){
        request
                .get(String.format("%s", RESOURCESTATUS))
                .then()
                .statusCode(200)
                .body ("status", equalTo("Listos para el examen"));
    }

    @Test
    public void PutName_Test()
    {
        request
                .put(String.format("%s", RESOURCEUPDATE))
                .then()
                .statusCode(406)
                .body ("message", equalTo("Name was not provided"));
    }

    @Test
    public void RandomeNameNonAuth_Test()
    {
        request
                .get(String.format("%s", RESOURCERANDOM))
                .then()
                .statusCode(401)
                .body ("message", equalTo("Please login first"));
    }

    @Test
    public void RandomeNameAuth_Test()
    {
        request
                .given()
                .auth()
                .basic ("testuser", "testpass")
                .get (String.format("%s", RESOURCERANDOM))
                .then()
                .statusCode(200)
                .body("name", is(not(equalTo(null))));
    }

    @Test
    public void PostSameName_Test(){

        JSONObject PostSameName = new JSONObject();
        PostSameName.put("name", "Edge");

        request
                .body(PostSameName.toJSONString())
                .post(String.format("%s", RESOURCESAME))
                .then()
                .statusCode(200)
                .body("name", equalTo("Edge"));
    }
}
