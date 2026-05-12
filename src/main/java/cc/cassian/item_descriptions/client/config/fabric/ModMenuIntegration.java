package cc.cassian.item_descriptions.client.config.fabric;

//? fabric {
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.Platform;
import cc.cassian.item_descriptions.client.config.ModConfigFactory;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        //Display Cloth Config/YACL screen if mod present, else error.
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            return new ModConfigFactory("yacl");
        } else if (Platform.INSTANCE.isLoaded("cloth-config") && !ModClient.CONFIG.developerOptions.configScreen.value().equals("yacl")) {
            return new ModConfigFactory("cloth-config");
        } else {
            ModClient.LOGGER.warn("Item Descriptions requires Cloth Config or YACL for an ingame config!");
            return parent -> null;
        }
    }
}
//?}