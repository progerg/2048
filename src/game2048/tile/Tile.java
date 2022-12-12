package game2048.tile;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

public class Tile {
    int x, y, width, height, number;
    Color color;

    public Tile(int x, int y, int width, int height, int number) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = getColor(number);
        this.number = number;
    }

    public void setNumber(int number) {
        this.number = number;
        this.color = getColor(number);
    }

    public void paintItself(Graphics g) {
        // g.drawRoundRect(x, y, width, height, 10, 10);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 10, 10);
        if (number != 0) {
            Graphics2D g2 = (Graphics2D) g;
            Font font = new Font(null, Font.BOLD, 20);
            String text = Integer.toString(number);
            FontRenderContext context = g2.getFontRenderContext();
            int textWidth = (int) font.getStringBounds(text, context).getWidth();
            LineMetrics ln = font.getLineMetrics(text, context);
            int textHeight = (int) (ln.getAscent() + ln.getDescent());
            int x1 = x + (width - textWidth)/2;
            int y1 = (int)(y + (height + textHeight)/2 - ln.getDescent());
            g2.setColor(Color.BLACK);
            g2.setFont(font);
            g2.drawString(text, x1, y1);
        }
    }

    public int getNumber() {
        return number;
    }

    public Color getColor(int number) {
        return switch (number) {
            case (0) -> new Color(0xCCC3B3);
            case (2) -> new Color(0xEEE4DA);
            case (4) -> new Color(0xEDE0C8);
            case (8) -> new Color(0xF2B179);
            case (16) -> new Color(0xF49769);
            case (32) -> new Color(0xF67C5F);
            case (64) -> new Color(0xF2603E);
            case (128) -> new Color(0xEACF76);
            case (256) -> new Color(0xEBCA65);
            case (512) -> new Color(0xEBC658);
            case (1024) -> new Color(0xE6C356);
            case (2048) -> new Color(0xE9BD4F);
            default -> Color.GRAY;
        };
    }

    @Override
    public String toString() {
        return Integer.toString(this.number);
    }
}
