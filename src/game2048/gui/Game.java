package game2048.gui;

import game2048.logic.Logic;
import game2048.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.util.ArrayList;


public class Game extends JFrame implements KeyListener {
    public static final int WIDTH = 500, HEIGHT = 600;
    private final Tile[][] tiles = new Tile[4][4];
    private ArrayList<ArrayList<Integer>> numbers = new ArrayList<>(4);
    private boolean win = false;

    public Game() {
        fillEmptyTiles();
        setUndecorated(true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0xBBADA0));
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        JPanel panel = new JPanel();
        JButton button = new JButton("ЗАКРЫТЬ");
        button.setSize(200, 50);
        button.setLocation(200, 530);
        button.addActionListener(e -> System.exit(0));
        panel.add(button);
        this.add(panel, BorderLayout.SOUTH);
    }

    public void keyPressed(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}


    public void keyReleased(KeyEvent e) {
        if (numbers == null) {
            return;
        }
        switch (e.getKeyCode()) {
            case (KeyEvent.VK_RIGHT) -> numbers = Logic.slideRight(numbers);
            case (KeyEvent.VK_LEFT) -> numbers = Logic.slideLeft(numbers);
            case (KeyEvent.VK_DOWN) -> numbers = Logic.slideDown(numbers);
            case (KeyEvent.VK_UP) -> numbers = Logic.slideUp(numbers);
        }
        repaint();
    }

    public void fillEmptyTiles() {
        int x;
        int y = 0;
        for (int i = 0; i < tiles.length; i++) {
            y += 20;
            x = 0;
            for (int j = 0; j < tiles[i].length; j++) {
                x += 20;
                tiles[i][j] = new Tile(x + 100 * j, y + 100 * i, 100, 100, 0);
            }
        }
//        tiles[0][1] = new Tile(40 + 100 * 1, 20 + 100 * 0, 100, 100, 1024);
//        tiles[0][2] = new Tile(60 + 100 * 2, 20 + 100 * 0, 100, 100, 1024);
        for(int i = 0; i < 4; ++i) {
            ArrayList<Integer> list = new ArrayList<>(4);
            numbers.add(list);
        }
        tilesToNumbers();
        numbers = Logic.setTileWithNumberTwo(numbers);
    }

    public void tilesToNumbers() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                numbers.get(i).add(j, tiles[i][j].getNumber());
            }
        }

    }

    public void numbersToTiles(Graphics g) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (numbers.get(i).get(j) == 2048) {
                    win = true;
                }
                tiles[i][j].setNumber(numbers.get(i).get(j));
                tiles[i][j].paintItself(g);
            }
        }
    }

    public void writeEndText(Graphics g, String text) {
        Graphics2D g2 = (Graphics2D) g;
        Font font = new Font(null, Font.BOLD, 50);
        FontRenderContext context = g2.getFontRenderContext();
        int textWidth = (int) font.getStringBounds(text, context).getWidth();
        LineMetrics ln = font.getLineMetrics(text, context);
        int textHeight = (int) (ln.getAscent() + ln.getDescent());
        int x1 = (WIDTH - textWidth) / 2;
        int y1 = (int) ((HEIGHT + textHeight)/2 - ln.getDescent());
        g2.setColor(Color.BLACK);
        g2.setFont(font);
        g2.drawString(text, x1, y1);
    }

    public void paint(Graphics g) {
        super.paint(g);
        try {
            numbers = Logic.setTileWithNumberTwo(numbers);
            if (numbers == null) {
                writeEndText(g, "ОПОЗОРИЛСЯ!");
            }
            numbersToTiles(g);
            if (win) {
                writeEndText(g, "МОЛОДЕЦ!");
                numbers = null;
            }
        } catch (NullPointerException ignored) {}
    }
}
