package cc.cassian.item_descriptions.client.helpers.compat;

//? if >1.20 && fabric {
import com.brokenkeyboard.usefulspyglass.api.event.BlockTooltipCallback;
import com.brokenkeyboard.usefulspyglass.api.event.LivingTooltipCallback;
//?}
import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.Platform;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
//? if >1.20 && (neoforge || forge) {
/*import com.brokenkeyboard.usefulspyglass.api.event.BlockTooltipEvent;
import com.brokenkeyboard.usefulspyglass.api.event.LivingTooltipEvent;
*///?}
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class UsefulSpyglassHelpers {
    public static void register() {
        //? if >1.20 && fabric {
        BlockTooltipCallback.EVENT.register(UsefulSpyglassHelpers::addBlockTooltip);
        LivingTooltipCallback.EVENT.register(UsefulSpyglassHelpers::addLivingTooltip);
        //?}
    }

    //? if (neoforge || forge) && >1.20 {
    /*public static void registerBlockEvent(BlockTooltipEvent event) {
        addBlockTooltip(event.getBlockState(), event.getBlockPos(), event.getTooltipList());
    }

    public static void registerEntityEvent(LivingTooltipEvent event) {
        addLivingTooltip(event.getEntity(), event.getTooltipList());
    }
    *///?}

    public static void addBlockTooltip(BlockState blockState, BlockPos blockPos, List<ClientTooltipComponent> list) {
        if (ModHelpers.showBlockDescriptions()) {
            var level = Minecraft.getInstance().level;
            BlockEntity blockEntity =null;
            if (level != null)
                blockEntity = level.getBlockEntity(blockPos);
            DescriptionKey blockDescription = ModHelpers.createBlockDescription(blockState.getBlock(), level, blockPos, blockState, blockEntity);
            ModHelpers.createTooltip(blockState.getBlock().getName(), blockDescription).forEach((component -> {
                list.add(ClientTooltipComponent.create(component.getVisualOrderText()));
            }));
        }
    }

    public static void addLivingTooltip(Entity entity, List<ClientTooltipComponent> list) {
        if (ModHelpers.showEntityDescriptions()) {
            ModHelpers.createEntityDescription(entity).forEach((component -> {
                list.add(ClientTooltipComponent.create(component.getVisualOrderText()));
            }));
        }
    }
}
