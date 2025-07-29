package cc.cassian.item_descriptions.client.config.forge;


import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

import static cc.cassian.item_descriptions.client.ModClient.MOD_ID_NEO;

public class ModConfigImpl {
    public static Path configPath() {
        return FMLPaths.CONFIGDIR.get();
    }
}
