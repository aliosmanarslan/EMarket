plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.safeArgs)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.aliosmanarslan.emarket"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aliosmanarslan.emarket"
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
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    //Android Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //LifeCycle
    implementation(libs.androidx.lifecycle.common)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.lifeCycleExtensions)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttpLogging)
    implementation(libs.converter.scalars)
    implementation(libs.retrofitRxJava)

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    //Room
    implementation(libs.room)
    kapt(libs.roomCompiler)
    implementation(libs.roomKtx)

    //Navigation
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)

    //Preferences
    implementation(libs.preferences)

    //RxJava
    implementation(libs.rxJava)
    implementation(libs.rxAndroid)

    //Legacy
    implementation(libs.legacySupport)

    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //Airbnb
    implementation(libs.lottie)

    //Glide
    implementation(libs.glide)
    kapt(libs.compiler)

    //DataStore
    implementation(libs.datastore)
}