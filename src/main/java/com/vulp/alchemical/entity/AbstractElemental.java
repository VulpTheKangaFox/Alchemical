package com.vulp.alchemical.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class AbstractElemental extends PathfinderMob implements NeutralMob, FlyingAnimal {

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(AbstractElemental.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    @Nullable
    private UUID persistentAngerTarget;

    public AbstractElemental(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        if (doesHover()) {
            this.moveControl = new FlyingMoveControl(this, 20, true);
            this.lookControl = new ElementalLookControl(this);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        if (doesHover()) {
            return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
        }
        return super.getWalkTargetValue(pos, level);
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.doesHover()) {
            if (this.onGround) {
                if (this.isControlledByLocalInstance()) {
                    this.moveRelative(0.1F, pTravelVector);
                    this.move(MoverType.SELF, this.getDeltaMovement());
                    this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.5F));
                    this.calculateEntityAnimation(true);
                    return;
                }
            }
        }
        super.travel(pTravelVector);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        this.addPersistentAngerSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.readPersistentAngerSaveData(this.level, pCompound);
    }

    abstract boolean doesHover();

    @Override
    protected PathNavigation createNavigation(Level level) {
        if (doesHover()) {
            FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level) {
                @Override
                public boolean isStableDestination(BlockPos pos) {
                    return !this.level.getBlockState(pos.below()).isAir();
                }

            };
            flyingpathnavigation.setCanOpenDoors(false);
            flyingpathnavigation.setCanFloat(false);
            flyingpathnavigation.setCanPassDoors(true);
            return flyingpathnavigation;
        } else {
            return super.createNavigation(level);
        }
    }

    @Nullable
    abstract SoundEvent stepSound();

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
        if (!doesHover() && stepSound() != null) {
            this.playSound(stepSound(), 1.0F, 1.0F);
        }
    }

    @Override
    public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 pDeltaMovement, float pFriction) {
        return super.handleRelativeFrictionAndCalculateMovement(pDeltaMovement, pFriction * 100);
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
        if (!doesHover()) {
            super.checkFallDamage(pY, pOnGround, pState, pPos);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return pDimensions.height * 0.5F;
    }

    @Override
    protected void customServerAiStep() {
        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level, false);
        }
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return false;
    }

    public abstract int decimalColor();

    public abstract ElementalTier getTier();

    @Override
    public boolean isFlying() {
        return doesHover();
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int pTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pTime);
    }

    @Override
    @javax.annotation.Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID pTarget) {
        this.persistentAngerTarget = pTarget;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    class ElementalLookControl extends LookControl {
        ElementalLookControl(Mob pMob) {
            super(pMob);
        }

        /**
         * Updates look
         */
        @Override
        public void tick() {
            if (!AbstractElemental.this.isAngry()) {
                super.tick();
            }
        }
    }

    class ElementalHurtByOtherGoal extends HurtByTargetGoal {
        ElementalHurtByOtherGoal(AbstractElemental pMob) {
            super(pMob);
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            return AbstractElemental.this.isAngry() && super.canContinueToUse();
        }

        @Override
        protected void alertOther(Mob pMob, LivingEntity pTarget) {
            if (pMob instanceof AbstractElemental && this.mob.hasLineOfSight(pTarget)) {
                pMob.setTarget(pTarget);
            }

        }
    }

    static class ElementalBecomeAngryTargetGoal extends NearestAttackableTargetGoal<Player> {
        ElementalBecomeAngryTargetGoal(AbstractElemental pMob) {
            super(pMob, Player.class, 10, true, false, pMob::isAngryAt);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        @Override
        public boolean canUse() {
            return this.elementalCanTarget() && super.canUse();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean canContinueToUse() {
            boolean flag = this.elementalCanTarget();
            if (flag && this.mob.getTarget() != null) {
                return super.canContinueToUse();
            } else {
                this.targetMob = null;
                return false;
            }
        }

        private boolean elementalCanTarget() {
            AbstractElemental elemental = (AbstractElemental) this.mob;
            return elemental.isAngry();
        }
    }

}
