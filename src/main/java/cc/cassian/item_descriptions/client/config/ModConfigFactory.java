package cc.cassian.item_descriptions.client.config;


import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class ModConfigFactory implements ConfigScreenFactory<Screen> {

    private static final ModConfig DEFAULT_VALUES = new ModConfig();

    @Override
    public Screen create(Screen parent) {
        final var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.item_descriptions.title"));

        final var entryBuilder = builder.entryBuilder();
        final var configInstance = ModConfig.get();
        final var category = builder.getOrCreateCategory(Text.translatable("config.item_descriptions.title"));
        final var keyBindsCategory = builder.getOrCreateCategory(Text.translatable("config.item_descriptions.keybinds_title"));
        final var blockCategory = builder.getOrCreateCategory(Text.translatable("config.item_descriptions.block_descriptions_title"));

        for (var field : ModConfig.class.getFields()) {
            if (field.getType() == boolean.class) {
                if (field.getName().contains("keybind")) {
                    keyBindsCategory.addEntry(entryBuilder.startBooleanToggle(fieldName(field), fieldGet(configInstance, field))
                            .setSaveConsumer(fieldSetter(configInstance, field))
                            .setDefaultValue((boolean) fieldGet(DEFAULT_VALUES, field)).build());
                }
                else if (field.getName().toLowerCase().contains("block")) {
                    blockCategory.addEntry(entryBuilder.startBooleanToggle(fieldName(field), fieldGet(configInstance, field))
                            .setSaveConsumer(fieldSetter(configInstance, field))
                            .setDefaultValue((boolean) fieldGet(DEFAULT_VALUES, field)).build());
                }
                else {
                    category.addEntry(entryBuilder.startBooleanToggle(fieldName(field), fieldGet(configInstance, field))
                            .setSaveConsumer(fieldSetter(configInstance, field))
                            .setDefaultValue((boolean) fieldGet(DEFAULT_VALUES, field)).build());
                }

            } else if (field.getType() == String.class) {
                category.addEntry(entryBuilder.startStrField(fieldName(field), fieldGet(configInstance, field))
                        .setSaveConsumer(fieldSetter(configInstance, field))
                        .setDefaultValue((String) fieldGet(DEFAULT_VALUES, field)).build());
            }
        }
        builder.setSavingRunnable(ModConfig::save);
        return builder.build();
    }

    private static Text fieldName(Field field) {
        return Text.translatable("config.item_descriptions.config." + field.getName());
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