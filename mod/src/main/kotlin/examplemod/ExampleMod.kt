package examplemod

import de.justjanne.modding.kovarna.KotlinModLoadingContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.server.ServerStartingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.InterModComms
import net.minecraftforge.fml.InterModComms.IMCMessage
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent
import org.apache.logging.log4j.LogManager
import java.util.stream.Collectors

// The value here should match an entry in the META-INF/mods.toml file
@Mod("examplemod")
object ExampleMod {
  // Directly reference a log4j logger.
  private val LOGGER = LogManager.getLogger("ExampleMod")

  init {
    // Register the setup method for modloading
    KotlinModLoadingContext.get().modEventBus.addListener { event: FMLCommonSetupEvent ->
      setup(event)
    }
    // Register the enqueueIMC method for modloading
    KotlinModLoadingContext.get().modEventBus.addListener { event: InterModEnqueueEvent ->
      enqueueIMC(event)
    }
    // Register the processIMC method for modloading
    KotlinModLoadingContext.get().modEventBus.addListener { event: InterModProcessEvent ->
      processIMC(event)
    }

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this)
  }

  private fun setup(event: FMLCommonSetupEvent) {
    // some preinit code
    LOGGER.info("HELLO FROM PREINIT")
    LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.registryName)
  }

  private fun enqueueIMC(event: InterModEnqueueEvent) {
    // some example code to dispatch IMC to another mod
    InterModComms.sendTo("examplemod", "helloworld") {
      LOGGER.info("Hello world from the MDK")
      "Hello world"
    }
  }

  private fun processIMC(event: InterModProcessEvent) {
    // some example code to receive and process InterModComms from other mods
    LOGGER.info("Got IMC {}", event.imcStream.map { m: IMCMessage ->
      m.messageSupplier().get()
    }.collect(Collectors.toList()))
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  fun onServerStarting(event: ServerStartingEvent?) {
    // do something when the server starts
    LOGGER.info("HELLO from server starting")
  }

  // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
  // Event bus for receiving Registry Events)
  @EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
  class RegistryEvents {
    @SubscribeEvent
    fun onBlocksRegistry(blockRegistryEvent: RegistryEvent.Register<Block?>) {
      // register a new block here
      LOGGER.info("HELLO from Register Block")
      val properties = BlockBehaviour.Properties.of(Material.AMETHYST)
      val block = Block(properties)
      block.setRegistryName("examplemod", "exampleblock")
      blockRegistryEvent.registry.register(block)
    }
  }
}
