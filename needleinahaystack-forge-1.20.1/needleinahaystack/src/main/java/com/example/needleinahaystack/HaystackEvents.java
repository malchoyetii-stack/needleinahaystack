package com.example.needleinahaystack;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NeedleInAHaystack.MODID)
public class HaystackEvents {

    /** 1-in-N chance per swing that the needle turns up. */
    private static final int NEEDLE_ODDS = 300;

    @SubscribeEvent
    public static void onBreak(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        BlockState state = event.getState();
        if (!state.is(NeedleInAHaystack.GIANT_HAYSTACK.get())) return;

        Player player = event.getPlayer();
        if (player.isCreative()) return; // creative players can remove it normally

        BlockPos pos = event.getPos();
        int hay = 1 + level.random.nextInt(2);
        if (player.getMainHandItem().getItem() instanceof PitchforkItem fork) {
            hay += fork.getBonusYield();
        }

        if (level.random.nextInt(NEEDLE_ODDS) == 0) {
            // Found it! Let the haystack collapse for real.
            Block.popResource(level, pos, new ItemStack(NeedleInAHaystack.NEEDLE.get()));
            Block.popResource(level, pos, new ItemStack(NeedleInAHaystack.HAY_BUNDLE.get(), hay));
            level.playSound(null, pos, SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                    pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 30, 0.6, 0.6, 0.6, 0.0);
            return; // not cancelled -> block is removed (no loot table, so it drops nothing itself)
        }

        // Otherwise: the haystack "regrows" instantly so you can keep digging.
        Block.popResource(level, pos, new ItemStack(NeedleInAHaystack.HAY_BUNDLE.get(), hay));
        level.playSound(null, pos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 12, 0.3, 0.3, 0.3, 0.05);
        event.setCanceled(true);
    }

    /**
     * Replaces the vanilla Farmer's trades with the hay economy.
     * Each level: sell hay for emeralds (better rate every level) + one upgrade to buy.
     */
    @SubscribeEvent
    public static void onTrades(VillagerTradesEvent event) {
        if (event.getType() != VillagerProfession.FARMER) return;
        var trades = event.getTrades();
        for (int lvl = 1; lvl <= 5; lvl++) trades.get(lvl).clear();

        int[] hayPerEmerald = {20, 16, 12, 8, 4};
        for (int lvl = 1; lvl <= 5; lvl++) {
            trades.get(lvl).add(new BasicItemListing(
                    new ItemStack(NeedleInAHaystack.HAY_BUNDLE.get(), hayPerEmerald[lvl - 1]),
                    new ItemStack(Items.EMERALD, 1), 16, 5 * lvl, 0.05F));
        }
        trades.get(1).add(new BasicItemListing(new ItemStack(Items.EMERALD, 6),
                new ItemStack(NeedleInAHaystack.GIANT_HAYSTACK_ITEM.get()), 8, 5, 0.05F));
        trades.get(2).add(new BasicItemListing(new ItemStack(Items.EMERALD, 12),
                new ItemStack(NeedleInAHaystack.IRON_PITCHFORK.get()), 1, 10, 0.05F));
        trades.get(3).add(new BasicItemListing(new ItemStack(Items.EMERALD, 24),
                new ItemStack(NeedleInAHaystack.GOLDEN_PITCHFORK.get()), 1, 15, 0.05F));
        trades.get(4).add(new BasicItemListing(new ItemStack(Items.EMERALD, 40),
                new ItemStack(NeedleInAHaystack.DIAMOND_PITCHFORK.get()), 1, 20, 0.05F));
        trades.get(5).add(new BasicItemListing(new ItemStack(Items.EMERALD, 64),
                new ItemStack(NeedleInAHaystack.NETHERITE_PITCHFORK.get()), 1, 30, 0.05F));
    }
}
