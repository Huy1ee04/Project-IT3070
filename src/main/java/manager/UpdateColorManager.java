package manager;

import javafx.scene.paint.Color;

import javafx.scene.shape.Shape;



public class UpdateColorManager {
    public static <T extends Shape> void updateColor(T uiComponent, Color color){
        uiComponent.setFill(color);
    }
}
