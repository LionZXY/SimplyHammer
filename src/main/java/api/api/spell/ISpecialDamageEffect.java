package api.api.spell;

import net.minecraft.entity.Entity;

public interface ISpecialDamageEffect
{
    float getDamageForEntity(Entity entity);

    String getKey();
}
