package limonblaze.example.modifier;

import com.google.gson.JsonObject;
import limonblaze.example.ExampleMod;
import limonblaze.lootextra.loot.modifier.base.LootModifier;
import limonblaze.lootextra.loot.modifier.base.LootModifierType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LootToXpModifier extends LootModifier {
    private final Enchantment enchantment;
    private final int baseXp;
    private final int levelXp;

    public LootToXpModifier(Identifier id, Enchantment enchantment, int baseXp, int levelXp, List<LootCondition> conditions) {
        super(id, conditions);
        this.enchantment = enchantment;
        this.baseXp = baseXp;
        this.levelXp = levelXp;
    }

    public LootModifierType<?> getType() {
        return ExampleMod.LOOT_TO_XP;
    }

    public boolean isValid() {
        return this.enchantment != null;
    }

    public List<ItemStack> modify(List<ItemStack> lootIn, LootContext context) {
        Entity entity = context.get(LootContextParameters.KILLER_ENTITY);
        Vec3d pos = context.get(LootContextParameters.ORIGIN);
        if(entity instanceof LivingEntity && pos != null) {
            LivingEntity killer = (LivingEntity) entity;
            int level = EnchantmentHelper.getEquipmentLevel(this.enchantment, killer);
            int xp = this.baseXp + level * this.levelXp;
            if(xp > 0) {
                killer.world.spawnEntity(new ExperienceOrbEntity(killer.world, pos.x, pos.y, pos.z, xp));
                return new ArrayList<>();
            }
        }
        return lootIn;
    }

    public static LootToXpModifier fromJson(Identifier id, JsonObject json, List<LootCondition> conditions) {
        Identifier enchantmentId = Identifier.tryParse(JsonHelper.getString(json, "enchantment"));
        Enchantment enchantment = Registry.ENCHANTMENT.get(enchantmentId);
        if(enchantment == null) {
            ExampleMod.LOGGER.error("Invalid enchantment {} found in LootToXpModifier {}", enchantmentId, id);
        }
        int baseXp = JsonHelper.getInt(json, "base_xp");
        int levelXp = JsonHelper.getInt(json, "level_xp");
        return new LootToXpModifier(id, enchantment, baseXp, levelXp, conditions);
    }

}
