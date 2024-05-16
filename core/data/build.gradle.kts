import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.data"
    compileSdk = SdkVersions.compileSdk

    defaultConfig {
        minSdk = SdkVersions.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // 개발여부설정 : false
            buildConfigField ("boolean", "DEV", "false")
            // API KEY
            buildConfigField("String", "REST_API_KET", getKakaoRestApiKey())
            // Base Url
            buildConfigField("String", "BASE_URL", getKakaoBaseUrl())

        }
        getByName("debug") {
            // 개발여부설정 : true
            buildConfigField("boolean", "DEV", "true")
            // API KEY
            buildConfigField("String", "REST_API_KET", getKakaoDevRestApiKey())
            // Base Url
            buildConfigField("String", "BASE_URL", getKakaoDevBaseUrl())
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

fun getKakaoRestApiKey(): String = gradleLocalProperties(rootDir).getProperty("KAKAO_REST_API_KEY", "")
fun getKakaoDevRestApiKey(): String = gradleLocalProperties(rootDir).getProperty("KAKAO_DEV_REST_API_KEY", "")
fun getKakaoBaseUrl(): String = gradleLocalProperties(rootDir).getProperty("KAKAO_BASE_URL", "")
fun getKakaoDevBaseUrl(): String = gradleLocalProperties(rootDir).getProperty("KAKAO_DEV_BASE_URL", "")

dependencies {
    implementation(project(":core:domain"))
    // AndroidX
    implementation(Dependency.AndroidX.CORE)
    implementation(Dependency.AndroidX.APPCOMPAT)
    implementation(Dependency.AndroidX.CONSTRAINTLAYOUT)
    implementation(Dependency.AndroidX.ACTIVITY)
    implementation(Dependency.AndroidX.FRAGMENT)
    implementation(Dependency.AndroidX.LIFECYCLE_VIEW_MODEL)

    // AndroidX Test
    androidTestImplementation(Dependency.AndroidX.TEST_EXT_JUNIT)
    androidTestImplementation(Dependency.AndroidX.TEST_ESPRESSO)

    // Google
    implementation(Dependency.Google.MATERIAL)
    implementation(Dependency.Google.GSON)

    // JUnit
    testImplementation(Dependency.JUnit.JUNIT)

    // Moshi
    implementation(Dependency.Moshi.MOSHI)

    // Retrofit
    implementation(Dependency.Retrofit.RETROFIT)
    implementation(Dependency.Retrofit.CONVERTER_MOSHI)
    implementation(Dependency.Retrofit.CONVERTER_SCALARS)
    implementation(Dependency.Retrofit.CONVERTER_JAXB)

    // OkHttp
    implementation(Dependency.OkHttp.OKHTTP)
    implementation(Dependency.OkHttp.URL_CONNECTION)
    implementation(Dependency.OkHttp.LOGGER_INTERCEPTOR)
    implementation(Dependency.OkHttp.OKHTTP_PROFILER)

    // Dagger Hilt
    implementation(Dependency.DaggerHilt.DAGGER_HILT)
    kapt(Dependency.DaggerHilt.DAGGER_HILT_COMPILER)
    kapt(Dependency.DaggerHilt.DAGGER_HILT_ANDROIDX_COMPILER)

    // Coroutine
    implementation(Dependency.Coroutines.COROUTINES)
    implementation(Dependency.Coroutines.COROUTINES_CORE)

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
