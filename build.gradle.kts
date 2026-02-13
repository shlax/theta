plugins {
    scala
    java
}

repositories {
    mavenCentral()
}

java{
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.withType<ScalaCompile>().configureEach {
    scalaCompileOptions.additionalParameters.add("-Xunchecked-java-output-version:25")
}

dependencies {

    implementation("org.scala-lang:scala3-library_3:3.8.1")
    implementation("org.antlr:antlr4-runtime:4.13.2")

    implementation("org.l33tlabs.twl:pngdecoder:1.0")

    implementation("org.lwjgl:lwjgl-glfw:3.4.1")
    implementation("org.lwjgl:lwjgl-vulkan:3.4.1")
    implementation("org.lwjgl:lwjgl-shaderc:3.4.1")

    runtimeOnly("org.lwjgl:lwjgl:3.4.1:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.4.1:natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-shaderc:3.4.1:natives-linux")

    runtimeOnly("org.lwjgl:lwjgl:3.4.1:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.4.1:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-shaderc:3.4.1:natives-windows")

    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test{
    useJUnitPlatform()
}

