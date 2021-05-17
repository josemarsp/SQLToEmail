plugins {
    java
}

group = "br.com.josef"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    implementation ( "javax.mail:mail:1.5.0-b01")
    implementation("com.microsoft.sqlserver:mssql-jdbc:9.2.1.jre8")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}