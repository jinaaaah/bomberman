import processing.core.PApplet;
import processing.core.PImage;

public class Player extends RenderObject {

    private PImage[] moveImages;
    private PImage[] stayImages;
    private boolean isMove;
    private float speedRate;
    private int direction;

    private int bombPower;
    private int bombMax;
    private int bombNum;


    public Player(PApplet pApplet, float pPosX, float pPosY) {
        super(pApplet, pPosX, pPosY);

        bombNum = 0;
        bombPower = 1;
        bombMax = 1;
        isMove = false;
        direction = 0;
        speedRate = 2.0f;
        moveImages = resourceManager.loadMoveImage();
        stayImages = resourceManager.loadStayImage();
    }

    public void getItem(int type) {
        if (type == 3) {
            System.out.println("powerup");
            setBombPower();
        }
        if (type == 2) {
            System.out.println("bombup");
            setBombMax();
        }
        if (type == 1) {
            System.out.println("speedup");
            setSpeedRate();
        }
    }


    private void setSpeedRate() {
        this.speedRate += 1;
    }

    private void setBombPower() {
        this.bombPower++;
    }

    private void setBombMax() {
        this.bombMax++;
    }

    public int getBombPower() {
        return bombPower;
    }

    public void expireBomb() {
        bombNum--;
    }

    public boolean isOverMax() {
        if (bombNum >= bombMax) {
            return false;
        }
        bombNum++;
        return true;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void isMoved(boolean isMove) {
        this.isMove = isMove;
    }

    public boolean update(int[][] mapArray) {

        if (isMove) {
            if (checkBlock(mapArray) != -1) {
                switch (direction) {
                    case 0:
                        pPosY = pPosY - speedRate;
                        break;
                    case 1:
                        pPosY = pPosY + speedRate;
                        break;
                    case 2:
                        pPosX = pPosX + speedRate;
                        break;
                    case 3:
                        pPosX = pPosX - speedRate;
                        break;
                }
                if (checkBlock(mapArray) == 1) {
                    return true;
                }
            }

        }
        draw();
        return false;
    }

    private int checkBlock(int[][] mapArray) {
        int x = 0, x2 = 0, y = 0, y2 = 0;

        switch (direction) {
            case 0:
                x = (int) ((pPosX + 20) / Constants.BLOCK_SIZE);
                x2 = (int) ((pPosX) / Constants.BLOCK_SIZE);
                y = (int) (pPosY) / Constants.BLOCK_SIZE;
                y2 = y;
                break;
            case 1:
                x = (int) ((pPosX + 20) / Constants.BLOCK_SIZE);
                x2 = (int) ((pPosX) / Constants.BLOCK_SIZE);
                y = (int) (pPosY + 32) / Constants.BLOCK_SIZE;
                y2 = y;
                break;
            case 2:
                x = (int) ((pPosX + 20) / Constants.BLOCK_SIZE);
                x2 = x;
                y = (int) pPosY / Constants.BLOCK_SIZE;
                y2 = (int) (pPosY + 32) / Constants.BLOCK_SIZE;
                break;
            case 3:
                x = (int) ((pPosX) / Constants.BLOCK_SIZE);
                x2 = x;
                y = (int) pPosY / Constants.BLOCK_SIZE;
                y2 = (int) (pPosY + 32) / Constants.BLOCK_SIZE;
                break;
        }

        if ((mapArray[x][y] == 0 && mapArray[x2][y2] == 0) ||
                (mapArray[x][y] == 6 && mapArray[x2][y2] == 6)) {
            return 0;
        }
        if ((mapArray[x][y] == 3 || mapArray[x2][y2] == 3)) {
            return 1;
        }

        return -1;
    }

    @Override
    public void draw() {
        tick++;

        int moveType;
        PImage[] pImages;

        if (isMove) {
            moveType = 5;
            pImages = moveImages;
        } else {
            moveType = 3;
            pImages = stayImages;
        }

        switch (direction) {
            case 1:
                pApplet.image(pImages[tick / 10 % moveType], pPosX, pPosY);
                break;
            case 2:
                pApplet.image(pImages[tick / 10 % moveType + moveType], pPosX, pPosY);
                break;
            case 0:
                pApplet.image(pImages[tick / 10 % moveType + moveType * 2], pPosX, pPosY);
                break;
            case 3:
                pApplet.image(pImages[tick / 10 % moveType + moveType * 3], pPosX, pPosY);
                break;
        }

    }
}
