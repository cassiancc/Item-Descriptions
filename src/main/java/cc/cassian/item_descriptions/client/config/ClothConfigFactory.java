package cc.cassian.item_descriptions.client.config;


import cc.cassian.item_descriptions.client.ModClient;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class ClothConfigFactory {

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

        addEntries(ModClient.CONFIG.values(), builder);

        builder.setSavingRunnable(ModClient.CONFIG::save);
        return builder.build();
    }

    private static void addEntries(Iterable<TrackedValue<?>> fields, ConfigBuilder builder) {
        var entryBuilder = builder.entryBuilder();
        for (var field : fields) {
            String categoryName = field.key().toString();
            if (categoryName.contains(".")) {
                categoryName = toSnakeCase(categoryName.split("\\.")[0]);
            } else {
                categoryName = null;
            }
            var category = createCategory(categoryName, builder);
            if (field.value().getClass() == Boolean.class) {
                category.addEntry(entryBuilder.startBooleanToggle(fieldName(field), (boolean) field.value())
                        .setSaveConsumer((o)-> fieldSetter(o, (TrackedValue<Boolean>) field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((boolean) field.getDefaultValue()).build());

            }
            else if (field.value().getClass() == String.class) {
                category.addEntry(entryBuilder.startStrField(fieldName(field), (String) field.value())
                        .setSaveConsumer((o)-> fieldSetter(o, (TrackedValue<String>) field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((String) field.getDefaultValue()).build());
            }
            else if (field.value().getClass() == Integer.class) {
                category.addEntry(entryBuilder.startIntField(fieldName(field), (int) field.value())
                        .setSaveConsumer((o)-> fieldSetter(o, (TrackedValue<Integer>) field))
                        .setTooltip(fieldTooltip(field))
                        .setDefaultValue((int) field.getDefaultValue()).build());
            }
//            else if (field.getType() == List.class) {
//                category.addEntry(entryBuilder.startStrList(fieldName(field, categoryName), fieldGet(config, field))
//                        .setSaveConsumer(fieldSetter(config, field))
//                        .setTooltip(fieldTooltip(field, categoryName))
//                        .setDefaultValue((List<String>) fieldGet(defaultValues, field)).build());
//            }
        }
    }
}