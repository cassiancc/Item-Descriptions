package cc.cassian.item_descriptions.client.config;


import cc.cassian.item_descriptions.client.ModClient;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.LinkedHashMap;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class YetAnotherConfigFactory {

    private static ConfigCategory.Builder createCategory(String section, LinkedHashMap<String, ConfigCategory.Builder> categories) {
        if (categories.containsKey(section)) {
            return categories.get(section);
        } else {
            String sectionKey = section;
            if (section == null) {
                sectionKey = "";
            } else {
                sectionKey += "_";
            }
            ConfigCategory.Builder category = ConfigCategory.createBuilder().name(Component.translatable("config.item-descriptions.%stitle".formatted(sectionKey)));
            categories.put(section, category);
            return category;
        }
      }

    public static Screen create(Screen parent) {
        final YetAnotherConfigLib.Builder builder = YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("modmenu.nameTranslation.item-descriptions"));
        LinkedHashMap<String, ConfigCategory.Builder> categories = new LinkedHashMap<>();
        addEntries(ModClient.CONFIG.values(), categories);
        for (ConfigCategory.Builder s : categories.values()) {
            builder.category(s.build());
        }


        builder.save(ModClient.CONFIG::save);
        return builder.build().generateScreen(parent);
    }

    private static void addEntries(Iterable<TrackedValue<?>> fields, LinkedHashMap<String, ConfigCategory.Builder> categories) {

        for (var field : fields) {
            String categoryName = field.key().toString();
            if (categoryName.contains(".")) {
                categoryName = toSnakeCase(categoryName.split("\\.")[0]);
            } else {
                categoryName = null;
            }
            var category = createCategory(categoryName, categories);
            if (field.value().getClass() == Boolean.class) {
                category.option(Option.<Boolean>createBuilder()
                        .name(fieldName(field))
                        .description(OptionDescription.of(fieldTooltip(field, false)))
                        .binding(
                                (boolean) field.getDefaultValue(),
                                ()-> (Boolean) field.value(),
                                (value)->fieldSetter(value, (TrackedValue<Boolean>) field)
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build());

            }
            else if (field.value().getClass() == String.class) {
                category.option(Option.<String>createBuilder()
                        .name(fieldName(field))
                        .description(OptionDescription.of(fieldTooltip(field, false)))
                        .binding(
                                (String) field.getDefaultValue(),
                                ()-> (String) field.value(),
                                (value)->fieldSetter(value, (TrackedValue<String>) field)
                        )
                        .controller(StringControllerBuilder::create)
                        .build());
            }
            else if (field.value().getClass() == Integer.class) {
                category.option(Option.<Integer>createBuilder()
                        .name(fieldName(field))
                        .description(OptionDescription.of(fieldTooltip(field, false)))
                        .binding(
                                (Integer) field.getDefaultValue(),
                                ()-> (Integer) field.value(),
                                (value)->fieldSetter(value, (TrackedValue<Integer>) field)
                        )
                        .controller(IntegerFieldControllerBuilder::create)
                        .build());
            }
        }
    }
}