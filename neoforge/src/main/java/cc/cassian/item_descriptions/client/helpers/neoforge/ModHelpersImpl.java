package cc.cassian.item_descriptions.client.helpers.neoforge;

import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.neoforge.common.CommonHooks;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class ModHelpersImpl {
    public static boolean clothConfigInstalled() {
        return ModList.get().isLoaded("cloth_config");
    }

    public static boolean isLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }

    public static boolean isLoadingLoaded(String mod) {
        return LoadingModList.get().getModFileById(mod) != null;
    }

    public static File getMissingTranslationsPath() {
        Path savePath = FMLPaths.getOrCreateGameRelativePath(Path.of("data").resolve(ModClient.MOD_ID).resolve("missing"));
        return savePath.toFile();
    }

    public static String getModName(ItemStack stack) {
        String namespace = "minecraft";
        if (Minecraft.getInstance().level != null) {
            namespace = stack.getItem().getCreatorModId(
                    //? if >=1.21.2 {
                     Minecraft.getInstance().level.registryAccess(),
                     //?}
                   stack);
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
