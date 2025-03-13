package cc.cassian.item_descriptions.client.helpers.compat;

import fuzs.fastitemframes.world.level.block.entity.ItemFrameBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;

public class FastItemFramesHelpers {
    public static boolean isFastItemFrame(BlockEntity block) {
        return block instanceof ItemFrameBlockEntity;
    }

    public static ItemStack getFastItemFrameContents(BlockEntity blockEntity) {
        if (blockEntity instanceof ItemFrameBlockEntity itemFrameBlockEntity) {
            return itemFrameBlockEntity.getItem();
        }
        return null;
    }
}
