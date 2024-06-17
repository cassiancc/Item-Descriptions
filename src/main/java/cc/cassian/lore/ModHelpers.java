package cc.cassian.lore;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModHelpers {
    public static String findLoreKey(ItemStack stack) {
        String translationKey = stack.getTranslationKey();
        String loreKey;
        if (translationKey.contains("block.")) {
            loreKey = translationKey.replaceFirst("block", "lore");
        }
        else if ((translationKey.contains("item."))) {
            loreKey = translationKey.replaceFirst("item", "lore");
        }
        else {
            loreKey = translationKey;
        }
        if (I18n.hasTranslation(loreKey)) {
           return loreKey;
        }
        else {
            if (loreKey.contains("planks")) {
                return "lore.generic.planks";
            }
            else {
                return "";
            }
        }
    }
}
