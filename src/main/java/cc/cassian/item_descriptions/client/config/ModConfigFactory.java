package cc.cassian.item_descriptions.client.config;


import cc.cassian.item_descriptions.client.config.ClothConfigFactory;
import cc.cassian.item_descriptions.client.config.YetAnotherConfigFactory;
//? if fabric
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
//? if neoforge {
/*import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
*///?}
//? if forge
/*import net.minecraftforge.fml.ModContainer;*/
import org.jetbrains.annotations.NotNull;

public class ModConfigFactory
        //? if neoforge
        /*implements IConfigScreenFactory*/
        //? if fabric
        implements ConfigScreenFactory<Screen>
{

    private final String loadedMod;

    public ModConfigFactory(String s) {
        this.loadedMod = s;
    }

    //? if fabric
    public Screen create(Screen parent) {
    //? if neoforge
    /*public @NotNull Screen createScreen(@NotNull ModContainer modContainer, @NotNull Screen parent) {*/
    //? if forge
    /*public static @NotNull Screen createScreen(Minecraft modContainer, @NotNull Screen parent, String loadedMod) {*/
        if (loadedMod.equals("cloth-config")) {
            return ClothConfigFactory.create(parent);
        } else if (loadedMod.equals("yacl")) {
            return YetAnotherConfigFactory.create(parent);
        }
        return parent;
    }
}