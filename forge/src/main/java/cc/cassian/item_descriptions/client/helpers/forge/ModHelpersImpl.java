package cc.cassian.item_descriptions.client.helpers.forge;


import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.LoadingModList;

public class ModHelpersImpl {
    public static boolean isLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }

    public static boolean isLoadingLoaded(String mod) {
        return LoadingModList.get().getModFileById(mod) != null;
    }

}
