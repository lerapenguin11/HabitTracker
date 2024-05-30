import java.util.Properties

plugins {
    id(libs.plugins.androidApplication.get().pluginId)
    id(libs.plugins.kotlinAndroid.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.habittracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.habittracker"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        //jvmTarget = "1.8"
        jvmTarget = "17"
    }
    buildFeatures{
        viewBinding = true
    }
    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md"
            )
        )
    }
}

dependencies {
    implementation(project(":feature_habit:habit_presentation"))
    implementation(project(":feature_habit:habit_domain"))
    implementation(project(":feature_habit:habit_data"))
    implementation(project(":core"))

    implementation(libs.android.core.ktx)
    implementation(libs.android.appcompat)
    implementation(libs.android.material)
    implementation(libs.android.constraintlayout)
    implementation(libs.bundles.navigation.all)
    implementation(libs.android.room)
    implementation(libs.android.room.runtime)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.android.room.compiler)
    implementation(libs.bundles.kotlin.coroutines.all)
    implementation(libs.bundles.coroutine.lifecycle.scope.all)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.converter.scalars)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlin.coroutines.adapter)
    implementation(libs.glide)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.android.test.ext.junit)
    androidTestImplementation(libs.android.test.espresso.core)

    androidTestImplementation(libs.android.components.kaspresso)
    androidTestUtil("androidx.test:orchestrator:1.4.2")

    debugImplementation("androidx.fragment:fragment-testing:1.7.1")
    debugImplementation("androidx.fragment:fragment-ktx:1.7.1")
    debugImplementation("androidx.test:core:1.5.0")
    debugImplementation("androidx.test:rules:1.5.0")
    debugImplementation("androidx.test:runner:1.5.2")

    /*debugImplementation("androidx.fragment:fragment-testing-manifest:1.6.0"){
        isTransitive = false
    }*/
    androidTestImplementation("io.mockk:mockk-android:1.13.11")
    androidTestImplementation("io.mockk:mockk-agent:1.13.11")
    api(libs.uiAutomator)
    api(libs.kakao)
    api(libs.kakaoExtClicks)

    androidTestImplementation(libs.mockito.core)

    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito:2.28.1")

    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")

    debugImplementation("androidx.fragment:fragment-testing:1.7.1")
    androidTestImplementation("com.kaspersky.android-components:kaspresso:1.3.0")
    testImplementation("com.kaspersky.android-components:kaspresso:1.5.3")
}