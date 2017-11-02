import processing.core.PApplet;
import processing.core.PImage;

public class Bomb extends RenderObject {

    private PImage[] pImages;
    private int bombPower;
    private long putTime;
    private Player player;

    public Bomb(PApplet pApplet, float pPosX, float pPosY, Player player) {
        super(pApplet, pPosX, pPosY);
        this.player = player;
        this.bombPower = player.getBombPower();
        this.putTime = System.currentTimeMillis();
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isExpire(int[][] mapArray) {
        boolean timeout = System.currentTimeMillis() - this.putTime > 3400;
        boolean flameTime = System.currentTimeMillis() - this.putTime > 3000;
        if (timeout) {
            this.player.expireBomb();
        }
        if(flameTime){
            drawFlame(mapArray);
        }
        return timeout;
    }

    public int getBombPower() {
        return bombPower;
    }

    @Override
    public void draw() {
        tick++;
        pImages = resourceManager.loadBombImage();
        pApplet.image(pImages[tick / 10 % 3], pPosX, pPosY);
    }

    private void drawFlame(int[][] mapArray){
        tick++;
        pImages = resourceManager.loadFlameImage();

        pApplet.image(pImages[tick / 10 % 4 ], pPosX,pPosY);
        for(int i = 1; i <= bombPower; i ++){
            if(mapArray[Math.round(pPosX/Constants.BLOCK_SIZE) -i][Math.round(pPosY/Constants.BLOCK_SIZE)] == 1){
                break;
            }
            pApplet.image(pImages[tick / 10 % 4 + 4], pPosX - Constants.BLOCK_SIZE * i, pPosY);
        }
        for(int i = 1; i <= bombPower; i ++){
            if(mapArray[Math.round(pPosX/Constants.BLOCK_SIZE) + i][Math.round(pPosY/Constants.BLOCK_SIZE)] == 1){
                break;
            }
            pApplet.image(pImages[tick / 10 % 4 + 8], pPosX + Constants.BLOCK_SIZE * i, pPosY);
        }
        for(int i = 1; i <= bombPower; i ++){
            if(mapArray[Math.round(pPosX/Constants.BLOCK_SIZE)][Math.round(pPosY/Constants.BLOCK_SIZE) - i] == 1){
                break;
            }
            pApplet.image(pImages[tick / 10 % 4 + 12], pPosX , pPosY- Constants.BLOCK_SIZE * i);
        }
        for(int i = 1; i <= bombPower; i ++){
            if(mapArray[Math.round(pPosX/Constants.BLOCK_SIZE)][Math.round(pPosY/Constants.BLOCK_SIZE) + i] == 1){
                break;
            }
            pApplet.image(pImages[tick / 10 % 4 + 16], pPosX , pPosY + Constants.BLOCK_SIZE * i);
        }



    }
}
