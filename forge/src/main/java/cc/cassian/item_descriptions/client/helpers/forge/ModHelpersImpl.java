package cc.cassian.item_descriptions.client.helpers.forge;


import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.LoadingModList;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Optional;

public class ModHelpersImpl {
    public static boolean isLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }

    public static boolean isLoadingLoaded(String mod) {
        return LoadingModList.get().getModFileById(mod) != null;
    }

    public static String getModName(ItemStack stack) {
        String namespace = "minecraft";
        if (Minecraft.getInstance().level != null) {
            namespace = stack.getItem().getCreatorModId(stack);
        }
        String key = "modmenu.nameTranslation."+namespace;
        Optional<? extends ModContainer> modContainer = ModList.get().getModContainerById(namespace);
        if (modContainer.isPresent()) {
            return modContainer.get().getModInfo().getDisplayName();
        } else if (I18n.exists(key)) {
            return I18n.get(key);
        } else {
            return WordUtils.capitalize(namespace);
        }
    }
}
