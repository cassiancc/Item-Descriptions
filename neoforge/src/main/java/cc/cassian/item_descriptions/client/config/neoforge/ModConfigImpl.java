package cc.cassian.item_descriptions.client.config.neoforge;

import net.neoforged.fml.loading.FMLLoader;

import java.nio.file.Path;

import static cc.cassian.item_descriptions.client.ModClient.MOD_ID_NEO;

public class ModConfigImpl {
    public static Path configPath() {
        return Path.of(FMLLoader.getGamePath() + "/config").resolve(MOD_ID_NEO + ".json");
    }
}
