package cc.cassian.item_descriptions.client.helpers.neoforge;

import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.LoadingModList;

public class ModHelpersImpl {
    public static boolean clothConfigInstalled() {
        return ModList.get().isLoaded("cloth_config");
    }
    public static boolean isLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }
    public static boolean isLoadingLoaded(String mod) {
        return LoadingModList.get().getModFileById(mod) != null;
    }

}
