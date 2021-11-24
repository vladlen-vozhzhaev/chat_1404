package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class User {
    private String userName;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ObjectOutputStream oos;
    private UUID uuid;

    public User(Socket socket) {
        this.socket = socket;
        uuid = UUID.randomUUID();
        try {
            in = new DataInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public DataInputStream getIn() {return in;}

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public UUID getUuid() {
        return uuid;
    }
}
