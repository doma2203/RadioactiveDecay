package Gui;


import RadioactiveDecay.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;


public class Window extends JFrame
{
    private JPanel panelMain;
    private Canvas canvas;
    private Chart chart;
    private JSpinner spinner;
    private JMenuBar menuBar;
    private Experiment experiment;
    private JPanel panel; //to jest do .form jedynie

    public Window()
    {
        super("Rozpad promieniotwórczy");
        init();
    }

    private void init()
    {
        createLayout();
        createMenuBar();
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(1000, 700);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void createLayout()
    {
        canvas = new Canvas();
        chart = new Chart();
        panelMain = new JPanel();

        panelMain.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.insets =  new Insets(5,5,5,5);
        panelMain.add(chart, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets =  new Insets(5,5,5,5);
        panelMain.add(spinner, c);

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.insets =  new Insets(5,5,5,5);
        panelMain.add(canvas, c);
    }

    private void createMenuBar()
    {
        menuBar = new JMenuBar();
        JMenu start = new JMenu("Start");
        menuBar.add(start);
        JMenu pauza = new JMenu("Pauza");
        menuBar.add(pauza);
        JMenu kontynuacja = new JMenu("Kontynuacja");
        menuBar.add(kontynuacja);
        JMenu koniec = new JMenu("Koniec");
        menuBar.add(koniec);
        setJMenuBar(menuBar);

        koniec.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                System.exit(0);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
    }

    public class Canvas extends JPanel
    {
        public Canvas()
        {
            setBackground(Color.BLUE);
            this.setPreferredSize(new Dimension(500, 300));
            setOpaque(true);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Color.YELLOW);
            g.fillOval(1, 1, 10, 10);
        }
    }

    public class Chart extends JPanel
    {
        public Chart()
        {
            setBackground(Color.magenta);
            this.setPreferredSize(new Dimension(500, 300));
            setOpaque(true);
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.fillOval(1, 1, 10, 10);
        }
    }
}
