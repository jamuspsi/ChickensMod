package com.setycz.chickens;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by setyc on 18.02.2016.
 */
public class CommonProxy {
    public void init() {
    	
    }

    public void registerChicken(ChickensRegistryItem chicken) {
        if (chicken.isDye() && chicken.isEnabled()) {
            GameRegistry.addShapelessRecipe(
                    new ItemStack(ChickensMod.coloredEgg, 1, chicken.getId()),
                    new ItemStack(Items.egg), new ItemStack(Items.dye, 1, chicken.getDyeMetadata())
            );
        } else if(chicken.canSpawn() && chicken.isEnabled()) {
        	GameRegistry.addShapedRecipe(new ItemStack(ChickensMod.coloredEgg, 1, chicken.getId()),
        			"EEE",
        			"EIE",
        			"EEE",
        			'E', new ItemStack(Items.egg),
        			'I', chicken.createLayItem()
        	);
        }
    }

    public void registerLiquidEgg(LiquidEggRegistryItem liquidEgg) {

    }
}
