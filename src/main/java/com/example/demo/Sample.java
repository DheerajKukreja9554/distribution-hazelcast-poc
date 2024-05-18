package com.example.demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sample {

    public static void main(String[] args) throws Exception {
        String dependenciesNexus = """
spring-boot-3.1.2.jar
spring-context-6.0.11.jar
spring-boot-autoconfigure-3.1.2.jar
log4j-to-slf4j-2.17.2.jar
log4j-api-2.17.2.jar
jul-to-slf4j-2.0.7.jar
jakarta.annotation-api-2.1.1.jar
snakeyaml-2.0.jar
jackson-datatype-jdk8-2.15.2.jar
jackson-module-parameter-names-2.15.2.jar
reactor-netty-http-1.1.9.jar
netty-codec-http-4.1.94.Final.jar
netty-common-4.1.94.Final.jar
netty-buffer-4.1.94.Final.jar
netty-transport-4.1.94.Final.jar
netty-codec-4.1.94.Final.jar
netty-handler-4.1.94.Final.jar
netty-codec-http2-4.1.94.Final.jar
netty-resolver-dns-4.1.94.Final.jar
netty-resolver-4.1.94.Final.jar
netty-codec-dns-4.1.94.Final.jar
netty-resolver-dns-native-macos-4.1.94.Final-osx-x86_64.jar
netty-resolver-dns-classes-macos-4.1.94.Final.jar
netty-transport-native-epoll-4.1.94.Final-linux-x86_64.jar
netty-transport-native-unix-common-4.1.94.Final.jar
netty-transport-classes-epoll-4.1.94.Final.jar
reactor-netty-core-1.1.9.jar
netty-handler-proxy-4.1.94.Final.jar
netty-codec-socks-4.1.94.Final.jar
spring-web-6.0.11.jar
spring-beans-6.0.11.jar
micrometer-observation-1.10.9.jar
micrometer-commons-1.10.9.jar
spring-webflux-6.0.11.jar
reactor-core-3.5.8.jar
reactive-streams-1.0.4.jar
dispatcher-common-4.0.3.jar
jackson-databind-2.16.0.jar
jackson-core-2.16.0.jar
jackson-datatype-jsr310-2.16.1.jar
spring-modulith-core-1.1.3.jar
spring-modulith-api-1.1.3.jar
archunit-1.1.0.jar
rhino-1.7.14.jar
commons-collections4-4.4.jar
commons-io-2.13.0.jar
bcprov-jdk18on-1.77.jar
rbac-commons-4.0.0.jar
slf4j-api-2.0.7.jar
jakarta.servlet-api-6.0.0.jar
commons-text-1.10.0.jar
loki-logback-appender-1.5.0.jar
logback-classic-1.3.14.jar
logback-core-1.3.14.jar
jsoup-1.16.1.jar
jakarta.xml.bind-api-4.0.0.jar
jakarta.activation-api-2.1.0.jar
jaxb-impl-4.0.3.jar
jaxb-core-4.0.3.jar
angus-activation-2.0.1.jar
commons-beanutils-1.9.4.jar
commons-logging-1.2.jar
commons-collections-3.2.2.jar
commons-lang3-3.13.0.jar
jackson-annotations-2.15.2.jar
commons-configuration2-2.9.0.jar
spring-core-6.0.11.jar
spring-jcl-6.0.11.jar
gson-2.10.1.jar
web-server-common-2.0-SNAPSHOT.jar
tomcat-embed-core-10.1.12.jar
tomcat-embed-el-10.1.12.jar
tomcat-embed-websocket-10.1.12.jar
spring-webmvc-6.0.11.jar
spring-aop-6.0.11.jar
spring-expression-6.0.11.jar
spring-boot-actuator-autoconfigure-3.1.3.jar
spring-boot-actuator-3.1.3.jar
micrometer-core-1.11.3.jar
HdrHistogram-2.1.12.jar
LatencyUtils-2.0.3.jar
jasypt-spring-boot-3.0.5.jar
jasypt-1.9.3.jar
jakarta.inject-api-2.0.1.MR.jar
httpclient5-5.2.1.jar
httpcore5-5.2.jar
httpcore5-h2-5.2.jar
spring-security-cas-6.1.3.jar
spring-security-core-6.1.3.jar
spring-security-crypto-6.1.3.jar
spring-security-web-6.1.3.jar
cas-client-core-4.0.2.jar
nimbus-jose-jwt-9.31.jar
jcip-annotations-1.0-1.jar
commons-codec-1.15.jar
spring-security-config-6.1.3.jar
javax.inject-1.jar
spring-boot-jarmode-layertools-2.7.13.jar
                                """;
                String dependenciesLocal = """
                        spring-boot-3.1.2.jar
                        spring-boot-autoconfigure-3.1.2.jar
                        log4j-to-slf4j-2.17.2.jar
                        log4j-api-2.17.2.jar
                        jul-to-slf4j-2.0.7.jar
                        jakarta.annotation-api-2.1.1.jar
                        snakeyaml-2.0.jar
                        jackson-datatype-jdk8-2.15.2.jar
                        jackson-module-parameter-names-2.15.2.jar
                        reactor-netty-http-1.1.9.jar
                        netty-codec-http-4.1.94.Final.jar
                        netty-common-4.1.94.Final.jar
                        netty-buffer-4.1.94.Final.jar
                        netty-transport-4.1.94.Final.jar
                        netty-codec-4.1.94.Final.jar
                        netty-codec-http2-4.1.94.Final.jar
                        netty-resolver-dns-4.1.94.Final.jar
                        netty-resolver-4.1.94.Final.jar
                        netty-codec-dns-4.1.94.Final.jar
                        netty-resolver-dns-native-macos-4.1.94.Final-osx-x86_64.jar
                        netty-resolver-dns-classes-macos-4.1.94.Final.jar
                        netty-transport-native-epoll-4.1.94.Final-linux-x86_64.jar
                        netty-transport-native-unix-common-4.1.94.Final.jar
                        netty-transport-classes-epoll-4.1.94.Final.jar
                        spring-web-6.0.11.jar
                        spring-beans-6.0.11.jar
                        micrometer-observation-1.10.9.jar
                        micrometer-commons-1.10.9.jar
                        spring-webflux-6.0.11.jar
                        reactor-core-3.5.8.jar
                        reactive-streams-1.0.4.jar
                        dispatcher-common-4.0.3.jar
                        jackson-databind-2.16.0.jar
                        jackson-core-2.16.0.jar
                        jackson-datatype-jsr310-2.16.1.jar
                        spring-modulith-core-1.1.3.jar
                        spring-modulith-api-1.1.3.jar
                        archunit-1.1.0.jar
                        rhino-1.7.14.jar
                        commons-collections4-4.4.jar
                        commons-io-2.13.0.jar
                        bcprov-jdk18on-1.77.jar
                        rbac-commons-4.0.0.jar
                        cas-client-core-4.0.3.jar
                        nimbus-jose-jwt-9.35.jar
                        jcip-annotations-1.0-1.jar
                        commons-codec-1.16.0.jar
                        bcpkix-jdk15on-1.70.jar
                        bcutil-jdk15on-1.70.jar
                        spring-security-core-6.1.5.jar
                        spring-security-crypto-6.1.5.jar
                        spring-aop-6.0.13.jar
                        spring-context-6.0.13.jar
                        spring-expression-6.0.13.jar
                        reactor-netty-1.1.12.jar
                        reactor-netty-core-1.1.12.jar
                        netty-handler-4.1.100.Final.jar
                        netty-handler-proxy-4.1.100.Final.jar
                        netty-codec-socks-4.1.100.Final.jar
                        reactor-netty-incubator-quic-0.1.12.jar
                        netty-incubator-codec-native-quic-0.0.51.Final-linux-x86_64.jar
                        netty-incubator-codec-classes-quic-0.0.51.Final.jar
                        spring-security-cas-6.1.5.jar
                        spring-security-web-6.1.5.jar
                        jakarta.ws.rs-api-3.1.0.jar
                        jakarta.validation-api-3.0.2.jar
                        bcprov-jdk15on-1.70.jar
                        simplecaptcha-1.2.1.jar
                        slf4j-api-2.0.7.jar
                        jakarta.servlet-api-6.0.0.jar
                        commons-text-1.10.0.jar
                        loki-logback-appender-1.5.0.jar
                        logback-classic-1.3.14.jar
                        logback-core-1.3.14.jar
                        jsoup-1.16.1.jar
                        jakarta.xml.bind-api-4.0.0.jar
                        jakarta.activation-api-2.1.0.jar
                        jaxb-impl-4.0.3.jar
                        jaxb-core-4.0.3.jar
                        angus-activation-2.0.1.jar
                        commons-beanutils-1.9.4.jar
                        commons-logging-1.2.jar
                        commons-collections-3.2.2.jar
                        commons-lang3-3.13.0.jar
                        jackson-annotations-2.15.2.jar
                        commons-configuration2-2.9.0.jar
                        spring-core-6.0.11.jar
                        spring-jcl-6.0.11.jar
                        gson-2.10.1.jar
                        web-server-common-2.0-SNAPSHOT.jar
                        tomcat-embed-core-10.1.12.jar
                        tomcat-embed-el-10.1.12.jar
                        tomcat-embed-websocket-10.1.12.jar
                        spring-webmvc-6.0.11.jar
                        spring-boot-actuator-autoconfigure-3.1.3.jar
                        spring-boot-actuator-3.1.3.jar
                        micrometer-core-1.11.3.jar
                        HdrHistogram-2.1.12.jar
                        LatencyUtils-2.0.3.jar
                        jasypt-spring-boot-3.0.5.jar
                        jasypt-1.9.3.jar
                        jakarta.inject-api-2.0.1.MR.jar
                        httpclient5-5.2.1.jar
                        httpcore5-5.2.jar
                        httpcore5-h2-5.2.jar
                        spring-security-config-6.1.3.jar
                        javax.inject-1.jar
                        spring-boot-jarmode-layertools-2.7.13.jar
                        """;

        List<String> local = Arrays.stream(dependenciesLocal.split("\n")).sorted(String.CASE_INSENSITIVE_ORDER).toList();
        List<String> nexus = Arrays.stream(dependenciesNexus.split("\n")).sorted(String.CASE_INSENSITIVE_ORDER).toList();
        Set<String> localSet = new HashSet<>(local);
        Set<String> nexusSet = new HashSet<>(nexus);

        Set<String> common = new HashSet<>(localSet);
        common.retainAll(nexusSet);

        nexusSet.removeIf(common::contains);
        localSet.removeIf(common::contains);
        System.out.println("NEXUS: ");
        nexusSet.stream().sorted(String.CASE_INSENSITIVE_ORDER).forEach(System.out::println);
        System.out.println("LOCAL: ");
        localSet.stream().sorted(String.CASE_INSENSITIVE_ORDER).forEach(System.out::println);
      
    }
}
