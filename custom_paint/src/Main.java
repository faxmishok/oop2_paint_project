import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class Main extends JPanel implements MouseListener , ActionListener , MouseMotionListener {

    public static int stroke = 2, eraser = 0;
    private int initX, initY, finX, finY, choice;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private int eraserWidth = 25;
    private int eraserHeight = 25;


    public static void main(String[] args) {
        Main main = new Main();
    }


    Main() {

        JFrame frame = new JFrame("Custom Paint");
        frame.setSize(1200, 800);
        frame.setBackground(BACKGROUND_COLOR);
        frame.getContentPane().add(this);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        //File
        JMenu file = new JMenu("File");
        menuBar.add(file);
        JMenuItem open = new JMenuItem("Open");
        file.add(open);
        JMenuItem save = new JMenuItem("Save");
        file.add(save);
        //Edit
        JMenu help = new JMenu("Help");
        menuBar.add(help);
        JMenuItem about = new JMenuItem("About");
        help.add(about);
        about.addActionListener(this); //


        JButton b1 = new JButton("Clear");
        b1.setIcon(new ImageIcon("src/clean.png"));
        b1.addActionListener(this);

        JButton color = new JButton("Color");
        color.setIcon(new ImageIcon("src/fill.png"));
        color.addActionListener(this);

        JButton erase = new JButton("Erase");
        erase.setIcon(new ImageIcon("src/eraser.png"));
        erase.addActionListener(this);

        JButton b2 = new JButton("Rectangle");
        b2.setIcon(new ImageIcon("src/rectangle.png"));
        b2.addActionListener(this);


        JButton b3 = new JButton("Circle");
        b3.setIcon(new ImageIcon("src/circle.png"));
        b3.addActionListener(this);

        JButton b4 = new JButton("Line");
        b4.setIcon(new ImageIcon("src/line.png"));
        b4.addActionListener(this);


        JRadioButton thin = new JRadioButton("Thin",true);
        thin.addActionListener(this);
        JRadioButton medium = new JRadioButton("Medium");
        medium.addActionListener(this);
        JRadioButton thick = new JRadioButton("Thick");
        thick.addActionListener(this);

        ButtonGroup lineOption = new ButtonGroup();
        lineOption.add(thin);
        lineOption.add(medium);
        lineOption.add(thick);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(3,1));
        radioPanel.add(thin);
        radioPanel.add(medium);
        radioPanel.add(thick);

        radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Thickness"));
        radioPanel.setVisible(true);

        this.add(b1);
        this.add(color);
        this.add(erase);
        this.add(b2);
        this.add(b3);
        this.add(b4);
        this.add(radioPanel);


        addMouseListener(this);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(true);
    }


    BufferedImage grid;
    Graphics2D gc;

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (grid == null){
            int w = this.getWidth();
            int h = this.getHeight();
            grid = (BufferedImage) (this.createImage(w, h));
            gc = grid.createGraphics();
            gc.setColor(Color.BLACK);
        }
        setreverseXY();
        g2.drawImage(grid, null, 0, 0);
    }


    public void draw(){
        Graphics2D g = (Graphics2D) getGraphics();
        int w = finX - initX;
        if (w < 0)	w = w * (-1);
        int h = finY - initY;
        if (h < 0)	h = h * (-1);
        /*conditions to set our stroke width's before starting to draw either line or shapes*/
        if ( stroke == 2 )  gc.setStroke(new BasicStroke(1));
        if (stroke == 0)	gc.setStroke(new BasicStroke(4));
        if (stroke == 1)	gc.setStroke(new BasicStroke(10));
        /**/
         if(choice == 1){
            setreverseXY();
            gc.drawRect(initX, initY, w, h);
            repaint();
        }

        else if(choice == 2){
            setreverseXY();
            gc.drawOval(initX, initY, w, h);
            repaint();
        }

        else if(choice == 3){
            gc.drawLine(initX, initY, finX, finY);
            repaint();
        }

        else if(choice == 4){
            Color temp = gc.getColor();
            gc.setColor(BACKGROUND_COLOR);
            gc.fillRect(0, 0, getWidth(), getHeight());
            gc.setColor(temp);
            repaint();
        }

        else if (choice == 5){
            if (eraser == 1)	gc.clearRect(initX, initY, w, h);
        }
        else { }
    }

    public void setreverseXY(){
        if (initX > finX){
            int temp = 0;
            temp = initX;
            initX = finX;
            finX = temp;
        }
        if (initY > finY){
            int temp = 0;
            temp = initY;
            initY = finY;
            finY = temp;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){

        super.removeMouseMotionListener(this);

        if (e.getActionCommand().equals("Color")){
            Color bgColor = JColorChooser.showDialog(this, "Choose Background Color",getBackground());
            if (bgColor != null)
                gc.setColor(bgColor);
        }

        if (e.getActionCommand().equals("About")){
            JFrame about = new JFrame("About");
            about.setSize(300, 300);
            JTextPane tp = new JTextPane();
            tp.setText("This is Custom Paint Project of Computer Science - 1 (L2) Students.This app is developed only for educational purposes and is not used in any professional case.");
            about.add(tp);
            about.setLocationRelativeTo(null);
            about.setVisible(true);
        }

        if (e.getActionCommand().equals("Rectangle")) {
            choice = 1;
        }

        if (e.getActionCommand().equals("Circle")){
            choice = 2;
        }

        if (e.getActionCommand().equals("Line")){
            choice = 3;
        }

        if ( e.getActionCommand().equals("Thin") ){
            stroke = 2;
        }
        if (e.getActionCommand().equals("Medium")){
            stroke = 0;
        }

        if (e.getActionCommand().equals("Thick")){
            stroke = 1;
        }

        if (e.getActionCommand().equals("Erase")){
            eraser = 1;
            choice = 5;
            super.addMouseMotionListener(this);
        }

        if (e.getActionCommand().equals("Clear")){
            choice = 4;
            draw();
        }

    } // end of actionPerformed

    @Override
    public void mousePressed(MouseEvent e){
        initX = e.getX();
        initY = e.getY();
        repaint();
    }


    @Override
    public void mouseReleased(MouseEvent e){
        finX = e.getX();
        finY = e.getY();
        draw();
        eraser = 0;
    }


    @Override
    public void mouseDragged(MouseEvent re){
        Color c = gc.getColor();
        gc.setColor(BACKGROUND_COLOR);
        gc.drawRect(re.getX(), re.getY(), eraserWidth, eraserHeight);
        gc.setColor(c);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e){ }
    @Override
    public void mouseEntered(MouseEvent e){ }
    @Override
    public void mouseClicked(MouseEvent e){ }
}