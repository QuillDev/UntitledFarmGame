package tech.quilldev.Engine.Network.Packets;


public enum Protocol {
    KEEP_ALIVE,
    END_SERVER,
    LOG_MESSAGE,
    ENTITY_MOVE
    ;

    private final String packet;

    /**
     * Create a new protocol
     */
    Protocol(){
        this.packet = String.format("{QP:%s}", this.name());
    }

    public String getPacket(){
        return this.packet;
    }
    /**
     * Get the protocol matching the given string
     * @param compare the protocol
     * @return the protocol matching the string
     */
    public static Protocol getProtocol(String compare){
        for(var protocol : Protocol.values()){
            if(compare.contains(protocol.packet)){
                return protocol;
            }
        }

        return null;
    }
}
