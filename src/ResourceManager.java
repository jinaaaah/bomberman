import processing.core.PApplet;
import processing.core.PImage;

public class ResourceManager {

    private PImage image;
    private PImage[] images;

    private PImage blockImage;
    private PImage effectImage;
    private PImage itemImage;
    private PImage moveImage;
    private PImage stayImage;

    public ResourceManager(PApplet pApplet) {
        blockImage = pApplet.loadImage("img/bomberman-block.png");
        effectImage = pApplet.loadImage("img/bomberman-effect.png");
        itemImage = pApplet.loadImage("img/bomberman-items.png");
        moveImage = pApplet.loadImage("img/bomberman-movement.png");
        stayImage = pApplet.loadImage("img/bomberman-stay.png");
    }

    public PImage loadBlockImage() {
        image = blockImage;
        return image;
    }

    public PImage[] loadBombImage() {
        images = new PImage[3];
        for (int k = 0; k < 3; k++) {
            images[k] = effectImage.get(24 * k, 0, 24, 24);
        }

        return images;
    }

    public PImage[] loadMoveImage() {
        images = new PImage[20];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                images[j * 5 + i] = moveImage.get(20 * i, 32 * j, 20, 32);
            }
        }

        return images;
    }

    public PImage[] loadStayImage() {
        images = new PImage[12];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                images[j * 3 + i] = stayImage.get(20 * i, 32 * j, 20, 32);
            }
        }

        return images;
    }

    public PImage getItemImage(int i) {


        if (i == 1) {
            image = itemImage.get(96, 0, 24, 24);
        } else if (i == 2) {
            image = itemImage.get(48, 0, 24, 24);
        } else if (i == 3) {
            image = itemImage.get(0, 0, 24, 24);
        }
        image.resize(40, 40);
        return image;
    }

    public PImage[] loadFlameImage(){
        images = new PImage[20];
        for(int i = 0 ; i < 4; i ++){
            for( int j = 0 ; j < 5 ; j ++){
                images[j * 4  + i] = effectImage.get(48*j, 96-(24*i), 24, 24);
                images[j * 4  + i].resize(40, 40);
            }
        }

        return images;
    }

}
