@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    id(libs.plugins.kotlin.kapt.get().pluginId)
}

android {
    namespace = "com.example.habit_data"
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
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature_habit:habit_domain"))

    implementation(libs.android.core.ktx)
    implementation(libs.android.appcompat)
    implementation(libs.android.material)
    implementation(libs.android.room)
    implementation(libs.android.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.android.room.compiler)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.converter.scalars)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlin.coroutines.adapter)
    testImplementation(libs.junit)
    androidTestImplementation(libs.android.test.ext.junit)
    androidTestImplementation(libs.android.test.espresso.core)
}