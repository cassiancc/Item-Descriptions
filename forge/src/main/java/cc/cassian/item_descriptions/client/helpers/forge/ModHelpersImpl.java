package cc.cassian.item_descriptions.client.helpers.forge;


import net.minecraft.client.Minecraft;
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

    public static String getModName(ItemStack stack, String namespace) {
        String creatorModId;
        creatorModId = stack.getItem().getCreatorModId(stack);
        if (creatorModId != null) {
            namespace = creatorModId;
        }
        Optional<? extends ModContainer> modContainer = ModList.get().getModContainerById(namespace);
        if (modContainer.isPresent()) {
            return modContainer.get().getModInfo().getDisplayName();
        } else {
            return WordUtils.capitalize(namespace);
        }
    }
}
