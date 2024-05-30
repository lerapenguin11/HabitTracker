import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        aidl = true
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_URL", "\"https://droid-test-server.doubletapp.ru/api/\"")
            buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
        }
        debug {
            buildConfigField("String", "API_URL", "\"https://droid-test-server.doubletapp.ru/api/\"")
            buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.android.core.ktx)
    implementation(libs.android.appcompat)
    implementation(libs.android.material)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.converter.scalars)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlin.coroutines.adapter)
    implementation(libs.android.room)
    implementation(libs.android.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.android.room.compiler)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.android.test.ext.junit)
    androidTestImplementation(libs.android.test.espresso.core)
}