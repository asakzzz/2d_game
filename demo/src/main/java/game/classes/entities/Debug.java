package game.classes.entities;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Debug {

    public Rectangle getHitboxProjectile(ArrayList projectileList) {
        Iterator<Projectile> it = projectileList.iterator();

        while (it.hasNext()) {
            Projectile projectile = it.next();
            Rectangle projectileBox = new Rectangle(projectile.getWidth(), projectile.getHeight());
            projectileBox.setFill(Color.TRANSPARENT);
            projectileBox.setStroke(Color.RED);
            projectileBox.setStrokeWidth(2);
            return projectileBox;
        }
        return null;
    }

}
