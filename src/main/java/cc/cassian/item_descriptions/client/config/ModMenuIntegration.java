package cc.cassian.item_descriptions.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import static cc.cassian.item_descriptions.ModHelpers.clothConfigInstalled;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (!clothConfigInstalled()) return parent -> null;
        return new ModConfigFactory();
    }
}