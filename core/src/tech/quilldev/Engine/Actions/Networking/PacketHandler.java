package tech.quilldev.Engine.Actions.Networking;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Actions.Networking.PacketActions.InitialDataPacketAction;
import tech.quilldev.Engine.Actions.Networking.PacketActions.KeepAlivePacketAction;
import tech.quilldev.Engine.Actions.Networking.PacketActions.SpawnObjectPacketAction;
import tech.quilldev.Engine.Actions.Networking.PacketActions.UpdateEntityPacketAction;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;

import java.util.ArrayList;

public class PacketHandler extends Action {

    //Create the packet actions
    private final KeepAlivePacketAction keepAlivePacketAction;
    private final InitialDataPacketAction initialDataPacketAction;
    private final SpawnObjectPacketAction spawnObjectPacketAction;
    private final UpdateEntityPacketAction updateEntityPacketAction;

    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public PacketHandler(GameManager gameManager) {
        super(gameManager);

        //create the packet actions
        this.initialDataPacketAction = new InitialDataPacketAction(gameManager);
        this.keepAlivePacketAction = new KeepAlivePacketAction(gameManager);
        this.spawnObjectPacketAction = new SpawnObjectPacketAction(gameManager);
        this.updateEntityPacketAction = new UpdateEntityPacketAction(gameManager);
    }

    /**
     * Execute packet actions depending on the packet
     * @param packets to get data from
     * @return the packets data
     */
    public boolean execute(ArrayList<Packet> packets){

        //iterate through each packet
        for(var packet : packets){

            //if the packet is somehow malformed, skip over it
            if(packet.isMalformed()){
                continue;
            }

            var protocol = packet.getProtocol();

            switch (protocol){
                case INITIAL_DATA:
                    initialDataPacketAction.execute(packet);
                    break;
                case KEEP_ALIVE:
                    keepAlivePacketAction.execute(packet);
                    break;
                case SPAWN_ITEM:
                    break;
                case SPAWN_OBJECT:
                    spawnObjectPacketAction.execute(packet);
                    break;
                case ENTITY_UPDATE:
                    updateEntityPacketAction.execute(packet);
                    break;
                default:
                    System.out.println("Got some packet idk what it is doe");
            }
        }
        return true;
    }
}
