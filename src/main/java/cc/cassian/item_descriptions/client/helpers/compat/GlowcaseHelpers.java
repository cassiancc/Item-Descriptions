package cc.cassian.item_descriptions.client.helpers.compat;

import dev.hephaestus.glowcase.block.entity.ItemDisplayBlockEntity;
import fuzs.fastitemframes.world.level.block.entity.ItemFrameBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GlowcaseHelpers {
    public static boolean isItemDisplay(BlockEntity block) {
        return block instanceof ItemDisplayBlockEntity;
    }

    public static ItemStack getItemDisplayContents(BlockEntity blockEntity) {
        if (blockEntity instanceof ItemDisplayBlockEntity be) {
            return be.getDisplayedStack();
        }
        return null;
    }
}
