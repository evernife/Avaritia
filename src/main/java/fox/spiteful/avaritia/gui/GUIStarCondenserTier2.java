package fox.spiteful.avaritia.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.tile.TileEntityStarCondenser;
import fox.spiteful.avaritia.tile.TileEntityStarCondenserTier2;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIStarCondenserTier2 extends GuiContainer{
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("avaritia", "textures/gui/star_condenser_tier2_gui.png");

    private final TileEntityStarCondenserTier2 machine;
    public GUIStarCondenserTier2(InventoryPlayer player, TileEntityStarCondenserTier2 machine)
    {
        super(new ContainerStarCondenserTier2(player, machine));
        this.machine = machine;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String string = StatCollector.translateToLocal("container.star_condenser_tier2");
        this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) / 2, 4, 4210752);
        string = (machine.getCharge() == 0 ? "§7" : "§2") + "Carga: " + machine.getCharge();
        this.fontRendererObj.drawString(string, this.xSize / 2 - this.fontRendererObj.getStringWidth(string) / 2, 20, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

    }
}