package com.mcmoddev.htwtweaks.misc;

import com.google.gson.JsonObject;
import com.mcmoddev.htwtweaks.HighTechWolvesTweaks;
import com.mcmoddev.htwtweaks.data.HammerRecipe;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

public class HammerLootCondition extends LootModifier {
	public HammerLootCondition(ILootCondition[] conditions) {
		super(conditions);
		HighTechWolvesTweaks.LOGGER.info("HammerLootCondition Constructed");
	}

	@Nonnull
	@Override
	public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		HighTechWolvesTweaks.LOGGER.info("HammerLootCondition.doApply()");
		List<ItemStack> ret = new LinkedList<>();
		ItemStack ctxTool = context.get(LootParameters.TOOL);
		generatedLoot.forEach((stack) -> ret.add(crack(stack, context)));
		return ret;
	}

	private final ItemStack crack(ItemStack baseLoot, LootContext context) {
		HighTechWolvesTweaks.LOGGER.info("HammerLootCondition.crack({}, context)", baseLoot.toString());
		return context.getWorld().getRecipeManager().getRecipe(HammerRecipe.CRUSHING, new Inventory(baseLoot), context.getWorld())
			.map(HammerRecipe::getRecipeOutput).filter(stack -> !stack.isEmpty()).orElse(baseLoot);
	}

	public static class Serializer extends GlobalLootModifierSerializer<HammerLootCondition> {
	public Serializer() {
		super();
		HighTechWolvesTweaks.LOGGER.info("HammerLootCondition.Serializer() constructor called");
	}
		@Override
		public HammerLootCondition read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
			HighTechWolvesTweaks.LOGGER.info("HammerLootCondition.Serializer.read({}, {}, {})", name.toString(), object.toString(), conditionsIn);
			return new HammerLootCondition(conditionsIn);
		}

		@Override
		public JsonObject write(HammerLootCondition hlc) {
			return makeConditions(hlc.conditions);
		}
	}
}
