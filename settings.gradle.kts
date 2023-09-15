rootProject.name = "cafe"
include("infrastructure")
include("infrastructure:database")
findProject(":infrastructure:database")?.name = "database"

pluginManagement {
    val kotlinVersion = "1.8.22"
    val springBootVersion = "3.1.3"
    val springDependencyManagementVersion = "1.0.11.RELEASE"

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
            }
        }
    }
}