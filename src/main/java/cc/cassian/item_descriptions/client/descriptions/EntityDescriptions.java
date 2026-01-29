package cc.cassian.item_descriptions.client.descriptions;

import cc.cassian.item_descriptions.client.DescriptionKey;
import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.helpers.ModHelpers;
import cc.cassian.item_descriptions.client.helpers.TagHelpers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
//? if >1.21.10 || fabric {
import net.minecraft.world.entity.decoration.painting.Painting;
 //?} else {
/*import net.minecraft.world.entity.decoration.Painting;
*///?}
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EntityDescriptions {
    /**
     * Create an entity's lore key based off its entity data.
     */
    public static List<Component> createEntityDescription(Entity entity) {
        //Create and add tooltip.
        Component name = entity.getName();
        if (entity instanceof ItemFrame itemFrameEntity && !itemFrameEntity.getItem().isEmpty()) {
            return ModHelpers.createTooltip(name, ItemDescriptions.findLoreKey(itemFrameEntity.getItem()));
        } else if (entity instanceof Painting painting && painting.getVariant().isBound()) {
            var loreKey = new DescriptionKey("lore", "minecraft", "painting", ModHelpers.toTranslationKey(painting.getVariant().getRegisteredName()));
            if (loreKey.hasTranslation())
                return ModHelpers.createTooltip(name, loreKey);
        }
        return ModHelpers.createTooltip(name, findLoreKey(entity));
    }

    /**
     * Shorthand to check an entity's lore key.
     */
    public static DescriptionKey findLoreKey(Entity entity) {
        return findLoreKey(entity.getType());
    }

    /**
     * Shorthand to check an entity's lore key.
     */
    public static DescriptionKey findLoreKey(EntityType<?> entity) {
        DescriptionKey key = getDescriptionKey(entity);
        return DescriptionKey.checkLoreKey(key.hasTranslation() ? key : TagHelpers.checkGenericTagList(entity));
    }

    public static @NotNull DescriptionKey getDescriptionKey(EntityType<?> entityType) {
        return DescriptionKey.ofTranslationKey(entityType.getDescriptionId());
    }

    public static @NotNull DescriptionKey getDescriptionKey(Entity entity) {
        return DescriptionKey.ofTranslationKey(getEntityTranslationKey(entity));
    }

    /**
     * Find an entity's translation key
     */
    public static String getEntityTranslationKey(Entity entity) {
        //Allow for custom player descriptions
        if (entity instanceof Player) {
            String playerKey = "entity.minecraft.player.%s".formatted(entity.getName().tryCollapseToString());
            //Check if a custom player description exists.
            if (ModHelpers.hasTranslation(playerKey)) return playerKey;
                //If not, use the default one.
            else return "entity.minecraft.player";
        } else {
            return entity.getType().getDescriptionId();
        }
    }

    /**
     * Check if entity descriptions should be shown based off configuration.
     */
    public static boolean showEntityDescriptions() {
        return ModClient.CONFIG.entityDescriptions.enable.value() && (ModHelpers.tooltipKeyPressed() || ModClient.CONFIG.entityDescriptions.showAlways.value());
    }
}
