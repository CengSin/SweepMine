package info.zetile.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MineListener implements MouseListener{

    private int[][] array;
    public int[][] list;
    private MineFrame mf;
    private Graphics gra;
    private Graphics2D g2d;
    private int[][] dir;
    private int flag = 0;
    private int x, y, row, col;
    private int tempx, tempy;
    private ImageIcon img;
    private int count = 9*9;    //记录下剩下的未被访问到的格子数

    public MineListener(MineFrame mf, int[][] array, int[][] list, int[][] dir) {
        this.mf = mf;
        this.array = array;
        this.gra = mf.getGraphics();
        this.list = list;

        g2d = (Graphics2D) gra;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        row = (y-70)/50;
        col = (x-50)/50;

        img = new ImageIcon(this.getClass().getResource("timg.jpg"));

        if(x > 50 && x < 500 && y > 70 && y < 520) {
            //当鼠标点击在这个范围内
            if(array[row][col] == 0 && row >= 0 && row < array.length && col >= 0 && col < array.length) {
                //如果这个格子数值为0，则探测所有能打开的格子
                sweepMine(array, row, col);
            } else if(array[row][col] > 0 && array[row][col] < array.length) {
                //否则，就设置相应位置上的标记数组的值等于原有数值加1
                mf.score += array[row][col] * 10;
                list[row][col]=array[row][col] + 1;
            } else {
                list[row][col] = 10;
            }

            //根据上面三种情况打开应该打开的格子
            openCell(gra, list);

            //数一数一共打开多少格子，是否通关
            count = win();

            int i;
            if(count == 9*9-10) {
                g2d.setFont(new Font("TimesRoman",Font.ITALIC,60));
                g2d.setColor(Color.ORANGE);
                g2d.drawString("Game Win!!!", 125, 300);//显示你赢了
                mf.removeMouseListener(this);//给面板移除监听，再在面板上操作已经没有反应了
            }
        }
    }

    private int win() {
        int total = 0;
        for(int i=0;i<list.length;i++){
            for(int j=0;j<list[i].length;j++){
                if(list[i][j]==1 || list[i][j]==2 || list[i][j]==3 || list[i][j]==4 ||
                        list[i][j]==5 || list[i][j]==6 || list[i][j]==7 ||list[i][j]==8
                        || list[i][j]==9){
                    total++;
                }
            }
        }
        return total;
    }

    private void openCell(Graphics gra, int[][] list) {
        //通过List数组打开需要打开的格子
        for(int i = 0; i< array.length; i++) {
            for(int j = 0; j < array.length; j ++) {
                gra.setColor(Color.pink);
                if(list[i][j] == 1) {
                    gra.fillRect(50 + j * 50, 70 + i * 50, 50-1, 50-1);
                    gra.setColor(Color.black);
                    gra.setFont(new Font("楷体", Font.BOLD, 20));
                    if(array[i][j] != 0) {
                        gra.drawString(array[i][j]+"", 50 + j * 50 + 25, 70 + j * 50 + 25);
                    }
                }else if(list[i][j] == 2) {
                    gra.fillRect(50 + j * 50, 70 + i * 50, 50-1, 50-1);
                    gra.setColor(Color.cyan);
                    gra.setFont(new Font("楷体", Font.BOLD, 20));
                    gra.drawString(array[i][j]+"", 50 + j * 50 + 25, 70 + j * 50 + 25);
                }else if(list[i][j] == 3) {
                    gra.fillRect(50 + j * 50, 70 + i * 50, 50-1, 50-1);
                    gra.setColor(Color.blue);
                    gra.setFont(new Font("楷体", Font.BOLD, 20));
                    gra.drawString(array[i][j]+"", 50 + j * 50 + 25, 70 + j * 50 + 25);
                }else if(list[i][j] == 4) {
                    gra.fillRect(50 + j * 50, 70 + i * 50, 50-1, 50-1);
                    gra.setColor(Color.red);
                    gra.setFont(new Font("楷体", Font.BOLD, 20));
                    gra.drawString(array[i][j]+"", 50 + j * 50 + 25, 70 + j * 50 + 25);
                }else if(list[i][j] == 5) {
                    gra.fillRect(50 + j * 50, 70 + i * 50, 50-1, 50-1);
                    gra.setColor(Color.GREEN);
                    gra.setFont(new Font("楷体", Font.BOLD, 20));
                    gra.drawString(array[i][j]+"", 50 + j * 50 + 25, 70 + j * 50 + 25);
                }else if(list[i][j] == 6) {
                    gra.fillRect(50 + j * 50, 70 + i * 50, 50-1, 50-1);
                    gra.setColor(Color.green);
                    gra.setFont(new Font("楷体", Font.BOLD, 20));
                    gra.drawString(array[i][j]+"", 50 + j * 50 + 25, 70 + j * 50 + 25);
                }else if(list[i][j] == 7) {
                    gra.fillRect(50 + j * 50, 70 + i * 50, 50-1, 50-1);
                    gra.setColor(Color.magenta);
                    gra.setFont(new Font("楷体", Font.BOLD, 20));
                    gra.drawString(array[i][j]+"", 50 + j * 50 + 25, 70 + j * 50 + 25);
                }else if(list[i][j] == 8) {
                    gra.fillRect(50 + j * 50, 70 + i * 50, 50-1, 50-1);
                    gra.setColor(Color.orange);
                    gra.setFont(new Font("楷体", Font.BOLD, 20));
                    gra.drawString(array[i][j]+"", 50 + j * 50 + 25, 70 + j * 50 + 25);
                }else if(list[i][j] == 10) {
                    g2d.setFont(new Font("TimesRoman", Font.ITALIC, 60));
                    showAllMine(g2d);
                    g2d.setColor(Color.ORANGE);
                    g2d.drawString("Game Over", 125, 300);
                }

            }
        }
    }

    //踩到雷了，展示所有的雷
    private void showAllMine(Graphics2D g2d) {
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[i].length; j++) {
                if(array[i][j] == 10) {
                    g2d.drawImage(img.getImage(), 50 + j * 50, 70 + i * 50, mf);
                }
            }
        }
    }

    private void sweepMine(int[][] array, int row, int col) {
        //判断是否已经被点击过，是否被标记，以及是否越界
        if((isDignOn(list, row, col)) == false && row >= 0 
                && row < array.length && col >= 0 && col < array[0].length ) {
            //如果格子没有被打开，则设置这个格子为1
            setDign(list, row, col);
            mf.score += array[row][col] * 20;
            //判断是否有雷，如果没有 ， 则递归的进行展示相邻的格子
            if(array[row][col] == 0) {
                for(int i = 1; i <= 1; i ++) {
                    for(int j = 1; j <=1; j++) {
                        tempx = row + i;
                        tempy = col + j;
                        if(tempx >= 0 && tempx < array.length &&
                                tempy >= 0 && tempy < array.length) {
                            sweepMine(array, row+i, col+j);
                        }
                    }
                }
            }
            
        }
    }

    private void setDign(int[][] list, int row, int col) {
        list[row][col] = 1;
    }

    //判断某个位置是否已经Visited
    private boolean isDignOn(int[][] list, int row, int col) {
        return list[row][col] == 1;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //如果点击了new Game, 则开始新游戏
        if(e.getX() > 160 && e.getX() < 320 && e.getY() > 550 && e.getY() < 600) {
            clearData(array, list);
            mf.produceData();   //  产生新数据
            mf.repaint();   //重绘面板
            mf.addMouseListener(this); //重新添加监听器
            count = 9*9;    //回复count的数值
        }
    }

    private void clearData(int[][] array, int[][] list) {
        for (int i = 0; i <array.length; i++ ) {
            for(int j = 0; j < array[i].length; j++) {
                array[i][j] = 0;
                list[i][j] = 0;
            }
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
