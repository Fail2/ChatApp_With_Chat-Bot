plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.audiovideocall"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.audiovideocall"
        minSdk = 26
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // for jetpack compose
        kotlinCompilerExtensionVersion = "1.5.15" // Use the latest version
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.database)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //for jetpack compose
    implementation("androidx.compose.ui:ui:1.7.5")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation ("androidx.activity:activity-compose:1.9.3")




    implementation (platform("com.google.firebase:firebase-bom:32.0.0")) // Use the latest BOM version

    implementation ("com.google.firebase:firebase-database:20.0.7")
    implementation ("com.google.firebase:firebase-auth:22.1.1")


    implementation ("com.google.firebase:firebase-database:20.0.3")
    implementation ("com.google.firebase:firebase-auth:21.0.3")
    implementation ("com.google.firebase:firebase-storage:20.0.0")

    implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

    implementation ("com.github.ZEGOCLOUD:zego_uikit_prebuilt_call_android:+")

}