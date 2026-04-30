import auditory.Auditory;
import employee.Employee;
import employee.Laborant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationWindow extends JFrame {

    private MapPanel animationPanel;
    private JButton toggleButton;

    public AnimationWindow(List<Employee> employees, List<Auditory> auditories) {
        setTitle("Animated University Map");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        animationPanel = new MapPanel(employees, auditories);
        add(animationPanel, BorderLayout.CENTER);

        toggleButton = new JButton("Start / Pause");
        toggleButton.addActionListener(e -> animationPanel.toggleAnimation());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(toggleButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

class MapPanel extends JPanel implements ActionListener {
    private Timer timer;
    
    private class RoomGUI {
        Auditory auditory;
        int x, y, width, height;
        RoomGUI(Auditory a, int x, int y, int w, int h) {
            this.auditory = a; this.x = x; this.y = y; this.width = w; this.height = h;
        }
    }

    private class EmployeeGUI {
        Employee employee;
        double x, y; 
        double startX;
        int targetIndex = 0; 
        Color color;
        boolean headingToMeetingPoint = false;

        EmployeeGUI(Employee e, int startX, int startY, Color c) {
            this.employee = e; 
            this.x = startX; 
            this.y = startY; 
            this.startX = startX;
            this.color = c;
        }
    }

    private List<RoomGUI> rooms = new ArrayList<>();
    private List<EmployeeGUI> actors = new ArrayList<>();
    private Map<Integer, RoomGUI> roomMap = new HashMap<>();

    public MapPanel(List<Employee> employees, List<Auditory> auditories) {
        setBackground(Color.WHITE);

        int currentX = 50;
        for (Auditory a : auditories) {
            RoomGUI room = new RoomGUI(a, currentX, 50, 150, 80);
            rooms.add(room);
            roomMap.put(a.getId(), room);
            currentX += 200; 
        }

        int startX = 100;
        for (Employee e : employees) {
            Color color = (e instanceof Laborant) ? new Color(220, 50, 50) : new Color(50, 100, 220);
            actors.add(new EmployeeGUI(e, startX, 250, color));
            startX += 150;
        }

        timer = new Timer(20, this);
    }

    public void toggleAnimation() {
        if (timer.isRunning()) timer.stop();
        else timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (RoomGUI r : rooms) {
            g2.setColor(new Color(230, 230, 230));
            g2.fillRect(r.x, r.y, r.width, r.height);
            g2.setColor(Color.DARK_GRAY);
            g2.drawRect(r.x, r.y, r.width, r.height);
            g2.setColor(Color.BLACK);
            g2.drawString(r.auditory.getForSubject(), r.x + 10, r.y + 30);
            g2.drawString("Capacity: " + r.auditory.getCapacity(), r.x + 10, r.y + 50);
        }

        for (EmployeeGUI actor : actors) {
            g2.setColor(actor.color);
            g2.fillOval((int)actor.x, (int)actor.y, 20, 20);
            g2.setColor(Color.BLACK);
            g2.drawString(actor.employee.getName(), (int)actor.x - 10, (int)actor.y + 35);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (EmployeeGUI actor : actors) {
            Auditory[] assigned = actor.employee.getAuditories();
            if (assigned == null || assigned.length == 0) continue; 

            double destX, destY;

            if (actor.headingToMeetingPoint) {
                destX = actor.startX;
                destY = 250;
            } else {
                Auditory targetAuditory = assigned[actor.targetIndex];
                RoomGUI targetRoom = roomMap.get(targetAuditory.getId());
                
                if (targetRoom != null) {
                    destX = targetRoom.x + targetRoom.width / 2.0 + 20;
                    destY = targetRoom.y + targetRoom.height / 2.0 - 10;
                } else {
                    continue;
                }
            }

            double dx = destX - actor.x;
            double dy = destY - actor.y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > 2) {
                actor.x += (dx / distance) * 2;
                actor.y += (dy / distance) * 2;
            } else {
                if (actor.headingToMeetingPoint) {
                    actor.headingToMeetingPoint = false;
                    actor.targetIndex = (actor.targetIndex + 1) % assigned.length;
                } else {
                    actor.headingToMeetingPoint = true;
                }
            }
        }
        repaint(); 
    }
}