package cc.cassian.item_descriptions.client;

//? fabric || unobf {
import cc.cassian.item_descriptions.client.fabric.FabricPlatformImpl;

//?}
import net.minecraft.world.item.ItemStack;
import java.io.File;
import java.nio.file.Path;
//? neoforge {
/*import cc.cassian.item_descriptions.client.neoforge.NeoforgePlatformImpl;
*///?}
//? forge
/*import cc.cassian.item_descriptions.client.forge.ForgePlatformImpl;*/

public interface Platform {

    //? fabric || unobf {
    Platform INSTANCE = new FabricPlatformImpl();
    //?}
    //? neoforge {
    /*Platform INSTANCE = new NeoforgePlatformImpl();
    *///?}
    //? forge {
    /*Platform INSTANCE = new ForgePlatformImpl();
     *///?}


    boolean isLoaded(String modid);
    boolean isLoadingLoaded(String mod);
    String loader();
    String getModName(ItemStack stack);
    File getMissingTranslationsPath();
    Path configPath();
}
