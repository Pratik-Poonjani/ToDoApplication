package Beginner.ToDoApplication;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class AppFrame extends JFrame {

    private final List list;
    private final JButton newTask;
    private final JButton clear;


    AppFrame() {
        this.setSize(400, 700);
        this.setLocationRelativeTo(null); // fix size in CENTER
        this.setVisible(true);

        TitleBar title = new TitleBar();
        list = new List();
        ButtonPanel btnPanel = new ButtonPanel();
        this.add(title, BorderLayout.NORTH);
        this.add(btnPanel, BorderLayout.SOUTH);
        this.add(list, BorderLayout.CENTER);

        newTask = btnPanel.getNewTask();
        clear = btnPanel.getClear();

        addListeners();
    }


    public void addListeners()
    {
        newTask.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                Task task = new Task();
                list.add(task);
                list.updateNumbers();

                task.getDone().addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mousePressed(MouseEvent e)
                    {
                        task.changeState();
                        list.updateNumbers();
                        revalidate();
                    }
                });
            }
        });

        clear.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                list.removeCompletedTasks();
                repaint();
            }
        });
    }
}

class ButtonPanel extends JPanel {
    JButton addTask;
    JButton clear;
    Border emptyBorder = BorderFactory.createEmptyBorder();

    ButtonPanel() {
        this.setPreferredSize(new Dimension(400, 60));
        this.setBackground(new Color(255, 255, 255));

        clear = new JButton(" Clear Completed Task ");
        clear.setBorder(emptyBorder);
        clear.setFont(new Font("SOLID", Font.PLAIN, 20));
        clear.setBackground(Color.white);
        this.add(clear);

        this.add(Box.createHorizontalStrut(60));//Space between buttons

        addTask = new JButton(" + ");
        addTask.setBorder(emptyBorder);
        addTask.setFont(new Font("SOLID", Font.BOLD, 40));
        addTask.setBackground(new Color(55, 141, 255));
        this.add(addTask);
    }

    public JButton getNewTask() {
        return addTask;
    }

    public JButton getClear() {
        return clear;
    }
}

class TitleBar extends JPanel {
    TitleBar() {
        this.setPreferredSize(new Dimension(400, 60));
        this.setBackground(new Color(55, 141, 255));

        JLabel titleText = new JLabel(" To-Do List ");
        titleText.setPreferredSize(new Dimension(200, 40));
        titleText.setFont(new Font("MONOSPACED", Font.BOLD, 25));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleText);
    }
}

class List  extends JPanel {

    List()
    {
        GridLayout layout = new GridLayout(10,1);
        layout.setVgap(5);

        this.setLayout(layout);
        this.setBackground(new Color(255,255,255));
        this.setPreferredSize(new Dimension(400,560));
    }
    public void updateNumbers()
    {
        Component[] listItems = this.getComponents();

        for(int i = 0;i<listItems.length;i++)
        {
            if(listItems[i] instanceof Task)
            {
                ((Task)listItems[i]).changeIndex(i+1);
            }
        }
    }

    public void removeCompletedTasks()
    {
        for(Component c : getComponents())
        {
            if(c instanceof Task)
            {
                if(((Task)c).getState())
                {
                    remove(c);
                    updateNumbers();
                }
            }
        }
    }
}

class Task extends JPanel {

    JLabel index;
    JTextField taskName;
    JButton done;

    private boolean checked;

    Task() {
        this.setPreferredSize(new Dimension(40, 20));
        this.setLayout(new BorderLayout());

        checked = false;

        index = new JLabel("");
        index.setPreferredSize(new Dimension(20, 20));
        index.setHorizontalAlignment(JLabel.CENTER);
        this.add(index, BorderLayout.WEST);

        taskName = new JTextField("");
        taskName.setFont(new Font("SOLID",Font.PLAIN, 20));
        taskName.setBorder(BorderFactory.createEmptyBorder());
        taskName.setBackground(new Color(220,220,220));

        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("Completed");
        done.setPreferredSize(new Dimension(80, 20));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setFocusPainted(false);

        this.add(done, BorderLayout.EAST);
    }
    public void changeIndex(int num)
    {
        this.index.setText(num+".");
        this.revalidate();
    }

    public JButton getDone()  { return done; }

    public boolean getState() { return checked; }

    public void changeState()
    {
        this.setBackground(Color.orange);
        taskName.setBackground(Color.orange);
        checked = true;
        revalidate();
    }
}

class ToDoList {
    public static void main(String[] args) {
        new AppFrame();
    }
}