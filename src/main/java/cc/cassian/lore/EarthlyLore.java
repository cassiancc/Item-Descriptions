package cc.cassian.lore;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class EarthlyLore implements ModInitializer {
    @Override
    public void onInitialize() {
        Items.IRON_BLOCK.appendTooltip(new ItemStack(Items.IRON_BLOCK), null, List.of(Text.literal("Test")), TooltipContext.BASIC);
    }
}
