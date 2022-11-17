import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.*;
import io.fabric8.openshift.api.model.DeploymentConfig;
import io.fabric8.openshift.api.model.operator.v1.OpenShiftAPIServer;
import io.fabric8.openshift.api.model.operator.v1.OpenShiftAPIServerBuilder;
import io.fabric8.openshift.client.NamespacedOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import io.fabric8.openshift.client.osgi.ManagedOpenShiftClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KubernetesClientApiDemoTest {

    @Test
    public void k8sClientApiDemoTest() {
        //NamespacedOpenShiftClient namespacedOpenShiftClient = new ManagedOpenShiftClient().adapt(OpenShiftClient.class);

        Config config = new ConfigBuilder().withMasterUrl("https://api.sandbox.x8i5.p1.openshiftapps.com:6443/")
                .withOauthToken("sha256~Zdk-BXJrGqIeidBRlVSxSMZNwH1G3Dp9jFtQrmVZztE")
                .build();
        OpenShiftClient openShiftClient = new KubernetesClientBuilder().withConfig(config)
                .build().adapt(OpenShiftClient.class);


        //получила все поды в рамках неймспейса (openshift проекта)
        Stream<Pod> pods = openShiftClient.pods().inNamespace("lisssalavo-dev").list().getItems().stream();

        //проверила, что все поды, в названии которых есть "спарк" поднялись
        //не совсем корректно смотреть по названию, так как спарк может быть в названии проекта
        //надо подумать, по каким параметрам забирать нужные поды
        pods.filter(x -> x.getFullResourceName().contains("spark"))
                .forEach(x -> Assert.assertTrue((x.getStatus().equals("Running"))));

        //получила все деплоймент конфиги в рамках неймспейса
        Stream<DeploymentConfig> deploymentConfigs = openShiftClient.deploymentConfigs()
                .inNamespace("lisssalavo-dev").list().getItems().stream();

        //выбрала нужные мне деплойментконфиги
        //опять же, надо подумать, по какому критерию правильнее отбирать деплойментконфиги
        Stream<ObjectMeta> sparkDeploymentConfigsMetaData =
                deploymentConfigs.filter(x -> x.getFullResourceName().contains("spark"))
                        .map(x -> x.getMetadata());

        //???? не могу понять, что конкретно мне нужно в метадате, предположу, что labels
        sparkDeploymentConfigsMetaData.forEach(x -> Assert.assertTrue(x.getLabels().containsKey("livenessProbe")));
    }
}
