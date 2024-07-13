package cc.cassian.item_descriptions.client.jade.fabric;

import cc.cassian.item_descriptions.client.TooltipClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public enum JadeEntityDescriptions implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip lines, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        //Check if block descriptions are enabled in mod config.
        if (showEntityDescriptions()) {
            //Create and add tooltip.
            List<Text> tooltip = createTooltip(getEntityAccessorLoreKey(entityAccessor.getEntity()), true);
            for (Text text : tooltip) {
                lines.add(text);
            }
        }
    }

    @Override
    public Identifier getUid() {
        return TooltipClient.ENTITY_DESCRIPTIONS;
    }
}
