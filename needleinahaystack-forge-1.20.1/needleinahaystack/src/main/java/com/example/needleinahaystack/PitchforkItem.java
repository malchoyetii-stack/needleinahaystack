package com.example.needleinahaystack;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/** An unbreakable tool that is only good at one thing: demolishing haystacks. */
public class PitchforkItem extends Item {
    private final float speed;
    private final int bonusYield;

    public PitchforkItem(float speed, int bonusYield, Properties properties) {
        super(properties);
        this.speed = speed;
        this.bonusYield = bonusYield;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(NeedleInAHaystack.GIANT_HAYSTACK.get()) ? speed : 1.0F;
    }

    public int getBonusYield() {
        return bonusYield;
    }
}
