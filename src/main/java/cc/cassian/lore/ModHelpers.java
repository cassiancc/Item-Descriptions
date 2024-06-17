package cc.cassian.lore;

import net.minecraft.item.ItemStack;

public class ModHelpers {
    public static String findLoreKey(ItemStack stack) {
        if (stack.getTranslationKey().contains("block.")) {
            return stack.getTranslationKey().replaceFirst("block", "lore");
        }
        else if ((stack.getTranslationKey().contains("item."))) {
            return stack.getTranslationKey().replaceFirst("item", "lore");
        }
        else {
            return stack.getTranslationKey();
        }
    }
}
