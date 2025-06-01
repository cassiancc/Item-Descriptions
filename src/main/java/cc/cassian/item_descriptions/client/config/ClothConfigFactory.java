package cc.cassian.item_descriptions.client.config;


import cc.cassian.item_descriptions.client.ModClient;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

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

        addEntries(ModConfig.class.getFields(), createCategory(null, builder), builder.entryBuilder());
        addEntries(ModConfig.Style.class.getFields(),createCategory("style", builder), builder.entryBuilder());
        addEntries(ModConfig.EnchantmentDescriptions.class.getFields(),createCategory("enchantment_descriptions", builder), builder.entryBuilder());
        addEntries(ModConfig.EffectDescriptions.class.getFields(),createCategory("effect_descriptions", builder), builder.entryBuilder());
        addEntries(ModConfig.BlockDescriptions.class.getFields(),createCategory("plugins", builder), builder.entryBuilder());
        addEntries(ModConfig.EffectDescriptions.class.getFields(),createCategory("plugins", builder), builder.entryBuilder());
        addEntries(ModConfig.Hint.class.getFields(),createCategory("hint", builder), builder.entryBuilder());
        addEntries(ModConfig.Keybinds.class.getFields(),createCategory("keybinds", builder), builder.entryBuilder());
        addEntries(ModConfig.DeveloperOptions.class.getFields(),createCategory("developer_options", builder), builder.entryBuilder());

        builder.setSavingRunnable(ModClient.CONFIG::save);
        return builder.build();
    }

    private static void addEntries(Field[] fields, ConfigCategory category, ConfigEntryBuilder entryBuilder) {
        for (var field : fields) {

            if (field.getType() == boolean.class) {
                category.addEntry(entryBuilder.startBooleanToggle(fieldName(field), fieldGet(ModClient.CONFIG, field))
                        .setSaveConsumer(fieldSetter(ModClient.CONFIG, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((boolean) fieldGet(DEFAULT_VALUES, field)).build());

            }
            else if (field.getType() == String.class) {
                category.addEntry(entryBuilder.startStrField(fieldName(field), fieldGet(ModClient.CONFIG, field))
                        .setSaveConsumer(fieldSetter(ModClient.CONFIG, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((String) fieldGet(DEFAULT_VALUES, field)).build());
            }
            else if (field.getType() == int.class) {
                category.addEntry(entryBuilder.startIntField(fieldName(field), fieldGet(ModClient.CONFIG, field))
                        .setSaveConsumer(fieldSetter(ModClient.CONFIG, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((int) fieldGet(DEFAULT_VALUES, field)).build());
            }
            else if (field.getType() == List.class) {
                category.addEntry(entryBuilder.startStrList(fieldName(field), fieldGet(ModClient.CONFIG, field))
                        .setSaveConsumer(fieldSetter(ModClient.CONFIG, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((List<String>) fieldGet(DEFAULT_VALUES, field)).build());
            }
        }
    }
}