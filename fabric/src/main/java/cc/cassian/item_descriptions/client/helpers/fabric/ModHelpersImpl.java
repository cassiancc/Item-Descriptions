package cc.cassian.item_descriptions.client.helpers.fabric;

import cc.cassian.item_descriptions.client.ModClient;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.nio.file.Path;

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

    //? if >1.21 {
    public static boolean hasComponent(ItemStack stack, ComponentType<?> type) {
        return stack.getComponents().contains(type);
    }
    //?}
}
