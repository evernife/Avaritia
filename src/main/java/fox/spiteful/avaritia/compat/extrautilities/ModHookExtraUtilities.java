package fox.spiteful.avaritia.compat.extrautilities;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.Item;

public class ModHookExtraUtilities {

    private static boolean isEnabled = false;

    public static void initialize(){
        isEnabled = Loader.isModLoaded("ExtraUtilities");
    }

    public static boolean isEnabled(){
        return isEnabled;
    }

    private static Item sigilItem = null;
    public static Item getSigilItem(){
        if (sigilItem == null){
            sigilItem = (Item) Item.itemRegistry.getObject("ExtraUtilities:divisionSigil");
        }
        return sigilItem;
    }
}
