package com.setycz.chickens.coloredEgg;

import com.setycz.chickens.ChickensRegistry;
import com.setycz.chickens.ChickensRegistryItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by setyc on 13.02.2016.
 */
public class ItemColoredEgg extends ItemEgg {
    public ItemColoredEgg() {
        setHasSubtypes(true);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
    	ChickensRegistryItem chicken = getChickensRegistryItem(stack);
    	if(chicken.isDye()) {
    		EnumDyeColor color = EnumDyeColor.byDyeDamage(chicken.getDyeMetadata());
    		return StatCollector.translateToLocal(getUnlocalizedName() + "." + color.getUnlocalizedName() + ".name");
    	} else {
    		return StatCollector.translateToLocal(getUnlocalizedName() + "." + chicken.getEntityName() + ".egg.name");
    	}
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (ChickensRegistryItem chicken : ChickensRegistry.getItems()) {
            if (chicken.isDye() || chicken.canSpawn()) {
                subItems.add(new ItemStack(itemIn, 1, chicken.getId()));
            }
        }
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int renderPass) {
    	ChickensRegistryItem chicken = ChickensRegistry.getByType(stack.getMetadata());

    	if(chicken.isDye()) {
    		return EnumDyeColor.byDyeDamage(chicken.getDyeMetadata()).getMapColor().colorValue;	
    	};
    	
    	
    	/*if(chicken.getId() == 108) return EnumDyeColor.ORANGE.getMapColor().colorValue;
    	else if(chicken.getId() == 101) return EnumDyeColor.GRAY.getMapColor().colorValue;
    	else if(chicken.getId() == 105) return EnumDyeColor.PINK.getMapColor().colorValue;
    	else if(chicken.getId() == 102) return EnumDyeColor.CYAN.getMapColor().colorValue;
        */
    	return chicken.getBgColor();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        if (!playerIn.capabilities.isCreativeMode) {
            --itemStackIn.stackSize;
        }

        worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {
            int chickenType = getChickenType(itemStackIn);
            if (chickenType != -1) {
                EntityColoredEgg entityIn = new EntityColoredEgg(worldIn, playerIn);
                entityIn.setChickenType(chickenType);
                worldIn.spawnEntityInWorld(entityIn);
            }
        }

        playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStackIn;
    }

    private int getChickenType(ItemStack itemStack) {
        ChickensRegistryItem chicken = ChickensRegistry.getByType(itemStack.getMetadata());
        if (chicken == null) {
            return -1;
        }
        return chicken.getId();
    }
    
    private ChickensRegistryItem getChickensRegistryItem(ItemStack itemStack) {
    	ChickensRegistryItem chicken = ChickensRegistry.getByType(itemStack.getMetadata());
        return chicken;
    }
}
