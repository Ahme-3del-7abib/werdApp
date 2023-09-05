plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.ahmed.a.habib.werdapp"

    defaultConfig {
        applicationId = "com.ahmed.a.habib.werdapp"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

apply(from = "${rootProject.projectDir}/shared-library.gradle")

dependencies {

    implementation(
        project(":domain")
    )

    // Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")

    // Circle progress bar
    implementation("com.mikhaellopez:circularprogressbar:3.1.0")

    // Sdp
    implementation("com.intuit.sdp:sdp-android:1.0.6")

    // Circle Imageview
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("androidx.navigation:navigation-ui-ktx:2.7.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.1")
}