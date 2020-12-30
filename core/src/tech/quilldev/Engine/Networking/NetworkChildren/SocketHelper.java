package tech.quilldev.Engine.Networking.NetworkChildren;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.badlogic.gdx.net.Socket;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;

public class SocketHelper {

    /**
     * Try to write a line to the given socket using the given data
     * 
     * @param socket to write to
     * @param data   to write
     * @return whether the write was successful
     */
    public static boolean writeLine(Socket socket, String data) {
        return writeLine(socket.getOutputStream(), data);
    }

    /**
     * Write a packet to the given socket
     * @param socket to write to
     * @param packet to write to the socket
     * @return whether we were able to write the packet successfully
     */
    public static boolean writePacket(Socket socket, Packet packet){
        return writeLine(socket, packet.getWriteData());
    }

    /**
     * Read the data from the given socket
     * @param socket to read from
     * @return the data read from the socket
     */
    public static String readData(Socket socket) {
        return readData(socket.getInputStream());
    }

    /**
     * Read data from the given input stream
     * @param inputStream to read from
     * @return the data from the stream as a string
     */
    public static String readData(InputStream inputStream) {

        try {
            var ready = inputStream.available();

            //if no bytes are ready, return null
            if(ready == 0){
                return null;
            }

            //read all available bytes from the input stream
            var bytes = inputStream.readNBytes(ready);

            //create a string from the given bytes
            var data = new String(bytes);

            //return the data
            return data;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Read any packets from the socket
     * @param socket to read from
     * @return a list of recieved packets
     */
    public static ArrayList<Packet> readPackets(Socket socket){
        var raw = readData(socket);

        var packets = new ArrayList<Packet>();
        //if there is no data, return null
        if(raw == null || raw.length() == 0){
            return packets;
        }

        //create a list of all received messages to the socket
        var segments = raw.split("\n");

        //For each segment, try to make a packet out of it
        for(var segment : segments){
            var packet = new Packet(segment);

            //if we got a bad packet, skip it
            if(packet.isMalformed()){
                continue;
            }

            //add the packet to packets
            packets.add(packet);
        }

        return packets;
    }
    /**
     * Write the given data to the given output stream & return whether the write was successful or not
     * @param outputStream to write to
     * @param data to write
     * @return whether the write was successful
     */
    public static boolean writeLine(OutputStream outputStream, String data){

        try {

            //create the data to write
            var writeData = data + "\n";

            //write the bytes to the socket
            outputStream.write(writeData.getBytes());

            return true;
        }catch(Exception exception){
            exception.printStackTrace();
            return false;
        }
    }
}
