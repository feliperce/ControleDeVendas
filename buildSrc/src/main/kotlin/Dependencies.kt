object Dependencies {

    object Androidx {
        const val core = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val junit = "androidx.test.ext:junit:${Versions.androidXJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"

        object Compose {
            const val bom = "androidx.compose:compose-bom:${Versions.composeBom}"
            const val ui = "androidx.compose.ui:ui"
            const val material = "androidx.compose.material:material"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview"
            const val uiTooling = "androidx.compose.ui:ui-tooling"
            const val junit = "androidx.compose.ui:ui-test-junit4"
            const val testManifest = "androidx.compose.ui:ui-test-manifest"
            const val livedata = "androidx.compose.runtime:runtime-livedata"
            const val iconsCore = "androidx.compose.material:material-icons-core"
            const val iconsExtended = "androidx.compose.material:material-icons-extended"
            const val activity = "androidx.activity:activity-compose:${Versions.composeActivity}"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
            const val constraint = "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintCompose}"
        }

        object Lifecycle {
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycleRuntimeKtx}"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleRuntimeKtx}"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:${Versions.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.room}"
            const val ktx = "androidx.room:room-ktx:${Versions.room}"
        }

        object Navigation {
            const val compose = "androidx.navigation:navigation-compose:${Versions.composeNav}"
        }
    }

    object Junit {
        const val junit = "junit:junit:${Versions.jUnit}"
    }

    object Koin {
        const val koin = "io.insert-koin:koin-android:${Versions.koin}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    }

    object Mockk {
        const val mockk = "io.mockk:mockk:"
    }
}