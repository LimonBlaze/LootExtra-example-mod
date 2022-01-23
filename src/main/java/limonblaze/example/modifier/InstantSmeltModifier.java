package limonblaze.example.modifier;

import com.google.gson.JsonObject;
import limonblaze.example.ExampleMod;
import limonblaze.lootextra.loot.modifier.base.LootModifier;
import limonblaze.lootextra.loot.modifier.base.LootModifierType;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InstantSmeltModifier extends LootModifier {

    public InstantSmeltModifier(Identifier id, List<LootCondition> conditions) {
        super(id, conditions);
    }

    public LootModifierType<?> getType() {
        return ExampleMod.INSTANT_SMELT;
    }

    public boolean isValid() {
        return true;
    }

    public List<ItemStack> modify(List<ItemStack> lootIn, LootContext context) {
        World world = context.getWorld();
        return lootIn.stream().map(stack -> this.process(stack, world)).collect(Collectors.toList());
    }

    public ItemStack process(ItemStack stack, World world) {
        if (!stack.isEmpty()) {
            Optional<SmeltingRecipe> optional = world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, new SimpleInventory(stack), world);
            if (optional.isPresent()) {
                ItemStack itemStack = optional.get().getOutput();
                if (!itemStack.isEmpty()) {
                    ItemStack itemStack2 = itemStack.copy();
                    itemStack2.setCount(stack.getCount());
                    return itemStack2;
                }
            }
        }
        return stack;
    }

    public static InstantSmeltModifier fromJson(Identifier id, JsonObject json, List<LootCondition> conditions) {
        return new InstantSmeltModifier(id, conditions);
    }

}
