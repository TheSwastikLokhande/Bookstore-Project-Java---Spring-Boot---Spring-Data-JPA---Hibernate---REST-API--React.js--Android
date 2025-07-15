plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.bookstoreapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.bookstoreapp"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // ✅ Core AndroidX libraries
    implementation(libs.appcompat)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // ✅ Material 3 (latest version)
    implementation("com.google.android.material:material:1.12.0")

    // ✅ Retrofit for REST APIs
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // ✅ Logging Interceptor (optional but useful for debugging API calls)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // ✅ Testing libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

