# Item Descriptions

This mod adds a unique Item Description to all Vanilla items, displayed when pressing Control. These descriptions sum up what a block can be used for, its source, and/or any other useful info. It strikes a compromise between not knowing anything about an item and constantly checking the Minecraft Wiki for detailed in-depth descriptions.

[ToolTipFix](https://modrinth.com/mod/tooltipfix) is recommended when using this mod to prevent tooltips from being cut off. [Mod Menu](https://modrinth.com/mod/mod-menu) and [Cloth Config](https://modrinth.com/mod/cloth-config) are also recommended if you want to change the key to activate the tooltip.

## Adding New Tooltips

By default, the mod provides descriptions for all Vanilla Minecraft items, but it has been designed in a way that makes it extensible for use in modpacks. To add a tooltip to a block, just add the following to your lang file.
```"lore.<modid>:<youritemhere>": "A mysterious modded block."```

When adding new tooltips, its recommended to keep them two sentences or less to match the ones present in the original mod.

Some blocks and items have default translations (planks, slabs, stairs, etc.) to prevent you from having to create a basic description for _every_ wood type in your mod. This translation is used when no tooltip is registered for the block, and can be overriden by having a more specific tooltip registered.

## Credits
The descriptions present in the mod are inspired by the Minecraft Legacy Console Editions. The interface used was inspired by the one present in the Better Than Adventure mod for Minecraft b1.7.3. Mod Menu/Cloth Config integration is based on the integration present in [idwtialsimmoedm](https://modrinth.com/mod/idwtialsimmoedm) under its [MIT License](https://github.com/gliscowo/idwtialsimmoedm/blob/1.21/LICENSE).