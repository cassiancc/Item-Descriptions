package cc.cassian.item_descriptions.client.helpers.neoforge;

import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.LoadingModList;

import java.io.File;
import java.nio.file.Path;

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

    public static boolean hasComponent(ItemStack stack, ComponentType<?> type) {
        //? if >=1.21.5 {
        return stack.getComponents().has(type);
        //?} else {
        /*return stack.getComponents().contains(type);
        *///?}
    }
}
