package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class Server {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(8179);
            System.out.println("Сервер запущен");
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                User currentUser = new User(socket);
                users.add(currentUser);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            currentUser.getOos().writeObject("Введите имя: ");
                            String userName = currentUser.getIn().readUTF();
                            currentUser.setUserName(userName);
                            while (true){
                                String request = currentUser.getIn().readUTF();
                                if(request.equals("/onlineUsers")){
                                    System.out.println("Запрос списка пользователей от клиента "+currentUser.getUserName());
                                    ArrayList<String> usersName = new ArrayList<>();
                                    for (User user:users) {
                                        usersName.add(user.getUserName());
                                    }
                                    currentUser.getOos().writeObject(usersName);
                                }else{
                                    System.out.println(currentUser.getUserName()+": "+request);
                                    for (User user: users) {
                                        if(currentUser.getUuid().toString().equals(user.getUuid().toString())) continue;
                                        user.getOos().writeObject(userName+": "+request);
                                    }
                                }
                            }
                        }catch (IOException exception){
                            users.remove(currentUser);
                            System.out.println(currentUser.getUserName()+" отключился");
                        }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
