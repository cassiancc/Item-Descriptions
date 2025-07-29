package cc.cassian.item_descriptions.client.config.forge;


import cc.cassian.item_descriptions.client.config.ClothConfigFactory;
import cc.cassian.item_descriptions.client.config.YetAnotherConfigFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class ModConfigFactory {

    public static @NotNull Screen createScreen(Minecraft arg, @NotNull Screen parent, String loadedMod) {
        if (loadedMod.equals("cloth-config")) {
            return ClothConfigFactory.create(parent);
        } else if (loadedMod.equals("yacl")) {
            return YetAnotherConfigFactory.create(parent);
        }
        return parent;
    }
}