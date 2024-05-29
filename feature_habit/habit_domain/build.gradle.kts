import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin.Companion.isUseJvmIr

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt")
    id(libs.plugins.kotlin.parcelize.get().pluginId)
}

android {
    namespace = "com.example.habit_domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions{
        unitTests.all {
            test -> test.useJUnitPlatform()
        }
    }
}

dependencies {
    implementation(project(":core"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.20")

    implementation(libs.android.core.ktx)
    implementation(libs.android.appcompat)
    implementation(libs.android.material)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    //testImplementation(libs.junit)
    androidTestImplementation(libs.android.test.ext.junit)
    androidTestImplementation(libs.android.test.espresso.core)

    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("org.mockito:mockito-junit-jupiter:4.0.0")
}