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
                ex = new Experiment(
                        control.getInputTime(),  size, control.getInputElement(), "halfLife", (float) 2);

                ActionListener repaintExperiment = new ActionListener()
                {
                    public void actionPerformed(ActionEvent evt)
                    {
                        canvas.repaintExperiment(size, ex);
                        chart.repaintExperiment(ex.getRadiologicalActivity(),ex.getSurviveProbability(), ex.getTime());
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

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);


            int x = 0;
            int y = 0;
            for(int i = 0; i < size; i++)
            {
                if(ex.isUndergone(i))
                    g.setColor(Color.red);
                else
                    g.setColor(Color.green);

                g.fillOval(x,y, 10, 10);
                x+=10;
                y+=10;
            }
        }
    }

    public class Chart extends JPanel
    {
        List<Double> radiologicalActivity  = new ArrayList();
        List<Double> surviveProbability  = new ArrayList();
        List<Long> time  = new ArrayList();

        public Chart()
        {
            setBackground(Color.lightGray);
            this.setPreferredSize(new Dimension(500, 300));
            setOpaque(true);
            repaint();
        }

        public void repaintExperiment(double _radiologicalActivity, double _surviveProbability, long _time) 
        {
            radiologicalActivity.add(_radiologicalActivity);
            surviveProbability.add(_surviveProbability);
            time.add(_time);
            repaint();
        }

        public void clear() 
        {
            radiologicalActivity.clear();
            surviveProbability.clear();
            time.clear();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);


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
//        private JComboBox<String> parametersList;

        public Control()
        {
            this.setLayout(new GridLayout(6,1));

            elementsList = new JComboBox<ChemicalElement>();
            elementsList.setModel(new DefaultComboBoxModel<>(ChemicalElement.values()));
//            parametersList = new JComboBox<String>();
//            List<String> parametersNames = Arrays.asList("halfLife", "meanLifeTime","decayConstant");
//            parametersList.setModel(new DefaultComboBoxModel(parametersNames.toArray()));
            scrollTime = new JScrollBar(JScrollBar.HORIZONTAL, 1, 1, 1, 100);
            scrollSize = new JScrollBar(JScrollBar.HORIZONTAL, 22, 1, 1, 100);
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
                    inputTime.setText("Time: " + String.valueOf(scrollTime.getValue()));
                }
            });
            scrollSize.addAdjustmentListener(new AdjustmentListener()
            {
                @Override
                public void adjustmentValueChanged(AdjustmentEvent e)
                {
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
