plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.finalprm392"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.finalprm392"
        minSdk = 27
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)

    implementation("androidx.room:room-runtime:2.7.2")

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    annotationProcessor("androidx.room:room-compiler:2.7.2")

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}