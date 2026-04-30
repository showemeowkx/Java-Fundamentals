package conway;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class InteractiveLife extends JPanel {
    private int[][] grid;
    private static final int SIZE = 15; 
    private int generationCounter = 0;
    private Timer timer;

    public InteractiveLife(int width, int height) {
        this.grid = new int[height / SIZE][width / SIZE];
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / SIZE;
                int y = e.getY() / SIZE;
                
                if (y >= 0 && y < grid.length && x >= 0 && x < grid[0].length) {
                    grid[y][x] = (grid[y][x] == 1) ? 0 : 1;
                    repaint();
                }
            }
        });

        timer = new Timer(100, (ActionEvent e) -> {
            updateGrid();
            repaint();
        });
    }

    public void updateGrid() {
        int[][] newGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                newGrid[i][j] = applyRule(i, j);
            }
        }
        grid = newGrid;
        generationCounter++;
    }

    private int applyRule(int i, int j) {
        int west = 0, east = 0, north = 0, south = 0;
        int northwest = 0, northeast = 0, southwest = 0, southeast = 0;

        if (j < grid[i].length - 1) {
            east = grid[i][j + 1];
            if (i > 0) northeast = grid[i - 1][j + 1];
            if (i < grid.length - 1) southeast = grid[i + 1][j + 1];
        }
        if (j > 0) {
            west = grid[i][j - 1];
            if (i > 0) northwest = grid[i - 1][j - 1];
            if (i < grid.length - 1) southwest = grid[i + 1][j - 1];
        }
        if (i > 0) north = grid[i - 1][j];
        if (i < grid.length - 1) south = grid[i + 1][j];

        int sum = west + east + north + south + northwest + northeast + southwest + southeast;

        if (grid[i][j] == 1) {
            if (sum < 2) return 0;
            if (sum > 3) return 0;
        } else {
            if (sum == 3) return 1;
        }
        return grid[i][j];
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(grid[0].length * SIZE, grid.length * SIZE);
    }

    @Override
    protected void paintComponent(Graphics gg) {
        Graphics2D g = (Graphics2D) gg;
        super.paintComponent(g);
        
        g.setBackground(Color.WHITE);
        g.clearRect(0, 0, getWidth(), getHeight());
        
        g.setColor(new Color(230, 230, 230));
        for(int i=0; i<getWidth(); i+=SIZE) g.drawLine(i, 0, i, getHeight());
        for(int i=0; i<getHeight(); i+=SIZE) g.drawLine(0, i, getWidth(), i);

        g.setColor(Color.BLACK);
        g.drawString("Generation: " + generationCounter, 5, 15);

        g.setColor(Color.BLUE);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    g.fillRect(j * SIZE, i * SIZE, SIZE, SIZE);
                }
            }
        }
    }

    public void toggleSimulation() {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            timer.start();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Conway's Game of Life - Interactive");
        InteractiveLife panel = new InteractiveLife(600, 400);
        
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        JButton startStopButton = new JButton("Start / Pause");
        startStopButton.addActionListener(e -> panel.toggleSimulation());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startStopButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}