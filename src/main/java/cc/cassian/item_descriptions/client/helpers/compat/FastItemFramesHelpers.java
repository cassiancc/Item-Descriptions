package cc.cassian.item_descriptions.client.helpers.compat;

import fuzs.fastitemframes.common.world.level.block.entity.ItemFrameBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

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
