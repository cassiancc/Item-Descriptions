package cc.cassian.item_descriptions.client.helpers.fabric;

import cc.cassian.item_descriptions.client.ModClient;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class ModHelpersImpl {

    public static boolean isLoaded(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod);
    }

    public static boolean isLoadingLoaded(String mod) {
        return isLoaded(mod);
    }

    public static File getMissingTranslationsPath() {
        Path savePath = FabricLoader.getInstance().getGameDir().resolve("data").resolve(ModClient.MOD_ID).resolve("missing");
        return savePath.toFile();
    }

    public static String getModName(ItemStack stack) {
        String namespace =
        //? if >=1.21.8 || =1.21.4 || =1.21.5 || =1.21.1 {
        stack.getCreatorNamespace();
        //?} else {
        /*BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();
        *///?}
        String key = "modmenu.nameTranslation."+namespace;
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(namespace);
        if (modContainer.isPresent()) {
            return modContainer.get().getMetadata().getName();
        } else if (I18n.exists(key)) {
            return I18n.get(key);
        } else {
            return WordUtils.capitalize(namespace);
        }
    }
}
