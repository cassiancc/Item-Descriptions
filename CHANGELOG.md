# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.2.0] - 2025-04-18 

### Added
- New developer options to disable built-in tooltips on items, either on individual item IDs or on all items.
- New option to display the mod name as part of the Item Description. This is disabled by default to prevent overlap with similar mods. This option is translatable, based off Mod Menu's Translation API (`"modmenu.nameTranslation.modmenu": "Menu o' mods!"`).
- Support for 1.21.6 snapshots (25w18a and above).
- Support for Useful Spyglass for 1.19.2.
- Support for Limelight for 1.21.5.
- Colour options now support [decimal colour](https://minecraft.wiki/w/Calculators/Decimal_representation_of_color), in addition to colour names and formatting codes.

### Fixed
- Music Discs have the correct descriptions again.
- Revised typos and incorrect descriptions.
- Status effect descriptions not rendering.

## [2.1.2] - 2025-04-18

### Fixed
- Reverted internal changes that broke tag translations in 2.1.1.

## [2.1.1] - 2025-04-13

### Added
- Text wrapping now works based on both a configurable minimum width and the length of the block/item/mob/enchantment/effect. This should lead to less dramatic wrapping at low values. (@sisby-folk)
- On Fabric, a new debug option to generate a resource pack containing missing descriptions. (@sisby-folk)

### Changed
- Minor cleanup of config translations.
- Added effect descriptions to mod info.
- Fixed wiki links in mod info.
- Updated Heavy and Light Weighted Pressure Plate descriptions.
- Fixed typos/inaccuracies on Firefly Bush and Arrow descriptions.

## [2.1.0] - 2025-04-08

### Added
- Effect Descriptions have been added, allowing for effects to be described in a consistent style. This is automatically disabled when other mods with the same feature are present.
  - Item Descriptions primarily uses its own keys, but will fall back to ones designed for Effect Descriptions if not present.
- Descriptions for the Spring Drop.

### Fixed
- Tooltips not wrapping on indented text (@MerchantCalico)
- Crash with Useful Spyglass on Forge 1.19.2.

## [2.0.2] - 2025-04-04

### Added
- Descriptions for 25w14craftmine.

## [2.0.1] - 2025-03-31

### Changed
- Keybind translation keys.
- Changed auto-wrapper to be more compatible with mods reading Item Descriptions. (@MerchantCalico, @cassiancc)

### Fixed
- Typos and wording in Thorns description.

## [2.0.0] - 2025-03-19

## Added
- Enchantment Descriptions have been added, allowing for enchantments to be described in a consistent style. This is automatically disabled when other mods with the same feature are present.
  - Item Descriptions primarily uses its own keys, but will fall back to ones designed for Enchantment Descriptions if not present.
- Tooltips are now wrapped based off of pixel width, instead of number of characters. This should make their length more consistent. (@sisby-folk)
- Tags are now sorted based off mod namespaces, not just length. Modded tags are assumed to be the most specific, followed by vanilla tags, followed by common tags. This should fix some odd tagging behaviour with blocks like wool and items like swords and axes.
- Entity style translation keys now work for blocks and items as well, allowing for more specific key matching. This is primarily useful for matching against blocks and items with the same identifier. (`block.minecraft.oak_sign.description` instead of `lore.minecraft.oak_sign`)

### Changed
- Hinting is no longer shown when an item has no available key.
- Cleaned up large parts of internal logic.

### Fixed
- New wrapping logic should prevent double wrapping in EMI.
- Missing tool tag descriptions on Fabric 1.19 and 1.20.

### Removed
- Legacy string matching code.

## [1.15.2] - 2025-03-19

### Added

- A new config option to hint at how to use the mod, showing a configurable tooltip that there's more information about the item that can be learned by holding down a key.
  - This is fully configurable, disabled by default, and the text is resource pack driven.
- Show translation keys debug tool now supports Jade and WTHIT.
- Documented the new Alt behaviour for show translation key debug tool.
- Fix more typos.

### Fixed
- Untranslated paintings now fall back correctly.

## [1.15.1] - 2025-03-17

## Added
- Re-added show untranslated keys debug option.
- Fix various typos.
- A new fallback option to match against an item's name - useful for modded items where the ID won't necessarily match its name.

## [1.15] - 2025-03-11

## Added
- Show translation keys debug tool will now translate keys when Alt is held.
- Additional convention tags for some modded blocks and items.
- On 1.21 and above, ability to use custom painting variant descriptions.
- On 1.21 and above, support for Polymer's custom items and blocks.
- Where possible, blocks with visible contents now show the description of the item inside, not their own descriptions.
  - This includes Item Frames. 
  - This includes Fast Item Frames.
  - This includes Glowcase Item Display Entities.

## Fixed
- Incorrect descriptions on leaves, swords, and axes.

## [1.14] - 2025-03-11

## Added
- Description for Polished Andesite.
- Descriptions for 1.21.5 blocks, including Bushes, Firefly Bushes, Short Dry Grass, Tall Dry Grass, Test Block, Test Instance Block, Blue Egg, Brown Egg,  and Cactus Flowers.
- Additional descriptions for legacy Forge common tags.
- New developer option to show all translation keys available for an item.

## Changed
- Removed options to disable key translation in favour of consolidated "show potential keys" option.
- Deprecated legacy string match code, likely to be removed in the future.

## Fixed
- Tooltip wrapping on Forge/NeoForge
- Issues with nested tags not being parsed correctly.

## [1.13] - 2025-01-15

## Added

- Added descriptions for Eyeblossoms, Resin blocks, Oak Leaves, and individual heads.
- Added descriptions for 1.21.5 content, including Leaf Litter and Wildflowers.
- Added additional descriptions for common tags.
- The wiki now includes suggestions on how to structure a description. (@cassiancc, @sisby-folk)
- [Useful Spyglass](https://modrinth.com/mod/useful-spyglass) now shows block and item descriptions.

## Changed

- A majority of tags have been rewritten based off of the suggestions of @sisby-folk.
- Ported to Stonecutter.

## Fixed

- Fixed a bug where the config can fail to apply.
- For items with multiple tags, the longest tag is now chosen instead of a random one. (@sisby-folk)

## [1.12] - 2024-11-20

## Changed
- Ported to 1.21.4, added support for new Custom Model Data format.

## [1.11] - 2024-10-08

## Added
- Block Items can now show descriptions for their block tags, not just their item tags.
- Descriptions for 1.21.4 Pale Garden content.

## Fixes
- Fixes for legacy tags.

## [1.10] - 2024-09-06

## Added
- Generic Translations from Item and Block Tags have been refactored, allowing descriptions from tags to be added from resource packs. The new format is documented on the Wiki.
- Added compatibility with [Limelight](https://modrinth.com/mod/limelight). Item Descriptions results will now show when an item, block, or entity is searched.
- Spawn Eggs, Infested Blocks, Horse Armour, Coral, Netherite Upgrades, and Banner Patterns now have proper descriptions.

## Changed
- Descriptions for rare items now consistently mention their rarity, based off upcoming 1.21.2 rarity values.

## [1.9] - 2024-09-06

## Added
- Additional entity descriptions

## Changed
- Updated mod descriptions

## [1.8] - 2024-08-08

## Added
- Additional entity descriptions
- Support for custom tooltip colours by name
- Support for bold and italic tooltips.
