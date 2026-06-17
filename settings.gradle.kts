rootProject.name = "NothingLauncher"

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

include(
    ":app",
    ":core:theme",
    ":core:icons",
    ":core:data",
    ":core:utils",
    ":launcher:home",
    ":launcher:drawer",
    ":launcher:dock",
    ":launcher:widgets",
    ":launcher:wallpaper",
    ":launcher:settings"
)
