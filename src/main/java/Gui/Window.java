package Gui;

import RadioactiveDecay.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame
{
    private JPanel panelMain;
    private Canvas canvas;
    private Chart chart;
    private JMenuBar menuBar;
    private Navigation navigation;
    private Control control;

    private Experiment ex;
    private int size;
    private Timer timer;

    public Window()
    {
        super("Rozpad promieniotwórczy");
        init();
    }

    private void init()
    {
        createItems();
        createLayout();
        createListeners();
        createMenuBar();
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(1000, 700);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void createItems()
    {
        canvas = new Canvas();
        chart = new Chart();
        panelMain = new JPanel();
        control = new Control();
        navigation = new Navigation();
    }

    private void createListeners()
    {

    }

    private void createLayout()
    {
        panelMain.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.ipadx = 5;
        c.ipady = 5;
        c.insets =  new Insets(5,5,5,5);
        panelMain.add(chart, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipadx = 5;
        c.ipady = 5;
        c.insets =  new Insets(5,5,5,5);
        panelMain.add(control, c);

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.ipadx = 5;
        c.ipady = 5;
        c.insets =  new Insets(5,5,5,5);
        panelMain.add(canvas, c);

        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipadx = 5;
        c.ipady = 5;
        c.insets =  new Insets(5,5,5,5);
        panelMain.add(navigation, c);
    }

    private void createMenuBar()
    {
        menuBar = new JMenuBar();
        JMenu start = new JMenu("Start");
        menuBar.add(start);
        JMenu pauza = new JMenu("Pauza");
        menuBar.add(pauza);
        JMenu koniec = new JMenu("Koniec");
        menuBar.add(koniec);
        setJMenuBar(menuBar);

        koniec.addMenuListener(new MenuListener()
        {
            @Override
            public void menuSelected(MenuEvent e)
            {
                System.exit(0);
            }

            @Override
            public void menuDeselected(MenuEvent e)
            {

            }

            @Override
            public void menuCanceled(MenuEvent e)
            {

            }
        });

        start.addMenuListener(new MenuListener()
        {
            @Override
            public void menuSelected(MenuEvent e)
            {
                size = control.getInputSize();
                chart.clear();

                ex = new Experiment(
                        control.getInputTime(),  size, control.getInputElement(), "halfLife", (float) 2);

                ActionListener repaintExperiment = new ActionListener()
                {
                    public void actionPerformed(ActionEvent evt)
                    {
                        canvas.repaintExperiment(size, ex);
                        chart.repaintExperiment(ex.getRadiologicalActivity(), ex.getTime(),control.getInputTime(),size);
                        navigation.setExperimentValues(size,ex.getRemainingParticles(),ex.getSurviveProbability());
                    }
                };

                timer = new Timer(300 ,repaintExperiment);
                timer.setRepeats(true);
                ex.start();
                timer.start();
            }

            @Override
            public void menuDeselected(MenuEvent e)
            {

            }

            @Override
            public void menuCanceled(MenuEvent e)
            {

            }
        });

        pauza.addMenuListener(new MenuListener()
        {
            @Override
            public void menuSelected(MenuEvent e)
            {
                try
                {
                    timer.stop();
                }
                catch (Exception exc)
                {
                    System.out.println(exc.getMessage());
                }
            }

            @Override
            public void menuDeselected(MenuEvent e)
            {

            }

            @Override
            public void menuCanceled(MenuEvent e)
            {

            }
        });
    }

    public class Canvas extends JPanel
    {
        private int size;
        private Experiment ex;

        private int padding = 25;

        public Canvas()
        {
            setBackground(Color.lightGray);
            this.setPreferredSize(new Dimension(500, 300));
            setOpaque(true);
            repaint();
        }

        public void repaintExperiment(int _size, Experiment _ex)
        {
            size = _size;
            ex =_ex;
            repaint();
        }

        public void clear()
        {
            size = 0;
            ex = null;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            double xScale = ((double) getWidth() - (2 * padding)) / 10;
            double yScale = ((double) getHeight() - 2 * padding) / 10;

            for(int i = 0; i < size; i++)
            {
                if(ex.isUndergone(i))
                    g.setColor(Color.red);
                else
                    g.setColor(Color.green);

                int x = (int)((i % 10) * xScale) + padding;
                int y = (int)((i % 100)/10 * yScale) + padding;
                g.fillOval(x,y, (int)xScale, (int)yScale);
            }
        }
    }

    public class Chart extends JPanel
    {
        /**
         * Listy przechowujace parametry do wystwietlania wartosci na wykresie
         */
        List<Double> radiologicalActivity  = new ArrayList();
        List<Long> time  = new ArrayList();

        /**
         * Zmienne potrzebne do utrzymania estetycznego wygladu wykresu
         */
        private int padding = 25;
        private int labelPadding = 25;
        private int maxTime = 1;
        private int minTime = 0;
        private int maxRadAct = 22;
        private int minRadAct = 0;
        private int numberYDivisions = 10;
        private int pointWidth = 4;
        private Color gridColor = new Color(200, 200, 200, 200);
        private Color lineColor = new Color(44, 102, 230, 180);

        public Chart()
        {
            setBackground(Color.lightGray);
            this.setPreferredSize(new Dimension(500, 300));
            setOpaque(true);
            repaint();
        }

        public void repaintExperiment(double _radiologicalActivity, long _time, int _timeSize, int _elementsSize)
        {
            radiologicalActivity.add(_radiologicalActivity);
            time.add(_time);
            maxRadAct = _elementsSize/3;
            maxTime = _timeSize;
            repaint();
        }

        public void clear() 
        {
            radiologicalActivity.clear();
            time.clear();
            repaint();
        }

        /**
         * Malowanie wykresu na podstawie repozytorium:
         * https://gist.github.com/roooodcastro/6325153
         */
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // wyliczania skali dla os
            double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (maxTime - minTime);
            double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxRadAct - minRadAct);

            // rysowanie białego tła
            g2.setColor(Color.WHITE);
            g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
            g2.setColor(Color.BLACK);

            // tworzenie poziomych linii i opisanie osi Y
            for (int i = 0; i < numberYDivisions + 1; i++) {
                int x0 = padding + labelPadding;
                int x1 = pointWidth + padding + labelPadding;
                int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
                int y1 = y0;
                if (maxRadAct > 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                    g2.setColor(Color.BLACK);
                    String yLabel = ((int) ((minRadAct + (maxRadAct - minRadAct) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(yLabel);
                    g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }

            // tworzenie pionowych linii i opisanie osi X
            for (int i = 0; i <= maxTime; i++) {
                if (maxTime > 0) {
                    int x0 = i * (getWidth() - padding * 2 - labelPadding) / maxTime + padding + labelPadding;
                    int x1 = x0;
                    int y0 = getHeight() - padding - labelPadding;
                    int y1 = y0 - pointWidth;
                    if ((i % ((int) ((maxTime / 20.0)) + 1)) == 0) {
                        g2.setColor(gridColor);
                        g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                        g2.setColor(Color.BLACK);
                        String xLabel = i + "";
                        FontMetrics metrics = g2.getFontMetrics();
                        int labelWidth = metrics.stringWidth(xLabel);
                        g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                    }
                    g2.drawLine(x0, y0, x1, y1);
                }
            }

            // tworzenie czarnych linii os
            g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
            g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

            // rysowanie punktow na wykresie
            g2.setStroke( new BasicStroke(2f));
            for (int i = 1; i < time.size(); i++) {
                g2.setColor(lineColor);
                int x1 = (int)((time.get(i-1).intValue()* xScale)/1000  + padding + labelPadding);
                int y1 = (int)((maxRadAct - radiologicalActivity.get(i-1)) * yScale + padding);
                int x2 = (int)((time.get(i).intValue()* xScale)/1000  + padding + labelPadding);
                int y2 = (int)((maxRadAct - radiologicalActivity.get(i)) * yScale + padding);
                g2.fillOval(x2 , y2, pointWidth, pointWidth);
                g2.drawLine(x1, y1, x2, y2);
            }
        }
    }

    public class Navigation extends JPanel
    {
        private JLabel label1;
        private JLabel label2;
        private JLabel label3;
        private JLabel label4;
        private JLabel label5;
        private JLabel label6;

        public Navigation()
        {
            this.setLayout(new GridLayout(3,2));

            label1 = new JLabel("Początkowa liczba atomów", SwingConstants.CENTER);
            label2 = new JLabel("Pozostała liczba atomów", SwingConstants.CENTER);
            label3 = new JLabel("Prawdopodobieństwo przeżycia", SwingConstants.CENTER);
            label4 = new JLabel("-", SwingConstants.CENTER);
            label5 = new JLabel("-", SwingConstants.CENTER);
            label6 = new JLabel("-", SwingConstants.CENTER);

            this.add(label1);
            this.add(label4);
            this.add(label2);
            this.add(label5);
            this.add(label3);
            this.add(label6);
        }

        public void setExperimentValues(int _size, int _remainingParticles, double _surviveProbability)
        {
            label4.setText(String.valueOf(_size));
            label5.setText(String.valueOf(_remainingParticles));
            label6.setText(String.valueOf(_surviveProbability));
        }

        public void clearValues()
        {
            label4.setText("-");
            label5.setText("-");
            label6.setText("-");
        }
    }

    public class Control extends JPanel
    {
        private JScrollBar scrollTime;
        private JScrollBar scrollSize;
        private JLabel inputTime;
        private JLabel inputSize;
        private JLabel inputElement;
        private JComboBox<ChemicalElement> elementsList;

        public Control()
        {
            this.setLayout(new GridLayout(6,1));

            elementsList = new JComboBox<ChemicalElement>();
            elementsList.setModel(new DefaultComboBoxModel<>(ChemicalElement.values()));
            scrollTime = new JScrollBar(JScrollBar.HORIZONTAL, 1, 1, 1, 1001);
            scrollSize = new JScrollBar(JScrollBar.HORIZONTAL, 22, 1, 1, 1001);
            inputTime = new JLabel("Czas: " + "1");
            inputSize = new JLabel("Ilość atomów: " +"22");
            inputElement = new JLabel("Pierwiastek: " + elementsList.getItemAt(0));

            this.add(inputTime);
            this.add(scrollTime);
            this.add(inputSize);
            this.add(scrollSize);
            this.add(inputElement);
            this.add(elementsList);
//            this.add(parametersList);

            scrollTime.addAdjustmentListener(new AdjustmentListener()
            {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e)
                {
                    chart.clear();
                    canvas.clear();
                    try
                    {
                        timer.stop();
                    }
                    catch(Exception exc)
                    {

                    }
                    inputTime.setText("Czas: " + String.valueOf(scrollTime.getValue()));
                }
            });
            scrollSize.addAdjustmentListener(new AdjustmentListener()
            {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e)
                {
                    chart.clear();
                    canvas.clear();
                    try
                    {
                        timer.stop();
                    }
                    catch(Exception exc)
                    {

                    }
                    inputSize.setText("Ilość atomów: " + String.valueOf(scrollSize.getValue()));
                }
            });
            elementsList.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    inputElement.setText("Pierwiastek: " + elementsList.getSelectedItem());
                }
            });

        }

        public int getInputSize()
        {
            return scrollSize.getValue();
        }

        public ChemicalElement getInputElement()
        {
            return (ChemicalElement)elementsList.getSelectedItem();
        }

        public int getInputTime()
        {
            return scrollTime.getValue();
        }
    }
}
