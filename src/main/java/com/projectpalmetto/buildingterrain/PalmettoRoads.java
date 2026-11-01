package com.projectpalmetto.buildingterrain;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

/** Project Palmetto road surfaces plus two full-cube painted marking materials. */
public final class PalmettoRoads {
    private static final List<Item> ROAD_ITEMS = new ArrayList<>();
    private PalmettoRoads() {}

    public static void register() {
        // Original road blocks and IDs: DO NOT CHANGE.
        register("fresh_asphalt");
        register("worn_asphalt");
        register("faded_asphalt");
        register("patched_asphalt");
        register("concrete_road");
        register("concrete_sidewalk");
        register("gravel_road");
        register("compacted_dirt_road");
        register("road_shoulder");

        // Same solid paint on top, bottom and every side; no rotation or variants.
        register("solid_white_marking");
        register("solid_yellow_marking");

        Registry.register(Registries.ITEM_GROUP, Identifier.of(PalmettoMod.MOD_ID, "roads"),
                FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.palmetto.roads"))
                    .icon(() -> new ItemStack(ROAD_ITEMS.get(0)))
                    .entries((context, entries) -> { for (Item item : ROAD_ITEMS) entries.add(item); })
                    .build());
    }

    private static void register(String name) {
        Identifier id = Identifier.of(PalmettoMod.MOD_ID, name);
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);
        Block block = Registry.register(Registries.BLOCK, blockKey,
                new Block(AbstractBlock.Settings.copy(Blocks.STONE).registryKey(blockKey)));
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
        Item item = Registry.register(Registries.ITEM, itemKey,
                new BlockItem(block, new Item.Settings().registryKey(itemKey)
                    .useBlockPrefixedTranslationKey()));
        ROAD_ITEMS.add(item);
    }
}
