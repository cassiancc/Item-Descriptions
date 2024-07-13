package cc.cassian.item_descriptions.client.config.neoforge;

import net.neoforged.fml.loading.FMLLoader;

import java.nio.file.Path;

public class ModConfigImpl {
    public static Path configPath() {
        return Path.of(FMLLoader.getGamePath() + "/config").resolve("item-descriptions.json");
    }
}
