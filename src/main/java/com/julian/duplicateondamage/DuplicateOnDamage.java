package com.julian.duplicateondamage;

import java.util.List;
import java.util.UUID;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(DuplicateOnDamage.MODID)
public class DuplicateOnDamage
{
    public static final String MODID = "duplicateondamage";

    public static final Config config = new Config();

    public DuplicateOnDamage()
    {
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.SERVER, config.spec);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(DuplicateOnDamage::LivingHurtEvent);
    }

    private static void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            List<LivingEntity> entities = event.getEntity().getCommandSenderWorld().getEntitiesOfClass(LivingEntity.class, event.getEntity().getBoundingBox().inflate(config.holder.radiusX.get(), config.holder.radiusY.get(), config.holder.radiusZ.get()));
            for (LivingEntity e : entities) {
                if (!(e instanceof Player)) {
                    LivingEntity copy = (LivingEntity) e.getType().create(e.getCommandSenderWorld());
                    UUID uuid = copy.getUUID();
                    copy.deserializeNBT(e.serializeNBT());
                    copy.setUUID(uuid);
                    event.getEntity().getCommandSenderWorld().addFreshEntity(copy);
                }
            }
        }
    }
}
