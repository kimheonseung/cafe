import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

dependencies {
    /* Json Web Token */
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    /* Json Web Token signWith noClassDefFoundError: javax/xml/bind/DatatypeConverter Java 11 Issue */
    implementation("javax.xml.bind:jaxb-api:2.1")
}
