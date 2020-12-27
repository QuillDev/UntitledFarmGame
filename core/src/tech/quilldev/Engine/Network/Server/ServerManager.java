package tech.quilldev.Engine.Network.Server;

import tech.quilldev.DebugModes;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerManager {

    private final SocketManager socketManager;
    private ScheduledExecutorService scheduler;
    private boolean active = false;

    public ServerManager(){
        this.socketManager = new SocketManager();
    }

    /**
     * Start the server
     */
    public void start(){

        //Start the server
        if(active){
            return;
        }

        //setup the scheduler
        this.scheduler = new ScheduledThreadPoolExecutor(1);
        this.scheduler.scheduleAtFixedRate(socketManager::handle, 0, 20, TimeUnit.MILLISECONDS);
        this.scheduler.scheduleAtFixedRate(socketManager::checkLifelines, 0 , 2, TimeUnit.SECONDS);

        //star the socket manager
        this.socketManager.start();
        this.active = true;
    }

    /**
     * Get the socket address for the server as a string
     * @return the socket address as a string
     */
    public String getSocketAddress(){
        if(!active){
            return "Not Connected!";
        }
        return String.format("%s:%s", socketManager.getHostName(), socketManager.getPort());
    }

    /**
     * Shutdown the scheduler
     */
    public void stop(){

        //if we're already stopped, return.
        if(!active){
            return;
        }

        //shutdown the scheduler
        this.scheduler.shutdown();
        this.scheduler = null;

        //stop the socket manager
        this.socketManager.stop();

        //set active to false
        this.active = false;
    }
}
