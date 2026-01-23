package net.eliotex.zirconium.mixin.lithium.entity;

import java.util.concurrent.locks.ReadWriteLock;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.eliotex.zirconium.utils.lithium.lock.NullReadWriteLock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.SynchedData;

/**
 * The vanilla implementation of {@link SynchedData} performs locking when fetching or updating data due to a legacy
 * quirk in older versions of the game where updates would occur on a network thread for (de)serialization while entities
 * were ticking and accessing values from it on the main thread. In newer versions (1.14+) this no longer happens.
 * <p>
 * The SynchedData is expected to only ever updated on the main-thread (or the thread owning it in recent versions when
 * baking entities) during entity initialization and main-thread network updates, and as such the locking mechanism
 * is unnecessary since the job is to only protect against simultaneous reading and writing.
 */
@Mixin(SynchedData.class)
public class SynchedDataMixin {
    
    @Mutable
    @Shadow
    @Final
    private ReadWriteLock lock;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(Entity entity, CallbackInfo ci) {
        this.lock = new NullReadWriteLock();
    }

}