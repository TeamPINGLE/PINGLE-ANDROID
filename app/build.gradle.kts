import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.sentry)
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "org.sopt.pingle"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.sopt.pingle"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", properties["base.url"].toString())
        buildConfigField("String", "ACCESS_TOKEN", properties["access.token"].toString())
        buildConfigField(
            "String",
            "NAVER_MAP_CLIENT_ID",
            properties["naver.map.client.id"].toString()
        )
        manifestPlaceholders["IO_SENTRY_DSN"] = properties["io.sentry.dsn"] as String
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
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
        buildConfig = true
    }
}

dependencies {

    implementation(libs.bundles.androidx)

    // Google
    implementation(libs.google.material)
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.crashlytics)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    // Coil
    implementation(libs.coil)

    // Timber
    implementation(libs.timber)

    // Network
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.retrofit)
    implementation(libs.kotlin.serialization.json)

    // Naver Map
    implementation(libs.naver.maps)

    // Location
    implementation(libs.play.services.location)

    // progress Bar
    implementation(libs.progress.bar)
}

ktlint {
    android = true
    debug = true
    coloredOutput = true
    verbose = true
    outputToConsole = true
}
