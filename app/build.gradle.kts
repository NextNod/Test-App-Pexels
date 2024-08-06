plugins {
    id("com.google.devtools.ksp")
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.test.pexels"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.test.pexels"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false

            buildConfigField("String", "PIXELS_API_KEY", "\"563492ad6f9170000100000106ce478899e241f5a1601d03fad32057\"")
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true

            buildConfigField("String", "PIXELS_API_KEY", "\"563492ad6f9170000100000106ce478899e241f5a1601d03fad32057\"")

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
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    // Navigation
    implementation(libs.cicerone)

    // Dagger
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    // Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.gson)
    implementation(libs.okhttp3.logging)

    // Compose
    platform("androidx.compose:compose-bom:2024.06.00").also { composeBom ->
        implementation(composeBom)
        androidTestImplementation(composeBom)
    }

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.viewmodel)
    implementation(libs.androidx.compose.livedata)

    // Glide
    implementation(libs.glide)
    implementation(libs.glide.compose)

    // Dexter (Permission)
    implementation(libs.dexter)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)


    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}