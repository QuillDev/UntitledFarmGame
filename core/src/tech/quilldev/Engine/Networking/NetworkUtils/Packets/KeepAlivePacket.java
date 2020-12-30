package tech.quilldev.Engine.Networking.NetworkUtils.Packets;

import tech.quilldev.Engine.Networking.NetworkUtils.Packet;
import tech.quilldev.Engine.Networking.NetworkUtils.Protocol;

public class KeepAlivePacket extends Packet {

    public KeepAlivePacket() {
        super(Protocol.KEEP_ALIVE, "breathe!");
    }
}
