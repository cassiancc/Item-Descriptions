package cc.cassian.item_descriptions.client.fabric;

//? fabric {
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.Platform;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.resources.language.I18n;
//? if >1.20 {
import net.minecraft.core.registries.BuiltInRegistries;
//?} else {
/*import net.minecraft.core.Registry;
*///?}
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class FabricPlatformImpl implements Platform {

    @Override
    public boolean isLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    @Override
    public String loader() {
        return "fabric";
    }

    public boolean isLoadingLoaded(String mod) {
        return isLoaded(mod);
    }

    public File getMissingTranslationsPath() {
        Path savePath = FabricLoader.getInstance().getGameDir().resolve("data").resolve(ModClient.MOD_ID).resolve("missing");
        return savePath.toFile();
    }

    @Override
    public Path configPath() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public String getModName(ItemStack stack) {
        String namespace = stack.getCreatorNamespace();
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
//?}