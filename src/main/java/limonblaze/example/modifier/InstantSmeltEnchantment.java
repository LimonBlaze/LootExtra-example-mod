package limonblaze.example.modifier;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class InstantSmeltEnchantment extends Enchantment {

    public InstantSmeltEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public int getMinPower(int level) {
        return 24;
    }

    public int getMaxPower(int level) {
        return 36;
    }

    public int getMaxLevel() {
        return 1;
    }

}
