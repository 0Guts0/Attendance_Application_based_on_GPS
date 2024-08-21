plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.attendanceapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.attendanceapplication"
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
}

dependencies {
    implementation(libs.utilcodex)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("androidx.sqlite:sqlite:2.1.0")
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.github.maning0303:MNZXingCode:V2.1.9")

}