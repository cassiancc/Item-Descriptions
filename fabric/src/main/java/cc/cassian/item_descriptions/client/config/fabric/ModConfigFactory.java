package cc.cassian.item_descriptions.client.config.fabric;


import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.config.ClothConfigFactory;
import cc.cassian.item_descriptions.client.config.YetAnotherConfigFactory;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.minecraft.client.gui.screens.Screen;

public class ModConfigFactory implements ConfigScreenFactory<Screen> {

    private final String loadedMod;

    public ModConfigFactory(String s) {
        this.loadedMod = s;
    }

    @Override
    public Screen create(Screen parent) {
        if (loadedMod.equals("cloth-config")) {
            return ClothConfigFactory.create(parent);
        } else if (loadedMod.equals("yacl")) {
            return YetAnotherConfigFactory.create(parent);
        }
        return parent;
    }
}