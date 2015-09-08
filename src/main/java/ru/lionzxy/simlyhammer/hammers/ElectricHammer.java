package ru.lionzxy.simlyhammer.hammers;
import ic2.api.item.ElectricItem;
import ic2.api.item.IBoxable;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.lionzxy.simlyhammer.libs.HammerSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikit on 08.09.2015.
 */
public class ElectricHammer extends BasicHammer implements IElectricItem,IBoxable, IItemHudInfo {
    public ElectricHammer(HammerSettings hammerSettings) {
        super(hammerSettings);
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return 30000;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 1;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 200;
    }

    @Override
    public boolean canBeStoredInToolbox(ItemStack itemstack) {
        return true;
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack) {
        List<String> list = new ArrayList<String>();
        list.add(ElectricItem.manager.getToolTip(itemStack));
        return list;
    }
}
