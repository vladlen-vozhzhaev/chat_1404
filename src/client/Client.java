package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8179);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){
                            String response = in.readUTF();
                            System.out.println("Сообщение от сервера: "+response);
                        }
                    }catch (IOException exception){
                        System.out.println("Потеряно соединение с сервером");
                    }
                }
            });
            thread.start();
            while (true){
                System.out.println("Введите сообщение: ");
                String request = scanner.nextLine();
                out.writeUTF(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
