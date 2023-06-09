plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = Config.getNameSpaceByModuleName("data")
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Dependencies.Androidx.core)
    implementation(Dependencies.Androidx.Lifecycle.runtimeKtx)

    // Room
    implementation(Dependencies.Androidx.Room.runtime)
    annotationProcessor(Dependencies.Androidx.Room.compiler)
    kapt(Dependencies.Androidx.Room.compiler)
    implementation(Dependencies.Androidx.Room.ktx)

    // Koin
    implementation(Dependencies.Koin.koin)
    implementation(Dependencies.Koin.compose)
}