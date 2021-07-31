package com.mcmoddev.htwtweaks;

import com.mcmoddev.htwtweaks.data.HammerRecipe;
import com.mcmoddev.htwtweaks.misc.HammerLootCondition;
import com.mcmoddev.htwtweaks.transport.LaserTransportNodeBase;
import com.mcmoddev.htwtweaks.transport.containers.LaserTransportContainer;
import com.mcmoddev.htwtweaks.transport.containers.LaserTransportScreen;
import com.mcmoddev.htwtweaks.transport.containers.LaserTransportTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("htwtweaks")
public class HighTechWolvesTweaks {
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

	@SuppressWarnings("ConstantConditions")
	@Nonnull
	private static <T> T toBeInitializedLater()
	{
		return null;
	}

    @ObjectHolder("htwtweaks:laser_transport_node")
	public static Block TRANSPORT_NODE = toBeInitializedLater();

    public HighTechWolvesTweaks() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, this::registerRecipeSerializers);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(GlobalLootModifierSerializer.class, this::lootModifiers);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, this::blockRegistration);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, this::tileEntities);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, this::registerContainers);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
		event.enqueueWork(() -> {
			ScreenManager.registerFactory(LaserTransportContainer.TYPE, LaserTransportScreen::new);
		});
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK"); return "Hello world";}
        );
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

	private void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		event.getRegistry().registerAll( new HammerRecipe.Serializer().setRegistryName("crushing"));
	}

	private void tileEntities(RegistryEvent.Register<TileEntityType<?>> ev) {
    	ev.getRegistry().register(TileEntityType.Builder.create(LaserTransportTileEntity::new, TRANSPORT_NODE).build(null).setRegistryName("laser_transport_node_tet"));
	}

	public void registerContainers(RegistryEvent.Register<ContainerType<?>> event)
	{
		event.getRegistry().register(new ContainerType<>(LaserTransportContainer::new).setRegistryName("laser_transport_node_container"));
	}

	private void lootModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event)
	{
		event.getRegistry().register(
			new HammerLootCondition.Serializer().setRegistryName(new ResourceLocation("htwtweaks","hammer_crushing"))
		);
	}

	private void blockRegistration(RegistryEvent.Register<Block> ev) {
    	ev.getRegistry().register(new LaserTransportNodeBase(AbstractBlock.Properties.create(Material.IRON),
			(state, world) -> (TileEntity)(new LaserTransportTileEntity()),	null,
			(id, inv, position) -> new LaserTransportContainer(id, inv, position)).setRegistryName("laser_transport_node"));
	}

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
}
