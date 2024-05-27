plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.mvvmmovies.notesappusingroomdb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mvvmmovies.notesappusingroomdb"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    // ssp
    implementation(libs.sspVersion)
    //sdp
    implementation(libs.sdpVersion)

    // room database
    implementation(libs.androidx.room.runtime)
    kapt ("androidx.room:room-compiler:2.6.1")


//    annotationProcessor(libs.androidx.room.compiler)

    androidTestImplementation(libs.androidx.espresso.core)
}