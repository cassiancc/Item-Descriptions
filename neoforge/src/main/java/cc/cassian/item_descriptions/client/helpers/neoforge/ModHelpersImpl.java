package cc.cassian.item_descriptions.client.helpers.neoforge;

import net.neoforged.fml.ModList;

public class ModHelpersImpl {
    public static boolean clothConfigInstalled() {
        return ModList.get().isLoaded("cloth_config");
    }
    public static boolean tooltipFixInstalled() {
        return false;
    }
}
