object Versions {
    const val core = "1.12.0"
    const val appcompat = "1.6.1"
    const val androidMaterial = "1.11.0"
    const val constraintLayout = "2.1.4"
    const val testImplJunit = "4.13.2"
    const val androidTestImplJunit = "1.1.5"
    const val androidTestEspresso = "3.5.1"
    const val retrofit = "2.9.0"
    const val gsonConvertor = "2.9.0"
    const val okHttp = "5.0.0-alpha.11"
    const val scalerConvertor = "2.1.0"
    const val converter_moshi = "2.9.0"
    const val moshi = "1.15.0"
    const val kotlinCoroutines = "1.6.1"
    const val coroutineLifecycleScope = "2.5.1"
    const val viewModelDeligate = "1.6.0"
    const val dagger = "2.50"
    const val hiltCompiler = "1.0.0"
    const val roomVersion = "2.6.1"
    const val kotlin_coroutines_adapter = "0.9.2"
    const val lifecycle_version = "2.6.1"
    const val kotlin_stdlib_jdk7 = "1.8.0"
    const val kotlin_stdlib_jdk8 = "1.8.0"
    const val fragment = "1.3.0"
    const val leakcanary = "2.12"
    const val kotlin_bom = "1.9.10"
    const val test_junit = "5.8.2"
    const val mockito_core = "4.0.0"
    const val mockito_kotlin = "4.0.0"
    const val mockito_inline = "3.11.2"
    const val navigation = "2.7.7"
    const val navigation_fragment = "2.7.7"
    const val flexbox = "3.0.0"
    const val gson = "2.8.7"
}

object Navigation{
    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation_fragment}"
    const val navigation = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
}

object Libraries{
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
    const val flexbox = "com.google.android.flexbox:flexbox:${Versions.flexbox}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
}

object Deps {

    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val androidMaterial = "com.google.android.material:material:${Versions.androidMaterial}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val kotlin_bom = "org.jetbrains.kotlin:kotlin-bom:${Versions.kotlin_bom}"
}

object TestImplementation {

    const val junit = "junit:junit:${Versions.testImplJunit}"
}

object AndroidTestImplementation {

    const val junit = "androidx.test.ext:junit:${Versions.androidTestImplJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidTestEspresso}"
    const val test_junit = "org.junit.jupiter:junit-jupiter:${Versions.test_junit}"
    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"
    const val mockito_kotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockito_kotlin}"
    const val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito_inline}"
}


object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConvertor = "com.squareup.retrofit2:converter-gson:${Versions.gsonConvertor}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val scalersConvertors =
        "com.squareup.retrofit2:converter-scalars:${Versions.scalerConvertor}"
    const val converter_moshi = "com.squareup.retrofit2:converter-moshi:${Versions.converter_moshi}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshi_adapter = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val kotlin_coroutine_adapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.kotlin_coroutines_adapter}"
    const val okhttp_bom = "com.squareup.okhttp3:okhttp-bom:${Versions.okHttp}"
}

object Coroutines {
    const val coroutineCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
    const val kotlin_stdlib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_stdlib_jdk7}"
    const val kotlin_stdlib_jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin_stdlib_jdk8}"

}

object CoroutinesLifecycleScope {
    const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.coroutineLifecycleScope}"
    const val lifeCycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.coroutineLifecycleScope}"
}

object ViewModelDelegate {
    const val viewModelDeligate = "androidx.activity:activity-ktx:${Versions.viewModelDeligate}"
}

object DaggerHilt {
    const val hilt = "com.google.dagger:hilt-android:${Versions.dagger}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.dagger}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
}

object Room {
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val room = "androidx.room:room-ktx:${Versions.roomVersion}"
    const val room_runtime = "androidx.room:room-runtime:${Versions.roomVersion}"
}