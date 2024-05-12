plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.ui"
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
        }
        getByName("debug") {
            // 개발여부설정 : true
            buildConfigField("boolean", "DEV", "true")
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
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
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

    // JUnit
    testImplementation(Dependency.JUnit.JUNIT)

    // Glide
    kapt(Dependency.Glide.GLIDE_COMPILER)
    implementation(Dependency.Glide.GLIDE_OKHTTP_INTEGRATION)
    implementation(Dependency.Glide.GLIDE_TRANSFORMATIONS)

    // Dagger Hilt
    implementation(Dependency.DaggerHilt.DAGGER_HILT)
    kapt(Dependency.DaggerHilt.DAGGER_HILT_COMPILER)
    kapt(Dependency.DaggerHilt.DAGGER_HILT_ANDROIDX_COMPILER)

    // Coroutine
    implementation(Dependency.Coroutines.COROUTINES)
    implementation(Dependency.Coroutines.COROUTINES_CORE)

    // Logger
    implementation(Dependency.Logger.LOGGER)

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}