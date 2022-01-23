package limonblaze.example;

import limonblaze.example.modifier.*;
import limonblaze.lootextra.loot.modifier.base.LootModifierType;
import limonblaze.lootextra.registry.LootExtraRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleMod implements ModInitializer {
	public static String MODID = "loot_extra_example";
	public static Logger LOGGER = LogManager.getLogger(MODID);

	public static LootModifierType<InstantSmeltModifier> INSTANT_SMELT =
			new LootModifierType<>(InstantSmeltModifier::fromJson, 100);
	public static LootModifierType<LootToXpModifier> LOOT_TO_XP =
			new LootModifierType<>(LootToXpModifier::fromJson, 120);

	public void onInitialize() {
		Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, "flamming_bobber"), new FlammingBobberEnchantment());
		Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, "instant_smelt"), new InstantSmeltEnchantment());
		Registry.register(Registry.ENCHANTMENT, new Identifier(MODID, "soul_extraction"), new SoulExtractionEnchantment());
		Registry.register(LootExtraRegistry.LOOT_MODIFIER_TYPE, new Identifier(MODID, "instant_smelt"), INSTANT_SMELT);
		Registry.register(LootExtraRegistry.LOOT_MODIFIER_TYPE, new Identifier(MODID, "loot_to_xp"), LOOT_TO_XP);
	}
}
