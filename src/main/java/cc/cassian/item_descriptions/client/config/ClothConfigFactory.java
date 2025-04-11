package cc.cassian.item_descriptions.client.config;


import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
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

    private static boolean is(Field field, String name) {
        return field.getName().toLowerCase(Locale.ROOT).contains(name);
    }

    public static Screen create(Screen parent) {
        final var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("modmenu.nameTranslation.item-descriptions"));

        final var entryBuilder = builder.entryBuilder();
        final var configInstance = ModConfig.get();
        final var generalCategory = createCategory(null, builder);
        final var styleCategory = createCategory("style", builder);
        final var enchantmentCategory = createCategory("enchantment_descriptions", builder);
        final var effectCategory = createCategory("effect_descriptions", builder);
        final var pluginsCategory = createCategory("plugins", builder);
        final var hintCategory = createCategory("hint", builder);
        final var keyBindsCategory = createCategory("keybinds", builder);
        final var developerCategory = createCategory("developer_options", builder);


        for (var field : ModConfig.class.getFields()) {
            ConfigCategory category;
            if (is(field, "keybind_")) category = keyBindsCategory;
            else if (is(field,"block")) category = pluginsCategory;
            else if (is(field,"entity")) category = pluginsCategory;
            else if (is(field,"developer")) category = developerCategory;
            else if (is(field,"style")) category = styleCategory;
            else if (is(field,"hint_")) category = hintCategory;
            else if (is(field,"enchantment")) category = enchantmentCategory;
            else if (is(field,"effect")) category = effectCategory;
            else category = generalCategory;
            
            if (field.getType() == boolean.class) {
                category.addEntry(entryBuilder.startBooleanToggle(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((boolean) fieldGet(DEFAULT_VALUES, field)).build());

            }
            else if (field.getType() == String.class) {
                category.addEntry(entryBuilder.startStrField(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((String) fieldGet(DEFAULT_VALUES, field)).build());
            }
            else if (field.getType() == int.class) {
                category.addEntry(entryBuilder.startIntField(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((int) fieldGet(DEFAULT_VALUES, field)).build());
            }
        }
        builder.setSavingRunnable(ModConfig::save);
        return builder.build();
    }
}