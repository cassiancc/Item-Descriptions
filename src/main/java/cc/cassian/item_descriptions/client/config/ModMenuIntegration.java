package cc.cassian.item_descriptions.client.config;

import cc.cassian.item_descriptions.client.TooltipClient;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

import static cc.cassian.item_descriptions.ModHelpers.clothConfigInstalled;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public String getModId() {
        return TooltipClient.MOD_ID;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        //Display Cloth Config screen if mod present, else error.
        if (clothConfigInstalled()) return new ModConfigFactory();
        else {
            TooltipClient.LOGGER.warn("User attempted to edit config, but Cloth Config is not present!");
            return parent -> null;
        }
    }
}