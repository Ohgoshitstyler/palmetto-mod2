package com.projectpalmetto.buildingterrain;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.List;
import java.util.Optional;

/**
 * Fertile Palmetto soil. Vanilla plants recognize BlockTags.DIRT, installed separately
 * in the resource data pack. This class adds the GrassBlock-style bone-meal interaction
 * without allowing our soil to change into vanilla grass blocks.
 */
public class PalmettoSoilBlock extends Block implements Fertilizable {
    public static final MapCodec<PalmettoSoilBlock> CODEC = createCodec(PalmettoSoilBlock::new);

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    public static final TagKey<Block> FERTILE_GROUND = TagKey.of(
            RegistryKeys.BLOCK, Identifier.of("palmetto", "fertile_soil"));

    public PalmettoSoilBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.up()).isAir();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos start = pos.up();
        BlockState shortGrass = Blocks.SHORT_GRASS.getDefaultState();
        Optional<RegistryEntry.Reference<PlacedFeature>> grassFeature = world.getRegistryManager()
                .getOrThrow(RegistryKeys.PLACED_FEATURE)
                .getOptional(VegetationPlacedFeatures.GRASS_BONEMEAL);

        // Follows vanilla GrassBlock's general distribution: nearby grasses,
        // occasionally flowers provided by the local biome's flower features.
        attempts:
        for (int i = 0; i < 128; i++) {
            BlockPos candidate = start;
            for (int step = 0; step < i / 16; step++) {
                candidate = candidate.add(
                        random.nextInt(3) - 1,
                        (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                        random.nextInt(3) - 1);
                if (!world.getBlockState(candidate.down()).isIn(FERTILE_GROUND)
                        || world.getBlockState(candidate).isFullCube(world, candidate)) {
                    continue attempts;
                }
            }
            BlockState existing = world.getBlockState(candidate);
            if (existing.isOf(Blocks.SHORT_GRASS) && random.nextInt(10) == 0) {
                if (((Fertilizable) Blocks.SHORT_GRASS).isFertilizable(world, candidate, existing)) {
                    ((Fertilizable) Blocks.SHORT_GRASS).grow(world, random, candidate, existing);
                }
            }
            if (!world.isAir(candidate)) {
                continue;
            }
            if (random.nextInt(8) == 0) {
                List<ConfiguredFeature<?, ?>> flowers = world.getBiome(candidate).value()
                        .getGenerationSettings().getFlowerFeatures();
                if (flowers.isEmpty()) {
                    continue;
                }
                ConfiguredFeature<?, ?> choice = flowers.get(random.nextInt(flowers.size()));
                if (choice.config() instanceof RandomPatchFeatureConfig patch) {
                    patch.feature().value().generateUnregistered(
                            world, world.getChunkManager().getChunkGenerator(), random, candidate);
                }
            } else if (grassFeature.isPresent()) {
                grassFeature.get().value().generateUnregistered(
                        world, world.getChunkManager().getChunkGenerator(), random, candidate);
            } else if (shortGrass.canPlaceAt(world, candidate)) {
                world.setBlockState(candidate, shortGrass, Block.NOTIFY_LISTENERS);
            }
        }
    }
}
