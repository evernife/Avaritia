package fox.spiteful.avaritia.compat.eventhelper;

import com.gamerforea.eventhelper.util.EventUtils;
import cpw.mods.fml.common.Loader;
import fox.spiteful.avaritia.Lumberjack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;

public class ModHookEventHelper {

   public static boolean initialized = false;

   public static void initialize() {
      initialized = Loader.isModLoaded(getModID());
      if(initialized) {
         Lumberjack.log(Level.INFO,"EventHelper has been enabled on Avaritia!");
      }
   }

   public static String getModID() {
      return "EventHelper";
   }

   public static boolean cantBreak(@Nonnull EntityPlayer player, double x, double y, double z){
      if (initialized){
         return EventUtils.cantBreak(player,x,y,z);
      }
      return false;
   }

   public static boolean cantAttack(@Nonnull Entity attacker, @Nonnull Entity victim){
      if (initialized){
         return EventUtils.cantDamage(attacker,victim);
      }
      return false;
   }

}
