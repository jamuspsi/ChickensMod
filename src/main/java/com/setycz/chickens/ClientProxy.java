package com.setycz.chickens;

import com.setycz.chickens.chicken.EntityChickensChicken;
import com.setycz.chickens.chicken.ModelChickensChicken;
import com.setycz.chickens.chicken.RenderChickensChicken;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

/**
 * Created by setyc on 18.02.2016.
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void init() {
        super.init();

        // chicken entity registration
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        renderManager.entityRenderMap.put(EntityChickensChicken.class, new RenderChickensChicken(renderManager, new ModelChickensChicken(), 0.3F));

        registerItemModel(Item.getItemFromBlock(ChickensMod.henhouse), 0);
        registerItemModel(Item.getItemFromBlock(ChickensMod.henhouse_acacia), 0);
        registerItemModel(Item.getItemFromBlock(ChickensMod.henhouse_birch), 0);
        registerItemModel(Item.getItemFromBlock(ChickensMod.henhouse_dark_oak), 0);
        registerItemModel(Item.getItemFromBlock(ChickensMod.henhouse_jungle), 0);
        registerItemModel(Item.getItemFromBlock(ChickensMod.henhouse_spruce), 0);
    }

    private void registerItemModel(Item item, int meta) {
        ModelResourceLocation resourceLocation = new ModelResourceLocation(ChickensMod.MODID + ":" + ChickensMod.getItemName(item), "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, resourceLocation);
    }

    @Override
    public void registerLiquidEgg(LiquidEggRegistryItem liquidEgg) {
        super.registerLiquidEgg(liquidEgg);

        registerItemModel(ChickensMod.liquidEgg, liquidEgg.getId());
    }

    @Override
    public void registerChicken(ChickensRegistryItem chicken) {
        super.registerChicken(chicken);

        registerItemModel(ChickensMod.spawnEgg, chicken.getId());

        if (chicken.isDye() || chicken.canSpawn()) {
            registerItemModel(ChickensMod.coloredEgg, chicken.getId());
        }
    }
}
