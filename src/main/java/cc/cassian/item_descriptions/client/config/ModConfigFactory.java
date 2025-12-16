package cc.cassian.item_descriptions.client.config;


import cc.cassian.item_descriptions.client.config.ClothConfigFactory;
import cc.cassian.item_descriptions.client.config.YetAnotherConfigFactory;
//? if fabric && <26
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
//? if neoforge {
/*import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
*///?}
import org.jetbrains.annotations.NotNull;

public class ModConfigFactory
        //? if neoforge
        /*implements IConfigScreenFactory*/
        //? if fabric && <26
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
        if (loadedMod.equals("cloth-config")) {
            return ClothConfigFactory.create(parent);
        } else if (loadedMod.equals("yacl")) {
            return YetAnotherConfigFactory.create(parent);
        }
        return parent;
    }
}