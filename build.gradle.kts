plugins {
    scala
    java
//  idea
}

repositories {
    mavenCentral()
}

//idea {
//    module {
//        setDownloadSources(true)
//    }
//}

java{
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.withType<ScalaCompile>().configureEach {
    scalaCompileOptions.additionalParameters.add("-Xunchecked-java-output-version:25")
}

dependencies {

    implementation("org.scala-lang:scala3-library_3:3.8.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


