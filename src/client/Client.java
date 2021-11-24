package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    static ArrayList<String> usersName = new ArrayList<>();
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8179);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            Object object = in.readObject();
                            if(object.getClass().equals(usersName.getClass())){
                                usersName = (ArrayList<String>) object;
                                System.out.println(usersName);
                            }else{
                                String response = (String) object;
                                System.out.println(response);
                            }
                        }
                    }catch (Exception exception){
                        System.out.println("Потеряно соединение с сервером");
                    }
                }
            });
            thread.start();
            while (true){
                String request = scanner.nextLine();
                out.writeUTF(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
