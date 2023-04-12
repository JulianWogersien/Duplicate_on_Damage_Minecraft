package com.julian.duplicateondamage;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class Config {
    public final ForgeConfigSpec spec;

    public final ConfigHolder holder;

    public Config() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        this.holder = new ConfigHolder(builder);
        spec = builder.build();
    }

    public static class ConfigHolder {
        public final ConfigValue<Double> radiusX;
        public final ConfigValue<Double> radiusY;
        public final ConfigValue<Double> radiusZ;

        public ConfigHolder(ForgeConfigSpec.Builder builder) {
            builder.comment("duplicateondamage settings").push("duplicateondamage");
            this.radiusX = builder
                    .comment("The radius in the X axis to duplicate entities in")
                    .defineInRange("radiusX", 150.0, 0.0, Double.MAX_VALUE);
            this.radiusY = builder
                    .comment("The radius in the Y axis to duplicate entities in")
                    .defineInRange("radiusY", 150.0, 0.0, Double.MAX_VALUE);
            this.radiusZ = builder
                    .comment("The radius in the Z axis to duplicate entities in")
                    .defineInRange("radiusZ", 150.0, 0.0, Double.MAX_VALUE);
            builder.pop();
        }
    }
}
