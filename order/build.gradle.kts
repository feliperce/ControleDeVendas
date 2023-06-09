plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = Config.getNameSpaceByModuleName("order")
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeKotlinCompiler
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.Androidx.core)
    implementation(Dependencies.Androidx.Lifecycle.runtimeKtx)
    implementation(Dependencies.Androidx.Compose.activity)

    testImplementation(Dependencies.Junit.junit)
    androidTestImplementation(Dependencies.Androidx.junit)
    androidTestImplementation(Dependencies.Androidx.espresso)

    val composeBom = platform(Dependencies.Androidx.Compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(Dependencies.Androidx.Compose.material)
    implementation(Dependencies.Androidx.Compose.ui)
    implementation(Dependencies.Androidx.Compose.toolingPreview)
    debugImplementation(Dependencies.Androidx.Compose.uiTooling)
    androidTestImplementation(Dependencies.Androidx.Compose.junit)
    debugImplementation(Dependencies.Androidx.Compose.testManifest)
    implementation(Dependencies.Androidx.Compose.viewmodel)
    implementation(Dependencies.Androidx.Compose.livedata)
    implementation(Dependencies.Androidx.Compose.iconsCore)
    implementation(Dependencies.Androidx.Compose.iconsExtended)
    implementation(Dependencies.Androidx.Compose.activity)
    implementation(Dependencies.Androidx.Compose.constraint)

    implementation(Dependencies.Androidx.Navigation.compose)

    // Koin
    implementation(Dependencies.Koin.koin)
    implementation(Dependencies.Koin.compose)

    // Room
    implementation(Dependencies.Androidx.Room.runtime)
    annotationProcessor(Dependencies.Androidx.Room.compiler)
    kapt(Dependencies.Androidx.Room.compiler)
    implementation(Dependencies.Androidx.Room.ktx)

    implementation(project(":design"))
    implementation(project(":data"))
    implementation(project(":commons"))
}