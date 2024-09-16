package cc.cassian.item_descriptions.client.config.fabric;


import cc.cassian.item_descriptions.client.config.ClothConfigFactory;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import net.minecraft.client.gui.screen.Screen;

public class ModConfigFactory implements ConfigScreenFactory<Screen> {

    @Override
    public Screen create(Screen parent) {
        return ClothConfigFactory.create(parent);
    }
}