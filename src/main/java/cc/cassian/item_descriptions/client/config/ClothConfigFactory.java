package cc.cassian.item_descriptions.client.config;


import cc.cassian.item_descriptions.client.ModClient;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class ClothConfigFactory {

    private static final ModConfig DEFAULT_VALUES = new ModConfig();

    private static ConfigCategory createCategory(String section, ConfigBuilder builder) {
        if (section == null) {
            section = "";
        } else {
            section += "_";
        }
        return builder.getOrCreateCategory(Text.translatable("config.item-descriptions.%stitle".formatted(section)));
    }

    public static Screen create(Screen parent) {
        final var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("modmenu.nameTranslation.item-descriptions"));

        addEntries(ModConfig.class.getFields(), ModClient.CONFIG, DEFAULT_VALUES, null, builder);
        addEntries(ModConfig.Style.class.getFields(), ModClient.CONFIG.style, DEFAULT_VALUES.style, "style", builder);
        addEntries(ModConfig.EnchantmentDescriptions.class.getFields(), ModClient.CONFIG.enchantmentDescriptions, DEFAULT_VALUES.enchantmentDescriptions, "enchantment_descriptions", builder);
        addEntries(ModConfig.EffectDescriptions.class.getFields(), ModClient.CONFIG.effectDescriptions, DEFAULT_VALUES.effectDescriptions, "effect_descriptions", builder);
        addEntries(ModConfig.BlockDescriptions.class.getFields(), ModClient.CONFIG.blockDescriptions, DEFAULT_VALUES.blockDescriptions, "block_descriptions" , builder);
        addEntries(ModConfig.EntityDescriptions.class.getFields(), ModClient.CONFIG.entityDescriptions, DEFAULT_VALUES.entityDescriptions, "entity_descriptions", builder);
        addEntries(ModConfig.Hint.class.getFields(), ModClient.CONFIG.hint, DEFAULT_VALUES.hint, "hint", builder);
        addEntries(ModConfig.Keybinds.class.getFields(), ModClient.CONFIG.keybinds, DEFAULT_VALUES.keybinds, "keybinds", builder);
        addEntries(ModConfig.DeveloperOptions.class.getFields(), ModClient.CONFIG.developer, DEFAULT_VALUES.developer, "developer_options", builder);

        builder.setSavingRunnable(ModClient.CONFIG::save);
        return builder.build();
    }

    private static void addEntries(Field[] fields, Object config, Object defaultValues, String categoryName, ConfigBuilder builder) {
        var entryBuilder = builder.entryBuilder();
        var category = createCategory(categoryName, builder);
        for (var field : fields) {

            if (field.getType() == boolean.class) {
                category.addEntry(entryBuilder.startBooleanToggle(fieldName(field, categoryName), fieldGet(config, field))
                        .setSaveConsumer(fieldSetter(config, field))
                        .setTooltip(fieldTooltip(field, categoryName))
                        .setDefaultValue((boolean) fieldGet(defaultValues, field)).build());

            }
            else if (field.getType() == String.class) {
                category.addEntry(entryBuilder.startStrField(fieldName(field, categoryName), fieldGet(config, field))
                        .setSaveConsumer(fieldSetter(config, field))
                        .setTooltip(fieldTooltip(field, categoryName))
                        .setDefaultValue((String) fieldGet(defaultValues, field)).build());
            }
            else if (field.getType() == int.class) {
                category.addEntry(entryBuilder.startIntField(fieldName(field, categoryName), fieldGet(config, field))
                        .setSaveConsumer(fieldSetter(config, field))
                        .setTooltip(fieldTooltip(field, categoryName))
                        .setDefaultValue((int) fieldGet(defaultValues, field)).build());
            }
            else if (field.getType() == List.class) {
                category.addEntry(entryBuilder.startStrList(fieldName(field, categoryName), fieldGet(config, field))
                        .setSaveConsumer(fieldSetter(config, field))
                        .setTooltip(fieldTooltip(field, categoryName))
                        .setDefaultValue((List<String>) fieldGet(defaultValues, field)).build());
            }
        }
    }
}