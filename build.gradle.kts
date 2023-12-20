plugins {
    `java`
    `application`
    id("org.openjfx.javafxplugin") version "0.1.0"
}

javafx {
    version = "21.0.1"
    modules = listOf("javafx.controls", "javafx.media")
}

repositories {
    mavenCentral()
}

dependencies {
	
	// https://mvnrepository.com/artifact/com.bernardomg.tabletop/dice
	implementation("com.bernardomg.tabletop:dice:2.2.4")

	// https://mvnrepository.com/artifact/org.tinylog/tinylog	
	implementation("org.tinylog:tinylog-api:2.6.2")
	implementation("org.tinylog:tinylog-impl:2.6.2")
	
	// https://mvnrepository.com/artifact/org.slf4j/slf4j-nop
	implementation("org.slf4j:slf4j-nop:2.0.9")

	
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
