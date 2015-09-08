package ru.lionzxy.simlyhammer.libs;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import ru.lionzxy.simlyhammer.SimplyHammer;
import ru.lionzxy.simlyhammer.config.Config;
import ru.lionzxy.simlyhammer.hammers.BasicHammer;

/**
 * Created by nikit on 08.09.2015.
 */
public class HammerSettings {
    private Item.ToolMaterial material;
    private String localizeName;
    private ItemStack repairItem;
    private int breakRadius, oreDictId;
    private boolean repair, isAchive, mDiamond, mAxe, mShovel, mTorch, infinity;

    public HammerSettings(String name, int breakRadius, int harvestLevel, float speed, int damage, String rm, boolean infinity) {
        String repairMaterial;
        material = EnumHelper.addToolMaterial(name,
                Config.config.get(name, "HarvestLevel", harvestLevel).getInt(),
                Config.config.get(name, "Durability", damage).getInt(),
                (float) Config.config.get(name, "Speed", (double) speed).getDouble(),
                Config.config.get(name, "AttackDamage", harvestLevel * speed).getInt(),
                Config.config.get(name, "Enchant", (int) (speed * 10000) / damage).getInt());
        this.breakRadius = Config.config.get(name, "BreakRadius", breakRadius).getInt();
        repairMaterial = Config.config.get(name, "RepairMaterial", rm).getString();
        if (repairMaterial.indexOf(':') != -1)
            repairItem = HammerUtils.getItemFromString(repairMaterial);
        else
            oreDictId = OreDictionary.getOreID(repairMaterial);
        this.repair = Config.config.get(name, "Repairable", true).getBoolean();
        this.isAchive = Config.config.get(name, "GetAchievement", true).getBoolean();
        this.mDiamond = Config.config.get(name, "DiamondModif", true).getBoolean();
        this.mAxe = Config.config.get(name, "AxeModif", true).getBoolean();
        this.mShovel = Config.config.get(name, "ShovelModif", true).getBoolean();
        this.mTorch = Config.config.get(name, "TorchModif", true).getBoolean();
        this.infinity = Config.config.get(name, "Infinity", infinity).getBoolean();
    }

    public boolean checkMaterial(ItemStack itemStack) {
        if (!repair)
            return false;
        if (repairItem != null)
            return itemStack.isItemEqual(repairItem);
        if (oreDictId != 0) {
            int[] oreIds = OreDictionary.getOreIDs(itemStack);

            for (int oreId : oreIds)
                if (oreDictId == oreId)
                    return true;
        }
        return false;
    }

    public int getHarvestLevel() {
        return this.getMaterial().getHarvestLevel();
    }

    public int getEnchant() {
        return this.getMaterial().getEnchantability();
    }

    public int getDurability() {
        return this.getMaterial().getMaxUses();
    }

    @Deprecated
    public int getMaxUses() {
        return this.getDurability();
    }

    public float getDamageVsEntity() {
        return this.getMaterial().getDamageVsEntity();
    }

    public float getEffiency() {
        return this.getMaterial().getEfficiencyOnProperMaterial();
    }

    public String getLocalizeName() {
        return this.localizeName == null ? StatCollector.translateToLocal("item." + this.getUnlocalizeName() + ".name") : this.localizeName;
    }

    public String getUnlocalizeName() {
        return this.getMaterial().name();
    }

    public Item.ToolMaterial getMaterial() {
        return this.getMaterial();
    }

    public boolean isAchive() {
        return isAchive;
    }

    public boolean isInfinity() {
        return infinity;
    }

    public boolean isRepair() {
        return repair;
    }

    public boolean getMTorch() {
        return mTorch;
    }


    public boolean getMDiamond() {
        return mDiamond;
    }

    public boolean getMAxe() {
        return mAxe;
    }

    public boolean getMShovel() {
        return mShovel;
    }

    public String getRepairMaterial() {
        return this.repairItem != null ? repairItem.getDisplayName() : OreDictionary.getOreName(oreDictId);
    }

    public void setInfinity() {
        this.infinity = true;
    }

    public void setLocalizeName(String localizeName) {
        this.localizeName = Config.config.get(this.getMaterial().name(), "LocalizeName", localizeName).getString();
    }

}
