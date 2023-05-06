import javax.swing.*;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {
        myJF gameFrame = new myJF();
        gameFrame.gameStart();
    }
}
class myJF extends JFrame{
    static int UP_KEY =38;
    static int DOWN_KEY =40;
    static int LEFT_KEY =37;
    static int RIGHT_KEY =39;
    static int HOME_KEY =36;
    int[][] data={
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    int x,y;
    int stepNum;
    int gameState=0;

    myJF(){
        jfInto();
        intoBlock();
        findXY();
        paintView();

        this.setVisible(true);
    }
    myJF(int[][] ignoredData){
        jfInto();
        for(int i=0;i<4;i++){
            System.arraycopy(ignoredData[i], 0, data[i], 0, 4);
        }
        findXY();
        paintView();
    }
    void gameStart(){
        this.addKeyListener(new KeyListener(){
            public  void  keyTyped(KeyEvent e) {
                // 键盘按键被释放时调用
            }
            public  void  keyPressed(KeyEvent e) {
                // 键盘按键被按下时调用
            }
            public  void  keyReleased(KeyEvent e) {
                // 键盘按键被释放时调用
                if(gameState!=1){
                    move(e.getKeyCode());
                }
            }
        });

    }//游戏开始
    void jfInto(){
        this.setTitle("石头迷阵");//设置标题
        this.setSize(514,595);//设置尺寸
        this.setLayout(null);//取消默认布局
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置关闭后的模式
        this.setAlwaysOnTop(true);//设置保持顶层
        this.setLocationRelativeTo(null);//居中
        stepNum=0;
    } //窗体的设置项
    void intoBlock(){
        Random rand= new Random();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                swapBlock(i,j, rand.nextInt(0,4), rand.nextInt(0,4));
            }
        }
    }//打乱方块

    void paintView(){
        this.getContentPane().removeAll();

        if(getGameStatus()){
            x=3;
            y=3;
            JLabel jl=new JLabel(new ImageIcon("image/win.png"));
            jl.setBounds(124,253,266,88);
            getContentPane().add(jl);
            System.out.println("win");
            gameState=1;
        }//win判断

        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                JLabel jl=new JLabel(new ImageIcon("image/"+data[i][j]+".png"));
                jl.setBounds(50+100*j,90+100*i,100,100);
                this.getContentPane().add(jl);
            }
        }//方块

        //显示步数
        JLabel Steps=new JLabel("已用步数:"+stepNum);
        Steps.setBounds(50,17,100,20);
        this.getContentPane().add(Steps);

        //remake按钮
        JButton btn1=new JButton("重新开始");
        btn1.setBounds(350,17,100,20);
        btn1.setFocusable(false);
        this.getContentPane().add(btn1);
        btn1.addActionListener(e->{
            stepNum=0;
            intoBlock();
            findXY();
            paintView();
        });

        //背景
        JLabel jl=new JLabel(new ImageIcon("image/background.png"));
        jl.setBounds(25,30,450,484);
        this.getContentPane().add(jl);

        this.getContentPane().repaint();
    } //刷新界面

    void findXY(){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(data[i][j]==0){
                    x=i;
                    y=j;
                }
            }
        }
        System.out.println("x:"+(x+1)+" y:"+(y+1));
    }//寻找空位置的坐标

    void swapBlock(int x1,int y1, int x2,int y2){
        int tmp=data[x1][y1];
        data[x1][y1]=data[x2][y2];
        data[x2][y2]=tmp;
    }//交换两个块
    void move(int keyCode){
        if(keyCode==LEFT_KEY){
            if(y<3){
                swapBlock(x,y,x,y+1);
                y++;
                stepNum++;
                paintView();
            }
        }else if(keyCode==RIGHT_KEY){
            if(y>0){
                swapBlock(x,y,x,y-1);
                y--;
                stepNum++;
                paintView();
            }
        }else if(keyCode==UP_KEY){
            if(x<3){
                swapBlock(x,y,x+1,y);
                x++;
                stepNum++;
                paintView();
            }
        }else if(keyCode==DOWN_KEY){
            if(x>0){
                swapBlock(x,y,x-1,y);
                x--;
                stepNum++;
                paintView();
            }
        }else if(keyCode==HOME_KEY){
            data=new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
            paintView();
        }
        System.out.println("x:"+(x+1)+" y:"+(y+1));
    }//移动
    boolean getGameStatus(){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(data[i][j]!=4*i+j+1&&data[i][j]!=0){
                    return false;
                }
            }
        }
        return true;
    }//判断输赢
}