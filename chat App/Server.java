import java.net.*;


import java.io.*;
class Server
{
    ServerSocket Server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;


    //Constructor..

    public Server()
    {
        try {
        Server=new ServerSocket(7777);
        System.out.println("server is ready to accept connection");
        System.out.println("waiting..");
        socket=Server.accept();

        br= new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out=new PrintWriter(socket.getOutputStream());

        StartReading();
        StartWriting();

        } catch (Exception e) {
            e.printStackTrace();
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
                System.out.println("Client terminate the chat");

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
        Runnable r2=()->{
            System.out.println("writer started..");

            try{

            while(!socket.isClosed())
            {
                

                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();


                    out.println(content);
                    out.flush();

                    if(content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }

                
            }


        }catch(Exception e)
        {
            //e.printStackTrace();
            System.out.println("connection is close");
        }

        

        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is server");
        new Server();
    }
    
}
