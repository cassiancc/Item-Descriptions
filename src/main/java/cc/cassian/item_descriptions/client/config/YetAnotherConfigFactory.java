package cc.cassian.item_descriptions.client.config;


import cc.cassian.item_descriptions.client.ModClient;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.DropdownStringControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.gui.controllers.string.number.IntegerFieldController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.List;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class YetAnotherConfigFactory {

    private static final ModConfig DEFAULT_VALUES = new ModConfig();

    private static ConfigCategory.Builder createCategory(String section) {
        if (section == null) {
            section = "";
        } else {
            section += "_";
        }
        return ConfigCategory.createBuilder().name(Text.translatable("config.item-descriptions.%stitle".formatted(section)));
    }

    public static Screen create(Screen parent) {
        final var builder = YetAnotherConfigLib.createBuilder()
                .title(Text.translatable("modmenu.nameTranslation.item-descriptions"));

        addEntries(ModConfig.class.getFields(), ModClient.CONFIG, DEFAULT_VALUES, null, builder);
        addEntries(ModConfig.Style.class.getFields(), ModClient.CONFIG.style, DEFAULT_VALUES.style, "style", builder);
        addEntries(ModConfig.EnchantmentDescriptions.class.getFields(), ModClient.CONFIG.enchantmentDescriptions, DEFAULT_VALUES.enchantmentDescriptions, "enchantment_descriptions", builder);
        addEntries(ModConfig.EffectDescriptions.class.getFields(), ModClient.CONFIG.effectDescriptions, DEFAULT_VALUES.effectDescriptions, "effect_descriptions", builder);
        addEntries(ModConfig.BlockDescriptions.class.getFields(), ModClient.CONFIG.blockDescriptions, DEFAULT_VALUES.blockDescriptions, "block_descriptions" , builder);
        addEntries(ModConfig.EntityDescriptions.class.getFields(), ModClient.CONFIG.entityDescriptions, DEFAULT_VALUES.entityDescriptions, "entity_descriptions", builder);
        addEntries(ModConfig.Hint.class.getFields(), ModClient.CONFIG.hint, DEFAULT_VALUES.hint, "hint", builder);
        addEntries(ModConfig.Keybinds.class.getFields(), ModClient.CONFIG.keybinds, DEFAULT_VALUES.keybinds, "keybinds", builder);
        addEntries(ModConfig.DeveloperOptions.class.getFields(), ModClient.CONFIG.developer, DEFAULT_VALUES.developer, "developer_options", builder);

        builder.save(ModClient.CONFIG::save);
        return builder.build().generateScreen(parent);
    }

    private static void addEntries(Field[] fields, Object config, Object defaultValues, String categoryName, YetAnotherConfigLib.Builder builder) {
        var category = createCategory(categoryName);
        for (var field : fields) {

            if (field.getType() == boolean.class) {
                category.option(Option.<Boolean>createBuilder()
                        .name(fieldName(field, categoryName))
                        .description(OptionDescription.of(fieldTooltip(field, categoryName)))
                        .binding(
                                fieldGet(defaultValues, field),
                                () -> fieldGet(config, field),
                                fieldSetter(config, field)
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build());

            }
            else if (field.getType() == String.class) {
                category.option(Option.<String>createBuilder()
                        .name(fieldName(field, categoryName))
                        .description(OptionDescription.of(fieldTooltip(field, categoryName)))
                        .binding(
                                fieldGet(defaultValues, field),
                                () -> fieldGet(config, field),
                                fieldSetter(config, field)
                        )
                        .controller(StringControllerBuilder::create)
                        .build());
            }
            else if (field.getType() == int.class) {
                category.option(Option.<Integer>createBuilder()
                        .name(fieldName(field, categoryName))
                        .description(OptionDescription.of(fieldTooltip(field, categoryName)))
                        .binding(
                                fieldGet(defaultValues, field),
                                () -> fieldGet(config, field),
                                fieldSetter(config, field)
                        )
                        .controller(IntegerFieldControllerBuilder::create)
                        .build());
            }
        }
        builder.category(category.build());
    }
}