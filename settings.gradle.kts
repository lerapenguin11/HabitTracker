pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Habit Tracker"
include(":app")
include(":feature_habit:habit_data")
include(":feature_habit:habit_presentation")
include(":core")
include(":base_ui")
include(":feature_habit:habit_domain")
