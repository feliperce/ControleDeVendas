plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = Config.getNameSpaceByModuleName("design")
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
}