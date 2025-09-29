plugins {
    id("buildsrc.convention.kotlin-jvm")
    application
}

dependencies {
    implementation(project(":utils"))
}

application {
    mainClass = "io.agistep.app.AppKt"
}
