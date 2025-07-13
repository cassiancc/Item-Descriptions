package cc.cassian.item_descriptions.client.helpers.neoforge;

import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    public static boolean hasComponent(ItemStack stack, ComponentType<?> type) {
        //? if >=1.21.5 {
        return stack.getComponents().has(type);
        //?} else {
        /*return stack.getComponents().contains(type);
        *///?}
    }

    public static String getModName(ItemStack stack, String namespace) {
        String creatorModId;
        if (MinecraftClient.getInstance().world != null) {
            creatorModId = stack.getItem().getCreatorModId(MinecraftClient.getInstance().world.getRegistryManager(), stack);
            if (creatorModId != null) {
                namespace = creatorModId;
            }
        }
        Optional<? extends ModContainer> modContainer = ModList.get().getModContainerById(namespace);
        if (modContainer.isPresent()) {
            return modContainer.get().getModInfo().getDisplayName();
        } else {
            return WordUtils.capitalize(namespace);
        }
    }
}
