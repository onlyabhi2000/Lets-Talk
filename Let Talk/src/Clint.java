
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class Client implements ActionListener {

    static JFrame f=new JFrame();//make an object of frame

    static DataOutputStream dout;

    //creating constructor
    JTextField text;
    static JPanel a1;//declare globally to use the event function
    static Box vertical=Box.createVerticalBox();//msg will be shown vertically one by one
    Client(){
        f.setLayout(null);
        JPanel p1= new JPanel();//panel for header
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);
        p1.setLayout(null);//setBounds works only if setLayout will be null
        f.add(p1);

        ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);//again convert the scaled image into ImgaeIcon
        JLabel back=new JLabel(i3);//we cant directly i2(scaled image) without converting into image icon
        back.setBounds(5,20,25,25);
        p1.add(back);//we hv set icons on above panel

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae){
                //mention water we want from our mouse event
                System.exit(0);
            }
        });
        //adding profile picture
        ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/222.jpg"));
        Image i5=i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);//again convert the scaled image into ImgaeIcon
        JLabel profile=new JLabel(i6);//we cant directly i2(scaled image) without converting into image icon
        profile.setBounds(40,10,50,50);
        p1.add(profile);

        //video calling icon
        ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8=i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon i9=new ImageIcon(i8);//again convert the scaled image into ImgaeIcon
        JLabel video=new JLabel(i9);//we cant directly i2(scaled image) without converting into image icon
        video.setBounds(300,20,30,30);
        p1.add(video);

        //voice calling icon
        ImageIcon i10= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11=i10.getImage().getScaledInstance(35,30,Image.SCALE_DEFAULT);
        ImageIcon i12=new ImageIcon(i11);//again convert the scaled image into ImgaeIcon
        JLabel phone=new JLabel(i12);//we cant directly i2(scaled image) without converting into image icon
        phone.setBounds(360,20,35,30);
        p1.add(phone);

        //more icon
        ImageIcon i13= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14=i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15=new ImageIcon(i14);//again convert the scaled image into ImgaeIcon
        JLabel moredot=new JLabel(i15);//we cant directly i2(scaled image) without converting into image icon
        moredot.setBounds(410,20,10,25);
        p1.add(moredot);

        JLabel name=new JLabel("D_Jaan");
        name.setBounds(110,15,100,18);
        name.setBackground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);

        JLabel status=new JLabel("Active Now");
        status.setBounds(110,35,100,16);
        p1.add(status);


        //panel for text messages
        a1=new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        text=new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f. add(text);

        //send button
        JButton send=new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        send.setForeground(Color.WHITE);
        //send.setFocusCycleRoot(rootPaneCheckingEnabled);
        f.add(send);

        f.setSize(450,700);
        f.setLocation(800,50);
        f.setUndecorated(true);//to remove the panel header
        f.getContentPane().setBackground(Color.black);
        f.setVisible(true);

    }
    public void actionPerformed(ActionEvent ae){
        //function override


        try {                           //note when working with server always hv to use try and catch
            String out = text.getText();//we got all data written in text fields
            //System.out.println(out);//now append the output into JPanel a1
            //JLabel output=new JLabel(out);
            JPanel p2 = formatLabel(out);
            //p2.add(output);

            a1.setLayout(new BorderLayout());//to display the msg right side

            JPanel right = new JPanel(new BorderLayout());//creating panel for chat messages visible in a1 Panel
            right.add(p2, BorderLayout.LINE_END);//we can add panel but not string.so created a bew label and passed Panel p2
            vertical.add(right);//now messages will align to right side
            vertical.add(Box.createVerticalStrut(15));//15 is the space between the two messages struct
            a1.add(vertical, BorderLayout.PAGE_START);// aligned messages and now we will get messages vertically one by one

            dout.writeUTF(out);//to send the messages to the server//protocol

            text.setText("");// to empty the text area after the message is sent
            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out){
        JPanel panel=new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JLabel output=new JLabel("<html><p style=\"width: 150px\">" +out+"</p></html>");//adding html style for the width we required
        output.setFont(new Font("Tahoma",Font.BOLD,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);//make it background visible
        output.setBorder(new EmptyBorder(15,15,15,15));//padding
        panel.add(output);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");//to display the time of the messages
        JLabel time=new JLabel();
        time.setText(sdf.format(cal.getTime()));//getting time from the calendar class
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Client();
        try{
            Socket s=new Socket("127.0.0.1",6001);//making socket //port no is 6001
            DataInputStream din=new DataInputStream(s.getInputStream());//to accept the data stream
            dout=new DataOutputStream(s.getOutputStream());//to send dat stream

            while (true){//using while loop to read infinite times
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();//readUTF is the method used to read the messages coming from server
                JPanel panel=formatLabel(msg);//to display our messages onto the frame
                JPanel left=new JPanel(new BorderLayout()); //to format icoming messages to the left side of the screen
                left.add(panel,BorderLayout.LINE_START);
                vertical.add(left);//have to make this static
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);

                f.validate();//have to make the frame static
            }

        } catch (Exception e){
            e.printStackTrace();//used to print this Throwable along with other details like class name and line number where the exception occurred means its backtrace
        }

    }
}
