package cc.cassian.item_descriptions.client;

//? fabric {
/*import cc.cassian.item_descriptions.client.fabric.FabricPlatformImpl;

*///?}
import net.minecraft.world.item.ItemStack;
import java.io.File;
import java.nio.file.Path;
//? neoforge {
import cc.cassian.item_descriptions.client.neoforge.NeoforgePlatformImpl;
//?}

public interface Platform {

    //? fabric {
    /*Platform INSTANCE = new FabricPlatformImpl();
    *///?}
    //? neoforge {
    Platform INSTANCE = new NeoforgePlatformImpl();
    //?}

    boolean isLoaded(String modid);
    boolean isLoadingLoaded(String mod);
    String loader();
    String getModName(ItemStack stack);
    File getMissingTranslationsPath();
    Path configPath();
}
