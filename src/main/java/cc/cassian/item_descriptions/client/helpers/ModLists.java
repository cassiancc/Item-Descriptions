package cc.cassian.item_descriptions.client.helpers;

//? if >1.20 {

import net.minecraft.registry.Registries;
//?} else {
/*import net.minecraft.util.registry.Registry;
 *///?}
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import cc.cassian.item_descriptions.client.config.ModConfig;


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
        for (String disabledItem : ModConfig.get().developer_items_with_tooltips_to_hide) {
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