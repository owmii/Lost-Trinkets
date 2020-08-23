package owmii.losttrinkets.network;

import owmii.losttrinkets.LostTrinkets;
import owmii.losttrinkets.network.packet.*;

public class Packets {
    public static void register() {
        LostTrinkets.NET.register(new SyncDataPacket());
        LostTrinkets.NET.register(new SetActivePacket());
        LostTrinkets.NET.register(new SetInactivePacket());
        LostTrinkets.NET.register(new UnlockSlotPacket());
        LostTrinkets.NET.register(new TrinketUnlockedPacket());
        LostTrinkets.NET.register(new SyncFlyPacket());
        LostTrinkets.NET.register(new MagnetoPacket());
    }
}
