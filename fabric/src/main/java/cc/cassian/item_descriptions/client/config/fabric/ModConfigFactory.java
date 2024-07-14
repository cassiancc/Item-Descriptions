package cc.cassian.item_descriptions.client.config.fabric;


import cc.cassian.item_descriptions.client.config.ModConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import static cc.cassian.item_descriptions.client.helpers.ModHelpers.*;

public class ModConfigFactory implements ConfigScreenFactory<Screen> {

    private static final ModConfig DEFAULT_VALUES = new ModConfig();

    @Override
    public Screen create(Screen parent) {
        final var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.item-descriptions.title"));

        final var entryBuilder = builder.entryBuilder();
        final var configInstance = ModConfig.get();
        final var generalCategory = builder.getOrCreateCategory(Text.translatable("config.item-descriptions.title"));
        final var keyBindsCategory = builder.getOrCreateCategory(Text.translatable("config.item-descriptions.keybinds_title"));
        final var blockCategory = builder.getOrCreateCategory(Text.translatable("config.item-descriptions.block_descriptions_title"));
        final var entityCategory = builder.getOrCreateCategory(Text.translatable("config.item-descriptions.entity_descriptions_title"));
        final var developerCategory = builder.getOrCreateCategory(Text.translatable("config.item-descriptions.developer_options_title"));


        for (var field : ModConfig.class.getFields()) {
            ConfigCategory category;
            if (field.getName().contains("keybind")) category = keyBindsCategory;
            else if (field.getName().toLowerCase().contains("block")) category = blockCategory;
            else if (field.getName().toLowerCase().contains("entity")) category = entityCategory;
            else if (field.getName().toLowerCase().contains("developer")) category = developerCategory;
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