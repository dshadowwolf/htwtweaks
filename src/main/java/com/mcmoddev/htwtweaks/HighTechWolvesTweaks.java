package com.mcmoddev.htwtweaks;

import com.mcmoddev.htwtweaks.data.HammerRecipe;
import com.mcmoddev.htwtweaks.misc.HammerLootCondition;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.crafting.IRecipeSerializer;
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
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, this::registerRecipeSerializers);
		FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(GlobalLootModifierSerializer.class, this::lootModifiers);
    }

	private void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		event.getRegistry().registerAll( new HammerRecipe.Serializer().setRegistryName("crushing"));
	}

	private void lootModifiers(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event)
	{
		event.getRegistry().register(
			new HammerLootCondition.Serializer().setRegistryName(new ResourceLocation("htwtweaks","hammer_crushing"))
		);
	}
}
