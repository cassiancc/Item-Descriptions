package cc.cassian.item_descriptions.client.jade;

import cc.cassian.item_descriptions.client.ModClient;
import cc.cassian.item_descriptions.client.helpers.TagHelpers;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public enum JadeEntityDescriptions implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip lines, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        //Check if entity descriptions are enabled in mod config.
        if (showEntityDescriptions()) {
            List<Text> tooltip;
            if (ModClient.CONFIG.developerOptions.showAllPotentialKeys.value()) {
                tooltip = TagHelpers.findAllPotentialKeys(entityAccessor.getEntity());
            }
            else {
                tooltip = createEntityDescription(entityAccessor.getEntity());
            }
            for (Text text : tooltip) {
                lines.add(text);
            }
        }
    }

    @Override
    public Identifier getUid() {
        return ModClient.ENTITY_DESCRIPTIONS;
    }

}
