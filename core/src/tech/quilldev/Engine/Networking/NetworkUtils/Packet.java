package tech.quilldev.Engine.Networking.NetworkUtils;

public class Packet {

    private final Protocol protocol;
    private String data;

    public Packet(Protocol protocol, String data){
        this.protocol = protocol;
        this.data = data;
    }

    /**
     * Try to parse a packet from a given data string
     * @param data to parse
     */
    public Packet(String data){


        //parse out the parts of the data
        var parts = data.split("\\{QP:SPLIT_LOC}");

        //get the protocol & data
        this.protocol = Protocol.getFromString(parts[0]);
        this.data = (parts.length <= 1) ? null : parts[1];
    }

    /**
     * Check if the packet is malformed (missing a protocol)
     * @return whether it is malformed
     */
    public boolean isMalformed(){
        return this.protocol == null || this.isDataless();
    }

    /**
     * Check if the packet has no data
     * @return whether there is data in the packet
     */
    public boolean isDataless(){
        return (this.data == null || this.data.length() == 0);
    }

    /**
     * Get the data we are going to be writing to the socket
     * @return the socket data
     */
    public String getWriteData(){
        return this.protocol.getLabel() + "{QP:SPLIT_LOC}" + this.data;
    }

    /**
     * Get the packets protocol
     * @return the packets protocol
     */
    public Protocol getProtocol() {
        return protocol;
    }

    /**
     * set the data to the given string
     * @param data
     */
    protected void setData(String data) {
        this.data = data;
    }

    /**
     * Get the data of the packet
     * @return the packet data
     */
    public String getData() {
        return data;
    }
    @Override
    public String toString() {
        return String.format("Packet: {\n\tProtocol: %s\n\tData:%s\n}", this.protocol, this.data);
    }
}
