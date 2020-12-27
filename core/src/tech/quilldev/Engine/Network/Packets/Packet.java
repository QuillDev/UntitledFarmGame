package tech.quilldev.Engine.Network.Packets;

public class Packet {

    public final Protocol protocol;
    public String data;

    /**
     * Create a protocol without data
     * @param protocol to create
     */
    public Packet(Protocol protocol){
        this.protocol = protocol;
        this.data = null;
    }

    /**
     * Construct a new packet
     * @param data to make packet from
     */
    public Packet(String data){
        this.protocol = Protocol.getProtocol(data);
        this.data = getType(data);
    }

    /**
     * If the packet is malformed, return null
     * @return whether the packet is malformed
     */
    public boolean isMalformed(){
        return this.protocol == null;
    }

    /**
     * Set the packets data to the given string
     * @param string to set data to
     */
    public void setData(String string){
        this.data = string;
    }
    /**
     * Checks if the packet has data
     * @return whether the packet has data
     */
    public boolean isDataless(){
        return this.data == null;
    }

    /**
     * Get the data of the current packet
     * @param string to get data from
     * @return the packets data
     */
    public String getType(String string){
        if(this.protocol == null){
            return null;
        }

        var split = string.split("\\{QP:[A-Z_]+}");

        return (split.length <= 1) ? null : split[1];
    }

    /**
     * Get the write data for the packet
     * @return the write data
     */
    public String getWriteData(){
        return this.protocol.getPacket() + this.data;
    }
    @Override
    public String toString(){
        return String.format("Packet: {\n\tProtocol: %s,\n\tData: %s\n}\n", protocol, data);
    }
}