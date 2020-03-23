package fox.spiteful.avaritia.compat.magicalcrops;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.Item;

public class ModHookMagicalCrops {

    private static boolean isEnabled = false;
    public static void initialize(){
        isEnabled = Loader.isModLoaded("magicalcrops");
    }

    public static boolean isEnabled(){
        return isEnabled;
    }

    private static Item zivicioItem = null;
    public static Item getZivicioItem(){
        if (zivicioItem == null){
            zivicioItem = (Item) Item.itemRegistry.getObject("magicalcrops:5ZivicioEssence");
            if (zivicioItem == null){
                zivicioItem = (Item) Item.itemRegistry.getObject("magicalcrops:magicalcrops_5ZivicioEssence");
            }
        }
        return zivicioItem;
    }
}
