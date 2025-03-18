plugins {
    alias(libs.plugins.android.application) apply false  // Cấu hình đúng plugin Android ở cấp độ dự án
    id("com.google.gms.google-services") version "4.4.2" apply false
}


