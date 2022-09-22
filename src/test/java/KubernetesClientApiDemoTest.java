import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.openshift.api.model.operator.v1.OpenShiftAPIServer;
import io.fabric8.openshift.api.model.operator.v1.OpenShiftAPIServerBuilder;
import io.fabric8.openshift.client.NamespacedOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import io.fabric8.openshift.client.osgi.ManagedOpenShiftClient;
import org.testng.annotations.Test;

public class KubernetesClientApiDemoTest {

    @Test
    public void k8sClientApiDemoTest() {
        //NamespacedOpenShiftClient namespacedOpenShiftClient = new ManagedOpenShiftClient().adapt(OpenShiftClient.class);

        Config config = new ConfigBuilder().withMasterUrl("https://api.sandbox.x8i5.p1.openshiftapps.com:6443/")
                .withOauthToken("sha256~Zdk-BXJrGqIeidBRlVSxSMZNwH1G3Dp9jFtQrmVZztE")
                .build();
        OpenShiftClient openShiftClient = new Ope.withConfig(config)
                .build().adapt(OpenShiftClient.class);


        openShiftClient.pods().inNamespace("lisssalavo-dev").list().getItems().stream().forEach(System.out::println);
    }
}
