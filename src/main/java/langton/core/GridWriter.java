package langton.core;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class GridWriter {

    public void export(Grid grid) throws IOException {
        Vector2 min = grid.getMinArea();
        Vector2 max = grid.getMaxArea();
        Vector2 offset = grid.getMinArea();

        long width = max.x - min.x;
        long height = max.y - min.y;

        BufferedImage bi = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();

        Color black = Color.black;
        Color white = Color.white;
        Vector2 currentCoord = new Vector2();

        graphics.setColor(Color.red);
        graphics.fillRect(0, 0, (int) width, (int) height);

        for (long y = min.y; y < height; y++) {
            currentCoord.y = y;
            for (long x = min.x; x < width; x++) {
                currentCoord.x = x;
                graphics.setColor(grid.isCellSet(currentCoord) ? black : white);
                graphics.fillRect((int)(x - offset.x), (int)(y - offset.y), 1, 1);
            }
        }
        graphics.dispose();

        File outputFile = new File("saved.png");
        ImageIO.write(bi, "png", outputFile);
    }
}
