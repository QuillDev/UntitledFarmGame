package tech.quilldev.Engine.Network.Server;

import tech.quilldev.Engine.Network.Packets.Packet;
import tech.quilldev.Engine.Network.Packets.Protocol;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class QuillSocket extends Socket {

    // the time since the last ping
    public long lastping = 0;

    // the socket we're using for printing data etc.
    private final Socket socket;

    /**
     * Constructor for a new QuillSocket
     *
     * @param socket to base the quillsocket on
     */
    public QuillSocket(Socket socket) {
        this.socket = socket;
        this.updateLastPing();

    }

    /**
     * Return whether the socket is alive
     */
    public boolean alive() {

        this.writeProtocol(Protocol.KEEP_ALIVE);

        // if the socket is closed, return false
        return !this.isClosed();
    }

    /**
     * Write a protocol with no data
     *
     * @param protocol to send
     */
    public void writeProtocol(Protocol protocol) {
        this.writeLineAsync(protocol.getPacket() + null);
    }

    /**
     * Read data from the socket syncronously
     * @return packets to send
     */
    public ArrayList<Packet> readSocketSync() {

        //packets array
        var packets = new ArrayList<Packet>();

        try {
            var stream = this.socket.getInputStream();
            var available = stream.available();

            // if there are no bytes available, return
            if (available == 0) {
                return packets;
            }

            // read all available bytes from the stream
            var bytes = stream.readNBytes(available);

            // byte string builder
            var byteStringBuilder = new StringBuilder();

            // convert all bytes to characters
            for (var b : bytes) {
                byteStringBuilder.append((char) b);
            }

            // get the data string
            var commands = byteStringBuilder.toString().split("\n");

            // print all commands
            for (var command : commands) {
                var packet = new Packet(command);

                // if the packet is malformed, skip it
                if (packet.isMalformed() || packet.protocol == Protocol.KEEP_ALIVE) {
                    continue;
                }

                packets.add(packet);
            }

            return packets;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packets;
    }

    /**
     * Write a line to the socket syncronously
     *
     * @param data the data to send
     */
    public void writeLineSync(String data) {
        try {
            var stream = this.socket.getOutputStream();
            var packet = data + "\n";

            // write the bytes to this sockets string
            stream.write(packet.getBytes());
            stream.flush();

        } catch (IOException e) {
            // close the socket
            this.close();

            // print the first exceptions stack trace
            e.printStackTrace();
        }
    }

    /**
     * Close the socket and handle exceptions locally
     */
    @Override
    public void close() {

        // if the socket is already closed, dw about it
        if (this.isClosed()) {
            return;
        }

        // try to close the socket
        try {
            super.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * Write a line to the output string asyncronously
     *
     * @param data to write to the line
     */
    public void writeLineAsync(String data) {
        new Thread(() -> writeLineSync(data) ).start();
    }

    /**
     * Update the last ping time
     */
    public void updateLastPing() {
        this.lastping = System.currentTimeMillis();
    }

    /**
     * Get a "pretty" address for printing
     */
    public String getAddress() {
        return socket.getInetAddress().toString() + ":" + socket.getPort();
    }
}
