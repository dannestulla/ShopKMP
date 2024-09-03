plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

tasks.register("androidDebugBuild") {
    description = "Assembles the debug variants of the shared and Android modules."
    group = "Build"

    dependsOn(":shared:assembleDebug", ":android:assembleDebug")
}

tasks.register("androidRebuild") {
    description = "Cleans and builds the debug variants of the shared and Android modules."
    group = "Build"

    dependsOn(":shared:clean", ":shared:assembleDebug", ":android:clean", ":android:assembleDebug")
}