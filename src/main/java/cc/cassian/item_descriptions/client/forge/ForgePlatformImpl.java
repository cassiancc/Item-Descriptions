package cc.cassian.item_descriptions.client.forge;

//? forge {
/*import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.Platform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.LoadingModList;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class ForgePlatformImpl implements Platform {

    public boolean isLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }

    public boolean isLoadingLoaded(String mod) {
        return LoadingModList.get().getModFileById(mod) != null;
    }

    @Override
    public String loader() {
        return "neoforge";
    }

    public File getMissingTranslationsPath() {
        Path savePath = FMLPaths.getOrCreateGameRelativePath(Path.of("data").resolve(ModClient.MOD_ID).resolve("missing"));
        return savePath.toFile();
    }

    @Override
    public Path configPath() {
        return FMLPaths.CONFIGDIR.get();
    }

    public String getModName(ItemStack stack) {
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
*///?}