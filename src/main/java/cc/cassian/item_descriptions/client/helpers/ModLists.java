package cc.cassian.item_descriptions.client.helpers;

//? if >1.20 {

import cc.cassian.item_descriptions.client.ModClient;
import net.minecraft.registry.Registries;
//?} else {
/*import net.minecraft.util.registry.Registry;
 *///?}
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;


import java.util.ArrayList;
import java.util.Optional;

public class ModLists {
    public static ArrayList<Item> hidden_items = new ArrayList<>();

    public static void loadLists() {
        //? if >1.20 {
        var registry = Registries.ITEM;
        //?} else {
        /*var registry = Registry.ITEM;
         *///?}
        hidden_items = new ArrayList<>();
        for (String disabledItem : ModClient.CONFIG.developerOptions.itemsWithTooltipsToHide.value()) {
            Optional<Item> item = registry.
            //? if >1.21.2 {
            getOptionalValue
            //?} else {
            /*getOrEmpty
            *///?}
            (Identifier.tryParse(disabledItem));
            item.ifPresent(value -> hidden_items.add(value));
        }
    }
}