import io.restassured.RestAssured;
import io.restassured.internal.http.AuthConfig;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class OpenshiftApiDemoTest {
    RequestSpecification requestSpecification;

    @BeforeTest
    public void login() {
        this.requestSpecification = RestAssured
                                        .given()
                                            .auth()
                                            .oauth2("sha256~SPIght9s7QQq7ojbz96h3SIw3Tmqpa32x_4M42ChOdc")
                                            .baseUri("https://api.sandbox.x8i5.p1.openshiftapps.com:6443/");
    }

    @Test
    public void openshiftApiDemoTest() {
        Assert.assertEquals(requestSpecification
                                            .when()
                                                .get("apis/user.openshift.io/v1/users/~")
                                                .statusCode()
                                                    , 200);
    }
}
