package cc.cassian.item_descriptions.client.config.forge;


import cc.cassian.item_descriptions.client.config.ClothConfigFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.fml.ModContainer;
import org.jetbrains.annotations.NotNull;

public class ModConfigFactory {

    public static @NotNull Screen createScreen(@NotNull MinecraftClient arg, @NotNull Screen parent) {
        return ClothConfigFactory.create(parent);
    }
}