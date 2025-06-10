package cc.cassian.item_descriptions.client.config.fabric;

import cc.cassian.item_descriptions.client.ModClient;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        //Display Cloth Config/YACL screen if mod present, else error.
        if (FabricLoader.getInstance().isModLoaded("cloth-config") && !ModClient.CONFIG.developer.configScreen.equals("yacl")) {
            return new ModConfigFactory("cloth-config");
        } else if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return new ModConfigFactory("yacl");
        } else {
            ModClient.LOGGER.warn("User attempted to edit config, but no config API is not present!");
            return parent -> null;
        }
    }
}