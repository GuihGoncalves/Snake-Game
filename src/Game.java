import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener {
    public Node[] nodeSnake=new Node[10];
    public boolean left, right, up, down;

    public int tam=480;
    public int score=0;
    public int appleX=0, appleY=0;
    public int spd= 1/2;
    public Game(){
        this.setPreferredSize(new Dimension(tam,tam));
        for(int i=0; i<nodeSnake.length;i++){
            nodeSnake[i]=new Node(0,0);
        }
        this.addKeyListener(this);
    }
    public void tick(){
        for(int i=nodeSnake.length-1;i>0;i--){
            nodeSnake[i].x=nodeSnake[i-1].x;
            nodeSnake[i].y=nodeSnake[i-1].y;
        }

        if(right){
            nodeSnake[0].x+=spd;
        }else if(left){
            nodeSnake[0].x-=spd;
        }else if(up){
            nodeSnake[0].y-=spd;
        }else if(down){
            nodeSnake[0].y+=spd;
        }

        if(new Rectangle(nodeSnake[0].x,nodeSnake[0].y,10,10).intersects(new Rectangle(appleX, appleY, 10, 10))){
            appleX=new Random().nextInt(tam-10);
            appleY=new Random().nextInt(tam-10);
            score++;
            spd++;
            System.out.println("Pontos: "+score);
        }

    }
    public void render(){
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy==null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics graphics=bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0,0,tam,tam);
        for(int i=0; i<nodeSnake.length;i++){
            graphics.setColor(Color.blue);
            graphics.fillRect(nodeSnake[i].x,nodeSnake[i].y, 10, 10);

        }

        graphics.setColor(Color.red);
        graphics.fillRect(appleX,appleY, 10,10);

        graphics.dispose();
        bufferStrategy.show();
    }
    public static void main(String args[]){
        Game game = new Game();
        JFrame frame = new JFrame("Snake");
        frame.add(game);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(game).start();

    }

    @Override
    public void run(){
        while(true){
            tick();
            render();
            try {
                Thread.sleep(1000 / 60);
            }catch(InterruptedException interruptedException){
                interruptedException.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
            left = false;
            up = false;
            down = false;
        }else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
            up = false;
            down = false;
            right = false;
        }else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
            down = false;
            right = false;
            left = false;
        }else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
            right = false;
            left = false;
            up = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
