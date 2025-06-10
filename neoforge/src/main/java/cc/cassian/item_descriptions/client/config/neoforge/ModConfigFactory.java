package cc.cassian.item_descriptions.client.config.neoforge;


import cc.cassian.item_descriptions.client.config.ClothConfigFactory;
import cc.cassian.item_descriptions.client.config.YetAnotherConfigFactory;
import net.minecraft.client.gui.screen.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

public class ModConfigFactory implements IConfigScreenFactory {

    private final String loadedMod;

    public ModConfigFactory(String s) {
        this.loadedMod = s;
    }

    @Override
    public @NotNull Screen createScreen(@NotNull ModContainer modContainer, @NotNull Screen parent) {
        if (loadedMod.equals("cloth-config")) {
            return ClothConfigFactory.create(parent);
        } else if (loadedMod.equals("yacl")) {
            return YetAnotherConfigFactory.create(parent);
        }
        return parent;
    }
}