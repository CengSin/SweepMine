package info.zetile.demo;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MineFrame extends JFrame {

    private int[][] array;
    private static int[][] list;    //标记数组
    private Random rd = new Random();   //声明Random类对象，随机产生某范围内的数字
    private Graphics2D g2d; //处理画笔周围毛刺，抗锯齿
    private int[][] dir = {{-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    //代表每个方块中的方法，分别为左上 上 右上 左 右 左下 下 右下
    private ImageIcon img; //将雷的照片画到面板上

    public static int score = 0;    //计算所得分数
    public static int MINE = 10;    //雷的个数

    // 画出整个扫雷游戏的界面
    public void MineUI() {
        this.setTitle("知乎学习Java扫雷");    //设置窗体名称
        this.setSize(580, 630);         //设置床体大小
        this.setLocationRelativeTo(null);   //设置显示在屏幕正中央
        this.setDefaultCloseOperation(3);   //关闭窗体

        array = new int[9][9];  //实例化数组对象，用来抽象扫雷游戏中的棋盘
        list = new int[9][9];

        produceData();  //随机产生雷，完善数组array中的所有数据

        this.setVisible(true);  //设置窗体可见
        MineListener ml = new MineListener(this, this.array, this.list, this.dir);
        //将面板，array数组，list数组传给事件监听类
        this.addMouseListener(ml);
        //给面板添加事件监听类
    }

    public void produceData() {

        //随机产生10个雷
        int ax = 0;
        int ay = 0;

        for(int i = 0; i < 10; i++) {
            //判断随机产生的位置是否存在雷，如果存在雷，则重新生成一对随机数字
            do{
                //随机生成雷的位置
                ax = rd.nextInt(array.length);
                ay = rd.nextInt(array.length);
            }while(array[ax][ay] == 10);

            //设置雷的位置
            array[ax][ay] = 10;
        }

        // 生成所有的扫雷棋盘中的数据
        int tempx = 0;
        int tempy = 0;

        // 通过二次循环为array数组生成数据
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[i].length; j++) {
                if(array[ax][ay] == 0) {    //如果这个位置为零，表示这个位置没有雷，
                    for(int m = 0; m < 8; m++) {
                        tempx = i + dir[m][0];
                        tempy = j + dir[m][1];

                        if(tempx >=0 && tempx < array.length && tempy >= 0 && tempy < array.length) {
                            if(array[tempx][tempy] == 10) {
                                array[i][j] ++;//如果相邻某位置上有类，则数值++
                            }
                        }
                    }
                }
            }
        }

    }

    public void paint(Graphics g) {
        super.paint(g);
        img = new ImageIcon(this.getClass().getResource("timg.jpg"));

        g2d = (Graphics2D)g;//强转为Graphics2D类型
        //消除抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //设置画笔粗细
        g2d.setStroke(new BasicStroke(4));
        //设置画笔颜色
        g.setColor(new Color(0,128,0));

        //绘制棋盘
        for (int i = 0; i <= array.length; i++) {
            g.drawLine(50, 70 + i * 50, 500, 70 + i  * 50);
            g.drawLine(50 + i * 50, 70, 50 + i * 50, 520);
        }

        //只在点击之后才格子上显示数据，所以，也为了好看一点点，一个一个地画矩形覆盖掉棋盘，
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array[i].length;j++){
                g.setColor(new Color(107,142,35));
                g.fillRect(50 + j  * 50, 70 + i * 50, 50-3, 50-3);//减3是为了让格子看起来立体一点
            }
        }

        //在面板棋盘下方画雷图片，并显示雷有多少
        g.drawImage(img.getImage(), 60, 540, null);
        g.setFont(new Font("Arial",Font.HANGING_BASELINE, 20));
        g.drawString(MINE+"", 115, 580);

        //用画笔写上"新游戏"
        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.HANGING_BASELINE,30));
        String s = "new game"+"";
        g.drawString(s, 170, 580);

    }

}
