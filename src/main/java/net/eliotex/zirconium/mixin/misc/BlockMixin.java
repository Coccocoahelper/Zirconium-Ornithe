/*package net.eliotex.zirconium.mixin.misc;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.IdRegistry;

@Mixin(value = Block.class)
public abstract class BlockMixin {

	@Shadow
	@Final
	public static IdRegistry blockRegistry;
    */

	/**
	 * @author jss2a98aj
	 * @reason Most calls have 0 (air), this makes those calls faster.
	 */
    /*
	@Overwrite()
	public static Block getBlockById(int blockId) {
		if (blockId == 0) return Blocks.air;
		Block ret = (Block) blockRegistry.getObjectById(blockId);
		return ret == null ? Blocks.air : ret;
	}
}
*/