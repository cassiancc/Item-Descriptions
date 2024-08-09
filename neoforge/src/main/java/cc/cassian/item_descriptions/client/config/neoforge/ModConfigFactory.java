package cc.cassian.item_descriptions.client.config.neoforge;


import cc.cassian.item_descriptions.client.config.ClothConfigFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

public class ModConfigFactory implements IConfigScreenFactory {

    @Override
    public @NotNull Screen createScreen(@NotNull MinecraftClient modContainer, @NotNull Screen parent) {
        return ClothConfigFactory.create(parent);
    }
}