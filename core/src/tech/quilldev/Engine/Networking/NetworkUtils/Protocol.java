package tech.quilldev.Engine.Networking.NetworkUtils;

public enum Protocol {
    INITIAL_DATA,
    KEEP_ALIVE,
    ENTITY_UPDATE,
    SPAWN_OBJECT,
    SPAWN_ITEM
    ;


    private final String label;
    Protocol(){
        this.label = String.format("{QP:%s}", this.name());
    }

    /**
     * Get the label for this protocol
     * @return this protocols label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Try to get a matching protocol from the given string
     * @param query to search for
     * @return the matching protocol if applicable
     */
    public static Protocol getFromString(String query){

        //iterate through all
        for(var protocol : Protocol.values()){

            //if the protocols label matches the query, return it
            if(protocol.getLabel().equals(query)){
                return protocol;
            }
        }

        return null;
    }
}
