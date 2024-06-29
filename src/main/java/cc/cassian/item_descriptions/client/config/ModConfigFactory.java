package cc.cassian.item_descriptions.client.config;


import io.github.prospector.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class ModConfigFactory implements ConfigScreenFactory<Screen> {

    private static final ModConfig DEFAULT_VALUES = new ModConfig();

    @Override
    public Screen create(Screen parent) {
        final ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(I18n.translate("title.item_descriptions.config"));

        final ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        final ModConfig configInstance = ModConfig.get();
        final ConfigCategory category = builder.getOrCreateCategory(I18n.translate("title.item_descriptions.config"));

        for (Field field : ModConfig.class.getFields()) {
            if (field.getType() == boolean.class) {
                category.addEntry(entryBuilder.startBooleanToggle(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setDefaultValue((boolean) fieldGet(DEFAULT_VALUES, field)).build());
            } else if (field.getType() == String.class) {
                category.addEntry(entryBuilder.startStrField(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setDefaultValue((String) fieldGet(DEFAULT_VALUES, field)).build());
            }
        }
        builder.setSavingRunnable(ModConfig::save);
        return builder.build();
    }

    private static String fieldName(Field field) {
        return I18n.translate("title.item_descriptions.config." + field.getName());
    }

    @SuppressWarnings("unchecked")
    private static <T> T fieldGet(Object instance, Field field) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Consumer<T> fieldSetter(Object instance, Field field) {
        return t -> {
            try {
                field.set(instance, t);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }
}