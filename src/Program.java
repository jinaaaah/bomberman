import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Program extends PApplet {

    private int[][] mapArray;
    private List<Block> blocks;
    private List<Bomb> bombs;

    private Player player1;
    private Player player2;

    @Override
    public void keyPressed() {

        if (key == CODED) {
            if (keyCode == UP) {
                player1.isMoved(true);
                player1.setDirection(Constants.UP_DIR);
            }
            if (keyCode == DOWN) {
                player1.isMoved(true);
                player1.setDirection(Constants.DOWN_DIR);
            }
            if (keyCode == RIGHT) {
                player1.isMoved(true);
                player1.setDirection(Constants.RIGHT_DIR);
            }
            if (keyCode == LEFT) {
                player1.isMoved(true);
                player1.setDirection(Constants.LEFT_DIR);
            }
        }

        if (key == ' ') {
            Bomb bomb = null;
            if (player1.isOverMax()) {
                bomb = new Bomb(this, Math.round(player1.getpPosX()), Math.round(player1.getpPosY()), player1);
                mapArray[Math.round(bomb.getpPosX()) / Constants.BLOCK_SIZE][Math.round(bomb.getpPosY()) / Constants.BLOCK_SIZE] = Constants.BOMB;
            }
            if (bomb != null) {
                bombs.add(bomb);
            }
        }

        if(key == 'r'){
            reset();
        }

    }

    private void checkBlock(int x, int y) {
        Block block = null;
        if (mapArray[x][y] == Constants.ITEM_BLOCK) {

            for (Block b : blocks) {
                if ((int) b.getpPosX() == x * Constants.BLOCK_SIZE && (int) b.getpPosY() == y * Constants.BLOCK_SIZE) {
                    block = b;
                    mapArray[x][y] = Constants.EMPTY_BLOCK;
                }
            }
            if (block != null)
                blocks.remove(block);
        }
        if (mapArray[x][y] == Constants.SOFT_STONE) {
            for (Block b : blocks) {
                if ((int) b.getpPosX() == x * Constants.BLOCK_SIZE && (int) b.getpPosY() == y * Constants.BLOCK_SIZE) {
                    b.itemReveal(0);
                    mapArray[x][y] = Constants.ITEM_BLOCK;
                }
            }

        }

    }

    private void eatItem(Player player, int x, int y) {
        Block block = null;
        for (Block b : blocks) {
            if ((int) b.getpPosX() == x && (int) b.getpPosY() == y) {
                block = b;
                System.out.println("getItem");
                player.getItem(b.itemReveal(1));
                mapArray[x / Constants.BLOCK_SIZE][y / Constants.BLOCK_SIZE] = Constants.EMPTY_BLOCK;
            }
        }
        if (block != null)
            blocks.remove(block);
    }

    private void checkPlayer(int x, int y){
        if(Math.round(player1.getpPosX()) == x * Constants.BLOCK_SIZE  && Math.round(player1.getpPosY()) == y* Constants.BLOCK_SIZE){
            reset();
        }

    }

    private void checkBombEffect( int i, int power, int posX, int posY) {

        if (i < power) {
            if (posX + 1 < 18) {
                if (mapArray[posX + 1][posY] != 0) {
                    checkBlock(posX + 1, posY);
                    checkPlayer(posX + 1, posY);
                } else {
                    i++;
                    checkBombEffect( i, power, posX + 1, posY);
                }
            }
            if (posX - 1 > 2) {
                if (mapArray[posX - 1][posY] != 0) {
                    checkBlock(posX - 1, posY);
                    checkPlayer(posX - 1, posY);
                } else {
                    i++;
                    checkBombEffect( i, power, posX - 1, posY);
                }
            }
            if (posY + 1 < 13) {
                if (mapArray[posX][posY + 1] != 0) {
                    checkBlock( posX, posY + 1);
                    checkPlayer(posX, posY + 1);
                } else {
                    i++;
                    checkBombEffect(i, power, posX, posY + 1);
                }
            }
            if (posY - 1 < 2) {
                if (mapArray[posX][posY - 1] != 0) {
                    checkBlock(posX, posY - 1);
                    checkPlayer(posX, posY - 1);
                } else {
                    i++;
                    checkBombEffect( i, power, posX, posY - 1);
                }
            }
        }


    }

    private void checkBombTime() {
        ArrayList<Bomb> tempBomb = new ArrayList<>();
        for (Bomb b : bombs) {
            if (b != null)
                if (b.isExpire(mapArray)) {
                    checkBombEffect(0, b.getBombPower(), Math.round(b.getpPosX()) / Constants.BLOCK_SIZE, Math.round(b.getpPosY()) / Constants.BLOCK_SIZE);
                    mapArray[Math.round(b.getpPosX()) / Constants.BLOCK_SIZE][Math.round(b.getpPosY()) / Constants.BLOCK_SIZE] = Constants.EMPTY_BLOCK;
                    tempBomb.add(b);

                }
        }
        bombs.removeAll(tempBomb);

    }


    @Override
    public void keyReleased() {
        player1.isMoved(false);
    }

    @Override
    public void draw() {
        background(0);

        for (Block b : blocks) {
            b.draw();
        }

        for (Bomb bomb : bombs) {
            bomb.draw();
        }

        checkBombTime();
        if (player1.update(mapArray)) {
            eatItem(player1, Math.round(player1.getpPosX()), Math.round(player1.getpPosY()));
        }

    }

    private void reset(){
        settings();
        draw();
    }
    @Override
    public void settings() {
        size(800, 600);

        player1 = new Player(this, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);
        player2 = new Player(this, Constants.BLOCK_SIZE * 18, Constants.BLOCK_SIZE * 13);


        mapArray = new int[Constants.WIDTH][Constants.HEIGHT];
        blocks = new ArrayList<>();
        bombs = new ArrayList<>();
        Block block;

        Random random = new Random();

        for (int i = 0; i < Constants.WIDTH; i++) {
            for (int j = 0; j < Constants.HEIGHT; j++) {
                if (!(i == 1 && j == 1) && !(i == 1 && j == 2) && !(i == 2 && j == 1)
                        && !(i == 18 && j == 13) && !(i == 18 && j == 12) && !(i == 17 && j == 13)) {
                    if (i > 0 && i < 19 && j > 0 && j < 14) {

                        if (random.nextInt(13) % 8 == 0) {
                            mapArray[i][j] = Constants.HARD_STONE;
                            block = new HardBlock(this, Constants.BLOCK_SIZE * i, Constants.BLOCK_SIZE * j);
                            blocks.add(block);
                        }
                        if (random.nextInt(13) % 8 == 0) {
                            mapArray[i][j] = Constants.SOFT_STONE;
                            block = new SoftBlock(this, Constants.BLOCK_SIZE * i, Constants.BLOCK_SIZE * j);
                            blocks.add(block);
                        }

                    } else {

                        mapArray[i][j] = Constants.HARD_STONE;
                        block = new HardBlock(this, Constants.BLOCK_SIZE * i, Constants.BLOCK_SIZE * j);
                        blocks.add(block);
                    }
                }
            }
        }


    }

    @Override
    public void setup() {
        background(0);
    }

    public static void main(String[] args) {
        PApplet.main("Program");
    }
}
