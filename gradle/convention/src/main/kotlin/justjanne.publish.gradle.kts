plugins {
  id("maven-publish")
  id("signing")
}

version = rootProject.version
group = rootProject.group

publishing {
  publications {
    create<MavenPublication>("maven") {
      publication()
      pom()
    }
  }
}

configure<SigningExtension> {
  sign(publishing.publications["maven"])
}

fun MavenPublication.pom() {
  pom {
    name.set(buildHumanReadableName(artifactId))
    description.set("example mod")
    url.set("https://github.com/ACCOUNT/REPOSITORY")
    licenses {
      license {
        name.set("Mozilla Public License Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
    developers {
      developer {
        name.set("Jane Doe")
        email.set("user@example.com")
      }
    }
    scm {
      connection.set("https://github.com/ACCOUNT/REPOSITORY.git")
      developerConnection.set("scm:git:ssh:git@github.com:ACCOUNT/REPOSITORY.git")
      url.set("https://github.com/ACCOUNT/REPOSITORY")
    }
  }
}

fun MavenPublication.publication() {
  val projectName = project.name
    .removePrefix("core")
    .removePrefix("-")
  artifactId = buildArtifactName(
    rootProject.name,
    projectName.ifEmpty { null }
  )
  from(components["java"])
}

fun buildArtifactName(group: String? = null, project: String? = null, module: String? = null): String {
  return listOfNotNull(group, project, module).flatMap { it.split('-') }
    .joinToString("-")
}

fun buildHumanReadableName(name: String) = name
  .splitToSequence('-')
  .joinToString(" ", transform = String::capitalize)
