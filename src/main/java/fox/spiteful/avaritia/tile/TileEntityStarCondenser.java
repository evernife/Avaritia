package fox.spiteful.avaritia.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.compat.magicalcrops.ModHookMagicalCrops;
import fox.spiteful.avaritia.items.LudicrousItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class TileEntityStarCondenser extends TileLudicrous implements IInventory {

    private ItemStack might_heads;
    private int facing = 2;
    private int progress = 0;
    private int charge = 0;

    public int getCharge() {
        return charge;
    }

    private boolean hasMightHeadOn(){
        return might_heads != null && might_heads.getItem() == LudicrousItems.resource && might_heads.getItemDamage() == 10;
    }

    private boolean packet = true;
    private int particleCount = 0;
    @Override
    public void updateEntity(){
        if (worldObj.isRemote && this.charge > 0){
            particleCount++;
            if (particleCount >= 20){
                particleCount = 0;
                spawnParticlesAbove();
            }
        }else {
            if (charge == 0){
                if (decrStackSize(0,1) != null){
                    charge = 100;
                    packet = true;
                }else {
                    return;
                }
            }
            if(++progress >= 1400){
                charge--;
                progress = 0;
                dropBlocksAbove();
                markDirty();
                packet = true;
            }

            if(packet) {
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
                packet = false;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticlesAbove(){
        double centerX = this.xCoord + 0.5D;
        double centerY = this.yCoord + 1.5D; //AboveCenter
        double centerZ = this.zCoord + 0.5D;
        int points = 8; //amount of points to be generated
        for (int i = 0; i < 360; i += 360/points) {
            double angle = (i * Math.PI / 180);
            double x = Math.cos(angle) * 0.5;
            double z = Math.sin(angle) * 0.5;
            worldObj.spawnParticle("portal", centerX + x, centerY - 0.2, centerZ + z, 0, 0, 0);
            worldObj.spawnParticle("portal", centerX + x, centerY - 0.2, centerZ + z, 0, 0, 0);
        }
    }

    @SideOnly(Side.SERVER)
    public void dropBlocksAbove(){
        double centerX = this.xCoord + 0.5D;
        double centerY = this.yCoord + 1.5D; //AboveCenter
        double centerZ = this.zCoord + 0.5D;
        List<EntityItem> drops = new ArrayList<>();
        drops.add(new EntityItem(this.worldObj, centerX, centerY, centerZ, new ItemStack(Items.nether_star,1,0)));
        if (ModHookMagicalCrops.isEnabled()){
            drops.add(new EntityItem(this.worldObj, centerX, centerY, centerZ, new ItemStack(ModHookMagicalCrops.getZivicioItem(),2,0)));
        }
        for (EntityItem drop : drops) {
            this.getWorldObj().spawnEntityInWorld(drop);
        }
        this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.xCoord + 0.5, this.yCoord + 1, this.zCoord + 0.5, 15));
    }

    public int getFacing(){
        return facing;
    }

    public void setFacing(int dir){
        facing = dir;
    }

    @Override
    public void readCustomNBT(NBTTagCompound tag){
        this.might_heads = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("MightHeads"));
        this.progress = tag.getInteger("Progress");
        this.charge = tag.getInteger("Charge");
        this.facing = tag.getShort("Facing");
    }

    @Override
    public void writeCustomNBT(NBTTagCompound tag){
        tag.setInteger("Progress", this.progress);
        tag.setInteger("Charge", this.charge);
        tag.setShort("Facing", (short) this.facing);
        if(might_heads != null) {
            NBTTagCompound heads = new NBTTagCompound();
            might_heads.writeToNBT(heads);
            tag.setTag("MightHeads", heads);
        }
        else
            tag.removeTag("MightHeads");
    }

    @Override
    public int getSizeInventory(){
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot){
        return might_heads;
    }

    @Override
    public ItemStack decrStackSize(int slot, int decrement){
        if(!hasMightHeadOn())
            return null;
        else {
            if(decrement < might_heads.stackSize){
                ItemStack take = might_heads.splitStack(decrement);
                if(might_heads.stackSize <= 0)
                    might_heads = null;
                return take;
            }
            else {
                ItemStack take = might_heads;
                might_heads = null;
                return take;
            }
        }
    }

    @Override
    public void openInventory() {}
    @Override
    public void closeInventory() {}

    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack){
        if (slot == 0){
            return stack != null && stack.getItem() == LudicrousItems.resource && stack.getItemDamage() == 10;
        }
        return false;
    }

    @Override
    public int getInventoryStackLimit(){
        return 64;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack){
        might_heads = stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot){
        return null;
    }

    /**
     * Returns the name of the inventory
     */
    @Override
    public String getInventoryName(){
        return  "container.star_condenser";
    }

    /**
     * Returns if the inventory is named
     */
    @Override
    public boolean hasCustomInventoryName(){
        return false;
    }

}
