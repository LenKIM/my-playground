plugins {
    id("buildsrc.convention.kotlin-jvm")
}

dependencies {
    implementation(project(":utils"))
    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:3.26.3") // AssertJ 직접 추가

}