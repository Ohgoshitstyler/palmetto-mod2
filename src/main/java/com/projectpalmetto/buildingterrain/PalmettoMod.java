package com.projectpalmetto.buildingterrain;

import net.fabricmc.api.ModInitializer;
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

public class PalmettoMod implements ModInitializer {
    public static final String MOD_ID = "palmetto";

    // Future groups (roads, architecture, etc.) can have their own lists and tabs.
    private static final List<Item> TERRAIN_ITEMS = new ArrayList<>();

    @Override
    public void onInitialize() {
        // Phase 1: Terrain blocks (IDs stay stable once worlds start using them).
        registerTerrain("red_clay", Blocks.DIRT);
        registerTerrain("orange_clay", Blocks.DIRT);
        registerTerrain("brown_clay", Blocks.DIRT);
        registerTerrain("red_dirt", Blocks.DIRT);
        registerTerrain("orange_dirt", Blocks.DIRT);
        registerTerrain("brown_dirt", Blocks.DIRT);
        registerTerrain("red_coarse_dirt", Blocks.COARSE_DIRT);
        registerTerrain("orange_coarse_dirt", Blocks.COARSE_DIRT);
        registerTerrain("brown_coarse_dirt", Blocks.COARSE_DIRT);
        registerTerrain("red_gravel", Blocks.GRAVEL);
        registerTerrain("brown_gravel", Blocks.GRAVEL);
        registerTerrain("red_sand", Blocks.SAND);
        registerTerrain("orange_sand", Blocks.SAND);
        registerTerrain("dark_mud", Blocks.MUD);
        registerTerrain("forest_floor", Blocks.PODZOL);
        registerTerrain("rooted_soil", Blocks.ROOTED_DIRT);

        PalmettoRoads.register();

        // This creates one Palmetto-specific Creative inventory tab.
        Registry.register(
                Registries.ITEM_GROUP,
                Identifier.of(MOD_ID, "terrain"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.palmetto.terrain"))
                        .icon(() -> new ItemStack(TERRAIN_ITEMS.get(0)))
                        .entries((context, entries) -> {
                            for (Item item : TERRAIN_ITEMS) {
                                entries.add(item);
                            }
                        })
                        .build()
        );
    }

    // Sand and gravel are plant-supporting ground but do not grow meadow vegetation
    // directly from bone meal. All clay, dirt, mud, and forest soils do.
    private static boolean isFertileTerrain(String name) {
        return !name.endsWith("_sand") && !name.endsWith("_gravel");
    }

    private static Block registerTerrain(String name, Block materialToCopy) {
        Identifier identifier = Identifier.of(MOD_ID, name);
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);

        // Model is an ordinary full cube. Copy the material's sounds/strength,
        // but intentionally do not add falling-block behavior to sand/gravel.
        Block block = Registry.register(
                Registries.BLOCK,
                blockKey,
                (isFertileTerrain(name)
                        ? new PalmettoSoilBlock(AbstractBlock.Settings.copy(materialToCopy).registryKey(blockKey))
                        : new Block(AbstractBlock.Settings.copy(materialToCopy).registryKey(blockKey)))
        );

        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, identifier);
        Item item = Registry.register(
                Registries.ITEM,
                itemKey,
                new BlockItem(block, new Item.Settings()
                        .registryKey(itemKey)
                        .useBlockPrefixedTranslationKey())
        );

        TERRAIN_ITEMS.add(item);
        return block;
    }
}
