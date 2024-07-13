package cc.cassian.item_descriptions.client.helpers.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class ModHelpersImpl {
    public static boolean clothConfigInstalled() {
        return FabricLoader.getInstance().isModLoaded("cloth-config");
    }
    public static boolean tooltipFixInstalled() {
        return FabricLoader.getInstance().isModLoaded("tooltipfix");
    }
}
