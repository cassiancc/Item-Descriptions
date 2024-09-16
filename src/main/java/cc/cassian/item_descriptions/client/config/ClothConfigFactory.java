package cc.cassian.item_descriptions.client.config;


import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.lang.reflect.Field;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class ClothConfigFactory {

    private static final ModConfig DEFAULT_VALUES = new ModConfig();

    public static Screen create(Screen parent) {
        final ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(I18n.translate(("config.item-descriptions.title")));

        final ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        final ModConfig configInstance = ModConfig.get();
        final ConfigCategory generalCategory = builder.getOrCreateCategory(I18n.translate("config.item-descriptions.title"));
        final ConfigCategory styleCategory = builder.getOrCreateCategory(I18n.translate("config.item-descriptions.style_title"));
        final ConfigCategory keyBindsCategory = builder.getOrCreateCategory(I18n.translate("config.item-descriptions.keybinds_title"));
        final ConfigCategory pluginsCategory = builder.getOrCreateCategory(I18n.translate("config.item-descriptions.plugins_title"));
        final ConfigCategory developerCategory = builder.getOrCreateCategory(I18n.translate("config.item-descriptions.developer_options_title"));



        for (Field field : ModConfig.class.getFields()) {
            ConfigCategory category;
            if (field.getName().contains("keybind")) category = keyBindsCategory;
            else if (field.getName().toLowerCase().contains("block")) category = pluginsCategory;
            else if (field.getName().toLowerCase().contains("entity")) category = pluginsCategory;
            else if (field.getName().toLowerCase().contains("compat_")) category = pluginsCategory;
            else if (field.getName().toLowerCase().contains("developer")) category = developerCategory;
            else if (field.getName().toLowerCase().contains("style")) category = styleCategory;

            else category = generalCategory;
            if (field.getType() == boolean.class) {
                category.addEntry(entryBuilder.startBooleanToggle(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
//                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((boolean) fieldGet(DEFAULT_VALUES, field)).build());

            }
            else if (field.getType() == String.class) {
                category.addEntry(entryBuilder.startStrField(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
//                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((String) fieldGet(DEFAULT_VALUES, field)).build());
            }
            else if (field.getType() == int.class) {
                category.addEntry(entryBuilder.startIntField(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
//                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((int) fieldGet(DEFAULT_VALUES, field)).build());
            }
        }
        builder.setSavingRunnable(ModConfig::save);
        return builder.build();
    }
}