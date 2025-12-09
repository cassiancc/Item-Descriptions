package cc.cassian.item_descriptions.client.helpers;

//? if >1.20 {


import net.minecraft.core.registries.BuiltInRegistries;
//?} else {

/*import net.minecraft.core.Registry;
 *///?}
import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Optional;

public class ModLists {
    public static ArrayList<Item> hidden_items = new ArrayList<>();

    public static void loadLists() {
        var registry = BuiltInRegistries.ITEM;
        hidden_items = new ArrayList<>();
        for (String disabledItem : ModClient.CONFIG.developerOptions.itemsWithTooltipsToHide.value()) {
            Optional<Item> item = registry.getOptional(ResourceLocation.tryParse(disabledItem));
            item.ifPresent(value -> hidden_items.add(value));
        }
    }
}