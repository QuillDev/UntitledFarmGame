package tech.quilldev.Engine.Actions.Networking.Recieve;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Network.Packets.Packet;

import java.util.ArrayList;
import java.util.Objects;

public class NetworkReceiveAction {

    private final EntityMovePacketAction entityMovePacketAction;

    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public NetworkReceiveAction(GameManager gameManager) {
        this.entityMovePacketAction = new EntityMovePacketAction(gameManager);
    }

    public boolean execute(ArrayList<Packet> packets){

        for(Packet packet : packets){

            //if the packet is malformed, continue
            if (packet.isMalformed()){
                continue;
            }

            switch (Objects.requireNonNull(packet.protocol)){
                case KEEP_ALIVE:
                    break;
                case END_SERVER:
                    break;
                case LOG_MESSAGE:
                    break;
                case ENTITY_MOVE: {
                    entityMovePacketAction.execute(packet);
                } break;

                default:{
                    return false;
                }

            }
        }
        return true;
    }
}
