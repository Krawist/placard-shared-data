plugins {
    id ("org.jetbrains.kotlin.jvm") version "1.9.21"
    id ("org.jetbrains.kotlin.plugin.allopen") version "1.9.21"
    id ("org.jetbrains.kotlin.plugin.jpa") version "1.9.21"
    id ("com.google.devtools.ksp") version "1.9.21-1.0.16"
    id ("com.github.johnrengelman.shadow") version "8.1.1"
    id ("io.micronaut.application") version "4.2.1"
    id ("io.micronaut.test-resources") version "4.2.1"
    id ("io.micronaut.aot") version "4.2.1"
}

version = "0.1"
group = "com.placard"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {

    //Data validation
    implementation("io.micronaut.validation:micronaut-validation")
    ksp("io.micronaut.validation:micronaut-validation-processor")

    //MongoDB
    ksp("io.micronaut.data:micronaut-data-document-processor")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    implementation("io.micronaut.serde:micronaut-serde-bson")
    runtimeOnly("org.mongodb:mongodb-driver-sync")

    // Kotlin Symbol Processing (KSP) Dependencies
    ksp("io.micronaut.data:micronaut-data-processor") // Micronaut Data Processor for Kotlin Symbol Processing
    ksp("io.micronaut:micronaut-http-validation") // Micronaut HTTP Validation processor for Kotlin Symbol Processing
    ksp("io.micronaut.serde:micronaut-serde-processor") // Micronaut Serde Processor for Kotlin Symbol Processing

    // Implementation Dependencies
    implementation("io.micronaut:micronaut-core")
    implementation("io.micronaut.data:micronaut-data-jpa")
    implementation("org.postgresql:postgresql")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa") // Micronaut Data Hibernate JPA for data access using Hibernate
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime") // Micronaut Kotlin runtime for supporting Kotlin features in Micronaut
    implementation("io.micronaut.serde:micronaut-serde-jackson") // Micronaut Serde Jackson for using Jackson as the serialization/deserialization provider
    implementation("io.micronaut.sql:micronaut-jdbc-hikari") // Micronaut JDBC Hikari for database access using HikariCP
    // https://mvnrepository.com/artifact/io.micronaut/micronaut-security-jwt
    implementation("io.micronaut:micronaut-security-jwt:2.0.0.M2")

    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}") // Kotlin reflection library
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}") // Kotlin standard library for JDK 8

    // Compile Only Dependency
    compileOnly("io.micronaut:micronaut-http-client") // Micronaut HTTP client for compile-time (only needed during compilation)

    // Runtime Only Dependencies
    runtimeOnly("ch.qos.logback:logback-classic") // Logback Classic for runtime logging
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin") // Jackson module for Kotlin at runtime
    runtimeOnly("org.postgresql:postgresql") // PostgreSQL driver for runtime database access
    runtimeOnly("org.yaml:snakeyaml") //YAML

    // Test Implementation Dependency
    testImplementation("io.micronaut:micronaut-http-client") // Micronaut HTTP client for testing purposes

}


application {
    mainClass.set("com.placard.SharedObjectsService")
}
java {
    sourceCompatibility = JavaVersion.toVersion("18")
}


graalvmNative.toolchainDetection = false
micronaut {
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("com.placard.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
    }
}



