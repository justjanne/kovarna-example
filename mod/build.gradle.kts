import net.minecraftforge.gradle.common.util.MinecraftExtension
import org.gradle.jvm.tasks.Jar
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

plugins {
  id("justjanne.kotlin")
  id("justjanne.publish")
  id("kovarna-plugin") version "0.0.2"
}

kovarna {
  withKotlin.set(true)
}

repositories {
  mavenLocal()
}

dependencies {
  // Specify the version of Minecraft to use. If this is any group other than "net.minecraft", it is assumed
  // that the dep is a ForgeGradle "patcher" dependency, and its patches will be applied.
  // The userdev artifact is a special name and will get all sorts of transformations applied to it.
  minecraft("net.minecraftforge:forge:1.18.1-39.0.66")

  // Real mod deobf dependency examples - these get remapped to your current mappings
  // compileOnly fg.deobf("mezz.jei:jei-${mc_version}:${jei_version}:api") // Adds JEI API as a compile dependency
  // runtimeOnly fg.deobf("mezz.jei:jei-${mc_version}:${jei_version}") // Adds the full JEI mod as a runtime dependency
  // implementation fg.deobf("com.tterrag.registrate:Registrate:MC${mc_version}-${registrate_version}") // Adds registrate as a dependency

  // Examples using mod jars from ./libs
  // implementation fg.deobf("blank:coolmod-${mc_version}:${coolmod_version}")

  // For more info...
  // http://www.gradle.org/docs/current/userguide/artifact_dependencies_tutorial.html
  // http://www.gradle.org/docs/current/userguide/dependency_management.html
}

// Example for how to get properties into the manifest for reading at runtime.
tasks.withType<Jar> {
  archiveBaseName.set("examplemod")

  manifest {
    attributes(
      mapOf(
        "Specification-Title" to "examplemod",
        "Specification-Vendor" to "examplemodsareus",
        "Specification-Version" to "1", // We are version 1 of ourselves
        "Implementation-Title" to project.name,
        "Implementation-Version" to "${rootProject.version}",
        "Implementation-Vendor" to "examplemodsareus",
        "Implementation-Timestamp" to Instant.now()
          .atZone(ZoneOffset.UTC)
          .format(DateTimeFormatter.ISO_DATE_TIME)
      )
    )
  }
}

configure<MinecraftExtension> {
  // The mappings can be changed at any time and must be in the following format.
  // Channel:   Version:
  // snapshot   YYYYMMDD   Snapshot are built nightly.
  // stable     #          Stables are built at the discretion of the MCP team.
  // official   MCVersion  Official field/method names from Mojang mapping files
  //
  // You must be aware of the Mojang license when using the "official" mappings.
  // See more information here: https://github.com/MinecraftForge/MCPConfig/blob/master/Mojang.md
  //
  // Use non-default mappings at your own risk. They may not always work.
  // Simply re-run your setup task after changing the mappings to update your workspace.
  mappings("official", "1.18.1")

  runs {
    create("client") {
      workingDirectory(project.file("run"))

      // Recommended logging data for a userdev environment
      // The markers can be added/remove as needed separated by commas.
      // "SCAN": For mods scan.
      // "REGISTRIES": For firing of registry events.
      // "REGISTRYDUMP": For getting the contents of all registries.
      property("forge.logging.markers", "REGISTRIES")

      // Recommended logging level for the console
      // You can set various levels here.
      // Please read: https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
      property("forge.logging.console.level", "debug")

      mods {
        create("examplemod") {
          source(sourceSets.main.get())
        }
      }
    }

    create("server") {
      workingDirectory(project.file("run"))

      // Recommended logging data for a userdev environment
      // The markers can be added/remove as needed separated by commas.
      // "SCAN": For mods scan.
      // "REGISTRIES": For firing of registry events.
      // "REGISTRYDUMP": For getting the contents of all registries.
      property("forge.logging.markers", "REGISTRIES")

      // Recommended logging level for the console
      // You can set various levels here.
      // Please read: https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
      property("forge.logging.console.level", "debug")

      mods {
        create("examplemod") {
          source(sourceSets.main.get())
        }
      }
    }

    create("data") {
      workingDirectory(project.file("run"))

      // Recommended logging data for a userdev environment
      // The markers can be added/remove as needed separated by commas.
      // "SCAN": For mods scan.
      // "REGISTRIES": For firing of registry events.
      // "REGISTRYDUMP": For getting the contents of all registries.
      property("forge.logging.markers", "REGISTRIES")

      // Recommended logging level for the console
      // You can set various levels here.
      // Please read: https://stackoverflow.com/questions/2031163/when-to-use-the-different-log-levels
      property("forge.logging.console.level", "debug")

      // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
      args(
        "--mod",
        "examplemod",
        "--all",
        "--output",
        file("src/generated/resources/"),
        "--existing",
        file("src/main/resources/")
      )

      mods {
        create("examplemod") {
          source(sourceSets.main.get())
        }
      }
    }
  }
}
