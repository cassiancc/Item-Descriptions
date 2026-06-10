package cc.cassian.item_descriptions.client.descriptions;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.TagHelpers;
import cc.cassian.item_descriptions.client.helpers.compat.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BlockDescriptions {
    /**
     * Create a block's lore key based off data from WAILA-based Block Accessors like Jade/WTHIT/HYWLA.
     */
    public static DescriptionKey createBlockDescription(Block block, Level world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        //Convert block translation key to lore translation key.
        DescriptionKey loreKey = findLoreKey(block);
        //? if fabric {
        /*if (ModHelpers.isLoaded("polymer-bundled"))
            if (pos != null && PolymerHelpers.isPolymerBlock(pos)) {
                loreKey = new DescriptionKey(PolymerHelpers.findPolymerBlockResourceLocation(pos));
            }
        *///?}
        //Custom handling of Player Heads so custom profiles give custom descriptions.
        if (blockEntity instanceof SkullBlockEntity) {
            DescriptionKey profileKey = getProfile(blockEntity, loreKey);
            //Only show custom descriptions if a translation is present.
            if (profileKey.hasTranslation()) {
                return profileKey;
            }
        }
        if (ModHelpers.isLoaded("fastitemframes")) {
            if (FastItemFramesHelpers.isFastItemFrame(blockEntity)) {
                var contents = FastItemFramesHelpers.getFastItemFrameContents(blockEntity);
                if (contents != null)
                    return ItemDescriptions.findLoreKey(contents);
            }
        }
        //Check if translation exists. If not, see if an item exists for it - e.g. seeds.
        if (!loreKey.hasTranslation()) {
            if (pos == null) return ItemDescriptions.findLoreKey(block.asItem().getDefaultInstance());
            return ItemDescriptions.findLoreKey(state.getBlock().getCloneItemStack(world, pos, state));
        }
        return loreKey;
    }

    /**
     * Find a profile name in a Player Head block.
     */
    public static DescriptionKey getProfile(BlockEntity blockEntity, DescriptionKey loreKey) {
        String optionalProfileName;
        try {
            optionalProfileName = Objects.requireNonNull(((SkullBlockEntity) blockEntity).getOwnerProfile()).name().orElse("");
        } catch (NullPointerException nullPointerException) {
            return loreKey;
        }
        loreKey.setSuffix("profile." + optionalProfileName);
        return loreKey;
    }

    /**
     * Check if block descriptions should be shown based off configuration.
     */
    public static boolean showBlockDescriptions() {
        return ModClient.CONFIG.blockDescriptions.enable.value() && (ModHelpers.tooltipKeyPressed() || ModClient.CONFIG.blockDescriptions.showAlways.value());
    }

    /**
     * Shorthand to check a block's lore key.
     */
    public static DescriptionKey findLoreKey(Block block) {
        DescriptionKey key = getDescriptionKey(block);
        return DescriptionKey.checkLoreKey(key.hasTranslation() ? key : TagHelpers.checkGenericTagList(block));
    }

    /**
     * Shorthand to check a blockstate's lore key.
     */
    public static DescriptionKey findLoreKey(BlockState state) {
        DescriptionKey key = getDescriptionKey(state);
        return DescriptionKey.checkLoreKey(key.hasTranslation() ? key : TagHelpers.checkGenericTagList(state));
    }

    public static @NotNull DescriptionKey getDescriptionKey(BlockState blockState) {
        return getDescriptionKey(blockState.getBlock());
    }

    public static @NotNull DescriptionKey getDescriptionKey(Block block) {
        return DescriptionKey.ofTranslationKey(block.getDescriptionId());
    }
}
