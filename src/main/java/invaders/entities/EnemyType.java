package invaders.entities;

import invaders.logic.Damagable;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Animator;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;

import java.io.File;


public enum EnemyType {
    TYPE1(10, "enemy1.png"),
    TYPE2(20, "enemy2.png"),
    TYPE3(30, "enemy3.png");

    private final int value;
    private final String imageName;

    EnemyType(int value, String imageName) {
        this.value = value;
        this.imageName = imageName;
    }

    public int getValue() {
        return value;
    }

    public String getImageName() {
        return imageName;
    }
}


