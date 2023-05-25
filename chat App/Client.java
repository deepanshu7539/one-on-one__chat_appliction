import java.net.*;
import java.io.*;

public class Client
 {
    ServerSocket Client;
    Socket socket;

    BufferedReader br;
    PrintWriter out;


    //constructor
    public Client()
    {
        try{
            System.out.println("Sending request to serever");
            socket=new Socket("192.168.1.3",7777);
            System.out.println("connection done.");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

        
            StartReading();
            StartWriting();


        }catch (Exception e) {
        //todo: handle exceptaion
        }
    }


    public void StartReading()
    {
        // thread for read the data

        Runnable r1=()->{

            System.out.println("reader stared");

            try{

            while(true)
            {
               
               String msg=br.readLine();
               if(msg.equals("exit"))
               {
                System.out.println("Server terminate the chat");

                socket.close();
                break;
               }
               System.out.println("Client : "+msg); 
        
        }

    }catch(Exception e)
    {
       // e.printStackTrace();
       System.out.println("connection is close");
    }
    };

    new Thread(r1).start();
}

public void StartWriting()
{
    //thread for sending the data
    Runnable r2=()-> {
        System.out.println("writer started.."); 
        while(true) {
            try{
        
                BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                String content=br1.readLine();
                out.println(content);
                out.flush();

            } catch(Exception e) {
               //todo: handle exception
                 e.printStackTrace();
    
            }

        }

    };
    new Thread(r2).start();
}

    public static void main(String[] args) {
        
        System.out.println("this is client...");
        new Client();
    }
}