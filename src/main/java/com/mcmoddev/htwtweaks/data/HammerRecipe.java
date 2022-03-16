package com.mcmoddev.htwtweaks.data;

import com.google.gson.JsonObject;
import com.mcmoddev.htwtweaks.HighTechWolvesTweaks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class HammerRecipe implements IForgeRegistryEntry<HammerRecipe>, IRecipe<IInventory> {
	private ResourceLocation name;
	private final Ingredient sourceIngredient;
	private final ItemStack resultItem;

	@ObjectHolder("htwtweaks:crushing")
	public static final IRecipeSerializer<?> SERIALIZER = null;

	public static final IRecipeType<HammerRecipe> CRUSHING = IRecipeType.register(new ResourceLocation("htwtweaks","crushing").toString());

	public HammerRecipe(final ResourceLocation recipeName, final Ingredient input, final ItemStack output) {
		this.name = recipeName;
		this.sourceIngredient = input;
		this.resultItem = output;
	}

	public HammerRecipe(final String name, Ingredient source, ItemStack result) {
		this(new ResourceLocation(name), source, result);
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.withSize(1, sourceIngredient);
	}

	// thank you, @Gigaherz, for this idea...
	public static Collection<HammerRecipe> getAllRecipes(World world)
	{
		return world.getRecipeManager().getRecipesForType(CRUSHING);
	}

	public HammerRecipe setRegistryName(String name) {
		return setRegistryName(new ResourceLocation(name));
	}

	@Override
	public HammerRecipe setRegistryName(ResourceLocation name) {
		this.name = name;
		return this;
	}

	@Nullable
	@Override
	public ResourceLocation getRegistryName() {
		return this.name;
	}

	@Override
	public Class<HammerRecipe> getRegistryType() {
		return HammerRecipe.class;
	}

	public boolean matches(ItemStack ingredient) {
		List<ItemStack> matches = Arrays.stream(this.sourceIngredient.getMatchingStacks()).filter(item -> item.isItemEqual(ingredient)).collect(Collectors.toList());
		return !matches.isEmpty();
	}

	public boolean matches(Ingredient ingredient) {
		List<ItemStack> matches = Arrays.stream(this.sourceIngredient.getMatchingStacks()).filter(ingredient).collect(Collectors.toList());
		return !matches.isEmpty();
	}

	public ItemStack getResult() {
		return this.resultItem.copy();
	}

	@Override
	public boolean matches(@Nonnull final IInventory inv, @Nonnull final World worldIn) {
		for( int i = 0; i < inv.getSizeInventory(); i++)
			if (matches(inv.getStackInSlot(i))) return true;

		return false;
 	}

	@Override
	public ItemStack getIcon()
	{
		return new ItemStack(ModItems.WOODEN_HAMMER);
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		if (matches(inv, null)) return this.resultItem.copy();
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canFit(int width, int height) {
		return true;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.resultItem.copy();
	}

	@Override
	public ResourceLocation getId() {
		return this.name;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public IRecipeType<?> getType() {
		return CRUSHING;
	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
		implements IRecipeSerializer<HammerRecipe>
	{

		@Override
		public HammerRecipe read(ResourceLocation recipeId, JsonObject json) {
			HighTechWolvesTweaks.LOGGER.info("Reading recipe {}", recipeId);
			Ingredient input = CraftingHelper.getIngredient(JSONUtils.getJsonObject(json, "ingredient"));
			ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);
			HighTechWolvesTweaks.LOGGER.info("Recipe {} outputs {} from {}", recipeId, output, input.serialize().toString());
			return new HammerRecipe(recipeId, input, output);
		}

		@Nullable
		@Override
		public HammerRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
			Ingredient input = Ingredient.read(buffer);
			ItemStack output = buffer.readItemStack();

			return new HammerRecipe(recipeId, input, output);
		}

		@Override
		public void write(PacketBuffer buffer, HammerRecipe recipe) {
			//buffer.writeString(recipe.getGroup());
			recipe.sourceIngredient.write(buffer);
			buffer.writeItemStack(recipe.resultItem);
		}
	}
}
