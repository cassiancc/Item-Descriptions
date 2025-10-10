package cc.cassian.item_descriptions.client.helpers.compat;

//? if >1.20
import fuzs.fastitemframes.world.level.block.entity.ItemFrameBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FastItemFramesHelpers {
    public static boolean isFastItemFrame(BlockEntity block) {
        //? if >1.20 {
        return block instanceof ItemFrameBlockEntity;
        //?} else {
        /*return false;
        *///?}
    }

    public static ItemStack getFastItemFrameContents(BlockEntity blockEntity) {
        //? if >1.20 {
        if (blockEntity instanceof ItemFrameBlockEntity itemFrameBlockEntity) {
            return itemFrameBlockEntity.getItem();
        }
        //?}
        return null;
    }
}
