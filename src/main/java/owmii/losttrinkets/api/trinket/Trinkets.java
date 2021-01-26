package owmii.losttrinkets.api.trinket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;
import owmii.losttrinkets.api.player.PlayerData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trinkets implements INBTSerializable<CompoundNBT> {
    private final List<ITrinket> available = new ArrayList<>();
    private final List<ITrinket> active = new ArrayList<>();
    private final List<ITickableTrinket> tickable = new ArrayList<>();
    private final List<ITargetingTrinket> targeting = new ArrayList<>();
    private final PlayerData data;
    private int slots = 1;
    private boolean slotsSet;

    public Trinkets(PlayerData data) {
        this.data = data;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("slots", this.slots);
        nbt.putBoolean("slots_set", this.slotsSet);
        ListNBT availableTrinkets = new ListNBT();
        this.available.forEach((trinket) -> {
            CompoundNBT nbt1 = new CompoundNBT();
            ResourceLocation location = trinket.getItem().getRegistryName();
            Objects.requireNonNull(location);
            nbt1.putString("trinket", location.toString());
            availableTrinkets.add(nbt1);
        });
        nbt.put("available_trinkets", availableTrinkets);
        ListNBT activeTrinkets = new ListNBT();
        this.active.forEach((trinket) -> {
            CompoundNBT nbt1 = new CompoundNBT();
            ResourceLocation location = trinket.getItem().getRegistryName();
            Objects.requireNonNull(location);
            nbt1.putString("trinket", location.toString());
            activeTrinkets.add(nbt1);
        });
        nbt.put("active_trinkets", activeTrinkets);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.slots = nbt.getInt("slots");
        this.slotsSet = nbt.getBoolean("slots_set");
        ListNBT availableTrinkets = nbt.getList("available_trinkets", Constants.NBT.TAG_COMPOUND);
        this.available.clear();
        for (int i = 0; i < availableTrinkets.size(); i++) {
            CompoundNBT nbt1 = availableTrinkets.getCompound(i);
            Item trinket = ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt1.getString("trinket")));
            if (trinket instanceof ITrinket) {
                this.available.add((ITrinket) trinket);
            }
        }
        ListNBT activeTrinkets = nbt.getList("active_trinkets", Constants.NBT.TAG_COMPOUND);
        this.active.clear();
        this.tickable.clear();
        this.targeting.clear();
        for (int i = 0; i < activeTrinkets.size(); i++) {
            CompoundNBT nbt1 = activeTrinkets.getCompound(i);
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt1.getString("trinket")));
            if (item instanceof ITrinket) {
                ITrinket trinket = (ITrinket) item;
                if (this.active.size() < this.slots) {
                    this.active.add(trinket);
                    if (trinket instanceof ITickableTrinket) {
                        this.tickable.add((ITickableTrinket) trinket);
                    }
                    if (trinket instanceof ITargetingTrinket) {
                        this.targeting.add((ITargetingTrinket) trinket);
                    }
                }
            }
        }
    }

    public boolean unlockSlot() {
        if (this.slots < 40) {
            this.slots++;
            this.data.setSync(true);
            return true;
        }
        return false;
    }

    public int getSlots() {
        return this.slots;
    }

    public void initSlots(int slots) {
        if (!this.slotsSet) {
            setSlots(slots);
            this.slotsSet = true;
        }
    }

    public void setSlots(int slots) {
        this.slots = slots;
        this.data.setSync(true);
    }

    public boolean clear() {
        if (!this.available.isEmpty() || !this.active.isEmpty()) {
            this.available.clear();
            this.active.clear();
            this.data.setSync(true);
            return true;
        }
        return false;
    }

    public boolean give(ITrinket trinket) {
        if (!has(trinket)) {
            this.available.add(trinket);
            this.data.setSync(true);
            return true;
        }
        return false;
    }

    public boolean setActive(ITrinket trinket, PlayerEntity player) {
        if (isAvailable(trinket)) {
            forceActive(trinket, player);
            this.available.remove(trinket);
            return true;
        }
        return false;
    }

    public boolean setInactive(ITrinket trinket, PlayerEntity player) {
        if (isActive(trinket)) {
            this.available.add(trinket);
            this.active.remove(trinket);
            if (trinket instanceof ITickableTrinket) {
                this.tickable.remove(trinket);
            }
            if (trinket instanceof ITargetingTrinket) {
                this.targeting.remove(trinket);
            }
            if (trinket instanceof Trinket) {
                ((Trinket) trinket).removeAttributes(player);
            }
            trinket.onDeactivated(player.world, player.getPosition(), player);
            this.data.setSync(true);
            return true;
        }
        return false;
    }

    public boolean forceActive(ITrinket trinket, PlayerEntity player) {
        if (!isActive(trinket) && this.active.size() < this.slots) {
            this.active.add(trinket);
            if (trinket instanceof ITickableTrinket) {
                this.tickable.add((ITickableTrinket) trinket);
            }
            if (trinket instanceof ITargetingTrinket) {
                this.targeting.add((ITargetingTrinket) trinket);
            }
            if (trinket instanceof Trinket) {
                ((Trinket) trinket).applyAttributes(player);
            }
            trinket.onActivated(player.world, player.getPosition(), player);
            this.data.setSync(true);
            return true;
        }
        return false;
    }

    public boolean has(ITrinket trinket) {
        return isActive(trinket) || isAvailable(trinket);
    }

    public boolean isActive(ITrinket trinket) {
        return this.active.contains(trinket);
    }

    public boolean isAvailable(ITrinket trinket) {
        return this.available.contains(trinket);
    }

    public List<ITrinket> getActiveTrinkets() {
        return this.active;
    }

    public List<ITrinket> getAvailableTrinkets() {
        return this.available;
    }

    public List<ITickableTrinket> getTickable() {
        return this.tickable;
    }

    public List<ITargetingTrinket> getTargeting() {
        return this.targeting;
    }
}
