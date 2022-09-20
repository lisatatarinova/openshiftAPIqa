import com.openshift.restclient.ClientBuilder;
import com.openshift.restclient.IClient;
import com.openshift.restclient.ResourceKind;
import com.openshift.restclient.model.IResource;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.basic;

public class OpenshiftApiDemoTest {

    @Test
    public void openshiftApiDemoTest() {
        IClient client = new ClientBuilder("https://console-openshift-console.apps.sandbox.x8i5.p1.openshiftapps.com/")
                .withUserName("lisssalavo@gmail.com")
                .withPassword("1111")
                .build();

        client.getAuthorizationContext().setToken("sha256~s-VJijV47sq9i98jV18bOrX1WKcySK6n-bA_kK4GfB4");

        IResource request = client.getResourceFactory().stub(ResourceKind.PROJECT_REQUEST, "lisssalavo-dev");
        Assert.assertEquals(request.getName(), "lisssalavo-dev");
    }
}