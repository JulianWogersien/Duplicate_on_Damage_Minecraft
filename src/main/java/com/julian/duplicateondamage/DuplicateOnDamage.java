package com.julian.duplicateondamage;

import java.util.List;
import java.util.UUID;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DuplicateOnDamage.MODID)
public class DuplicateOnDamage
{
    public static final String MODID = "duplicateondamage";

    public DuplicateOnDamage()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.addListener(DuplicateOnDamage::LivingHurtEvent);
    }

    private static void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            List<LivingEntity> entities = event.getEntity().getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, event.getEntity().getBoundingBox().inflate(150.0d, 150.0d, 150.0d));
            for (LivingEntity e : entities) {
                if (!(e instanceof Player)) {
                    LivingEntity copy = (LivingEntity) e.getType().create(e.getCommandSenderWorld());
                    UUID uuid = copy.getUUID();
                    copy.setPos(e.getX(), e.getY(), e.getZ());
                    copy.setHealth(e.getHealth());
                    copy.setDeltaMovement(e.getDeltaMovement());
                    copy.setCustomName(e.getCustomName());
                    copy.setCustomNameVisible(e.isCustomNameVisible());
                    copy.deserializeNBT(e.serializeNBT());
                    copy.setUUID(uuid);
                    event.getEntity().getCommandSenderWorld().addFreshEntity(copy);
                }
            }
        }
    }
}
