/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.unizh.ini.jaer.projects.gesture.hmm;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;
/**
 * Abstract class for trajectory drawing panel. Feature vector extraction and gesture simulation can be implemented based on this class.
 * @author Jun Haeng Lee
 */
abstract public class TrajectoryDrawingPanel extends Frame implements MouseMotionListener, MouseListener, ActionListener, WindowListener{

    /**
     * Name for button 'Clear trajectory' which is a default button always added to the menu. 
     */
    public static final String clearButtonName = "Clear Trajectory";

    /**
     * Width and height of image panel to draw a trajectory
     */
    public int imgPanelWidth, imgPanelHeight;

    /**
     * Array for a trajectory
     */
    protected ArrayList<Point2D.Float> trajectory = new ArrayList<Point2D.Float>();

    Image img = null;
    Graphics gImg = null;

    /**
    * for random color generation
    */
    protected static Random random = new Random();

    /**
     * pen color
     */
    Color penColor = Color.BLACK;

    /**
     * Constructor with the title and the size of image panel
     * @param title
     * @param imgPanelWidth
     * @param imgPanelHeight
     */
    public TrajectoryDrawingPanel(String title, int imgPanelWidth, int imgPanelHeight) {
        super(title);
        this.imgPanelWidth = imgPanelWidth;
        this.imgPanelHeight = imgPanelHeight;
    }
    /**
     * Constructor with the title, the size of image panel, and component names for menu
     * @param title : drawing panel frame title
     * @param imgPanelWidth 
     * @param componentNames : list of names for buttons used to control the panel
     * @param imgPanelHeight
     */
    public TrajectoryDrawingPanel(String title, int imgPanelWidth, int imgPanelHeight, String[] componentNames) {
        this(title, imgPanelWidth, imgPanelHeight);

        initLayout(componentNames);
        
        // add event listeners
        addWindowListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);

        setVisible(true);

        img = createImage(imgPanelWidth, imgPanelHeight);
        gImg = img.getGraphics();
        initialDeco();
        repaint();
    }

    /** override this method to change the layout
     *
     * @param componentNames
     */
    public void initLayout(String[] componentNames){
        setLayout(new BorderLayout());

        // configuration of button panel
        Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(1, componentNames.length));
        for(int i = 1; i<= componentNames.length; i++){
            Button newButton = new Button(componentNames[i-1]);
            buttonPanel.add(newButton, ""+i);
            newButton.addActionListener(this);
        }
        Button clearButton = new Button(clearButtonName);
        buttonPanel.add(clearButton, ""+componentNames.length);
        clearButton.addActionListener(this);
        add(buttonPanel, "South");

        setBounds(100, 100, imgPanelWidth, imgPanelHeight+25);
        setResizable(false);
    }

    @Override
    public void paint(Graphics g) {
        if(img != null)
            g.drawImage(img, 0, 0, this);
    }

    /**
     * Initial decorations
     */
    private void initialDeco(){
        penColor = Color.BLACK;
        gImg.setColor(penColor);
        gImg.setFont(new Font("Arial", Font.PLAIN, 15));
        gImg.drawString("Draw a trajectory by dragging your mouse.", 10, 50);
        gImg.drawString("X", 65, imgPanelHeight - 5);
        gImg.drawString("Y", 8, imgPanelHeight - 65);
        gImg.drawString("(0, 0)", 15, imgPanelHeight - 15);
        gImg.drawLine(10, imgPanelWidth - 10, 60, imgPanelHeight - 10);
        gImg.drawLine(55, imgPanelWidth - 15, 60, imgPanelHeight - 10);
        gImg.drawLine(55, imgPanelWidth - 5, 60, imgPanelHeight - 10);
        gImg.drawLine(10, imgPanelWidth - 10, 10, imgPanelHeight - 60);
        gImg.drawLine(5, imgPanelWidth - 55, 10, imgPanelHeight - 60);
        gImg.drawLine(15, imgPanelWidth - 55, 10, imgPanelHeight - 60);
    }
    
    /**
     * reset trajectory
     */
    public void resetTrajectory(){
        trajectory.clear();

        penColor = Color.getHSBColor(random.nextFloat(), 1, 1);
        gImg.setColor(penColor);
    }

    /**
     * returns pen color
     * @return
     */
    public Color getColor(){
        return penColor;
    }


    /** Clear images drawn on the image panel
     *
     */
    public void clearImage(){
        resetTrajectory();
        gImg.clearRect(0, 0, imgPanelWidth, imgPanelHeight);
        initialDeco();
        repaint();
    }

    /**
     * location of arrow head
     */
    public enum ArrowLocation {
        Center,
        End
    };
    /**
     * Draws arrow
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param size
     * @param aloc
     */
    public void drawArrow(int startX, int startY, int endX, int endY, int size, ArrowLocation aloc){
        double arrowAngle = Math.PI/6.0;
        double slope;
        int startY_ = imgPanelHeight - startY;
        int endY_ = imgPanelHeight - endY;

        if(endX == startX){
            if(endY_ == startY_)
                return;
            else if(endY_ > startY_)
                slope = Math.PI/2.0;
            else
                slope = 3.0*Math.PI/2.0;
        }else if(endX > startX)
            slope= Math.atan((double)(endY_ - startY_) / (double)(endX - startX));
        else
            slope= Math.atan((double)(endY_ - startY_) / (double)(endX - startX)) + Math.PI;

        if(slope < 0)
            slope += Math.PI*2.0;

        gImg.drawLine(startX, startY, endX, endY);
        if(aloc == ArrowLocation.End){
            gImg.drawLine(endX - (int) (size*Math.cos(arrowAngle + slope)), endY + (int) (size*Math.sin(arrowAngle + slope)), endX, endY);
            gImg.drawLine(endX - (int) (size*Math.cos(arrowAngle - slope)), endY - (int) (size*Math.sin(arrowAngle - slope)), endX, endY);
        } else {
            gImg.drawLine((startX+endX)/2 - (int) (size*Math.cos(arrowAngle + slope)), (startY+endY)/2 + (int) (size*Math.sin(arrowAngle + slope)), (startX+endX)/2, (startY+endY)/2);
            gImg.drawLine((startX+endX)/2 - (int) (size*Math.cos(arrowAngle - slope)), (startY+endY)/2 - (int) (size*Math.sin(arrowAngle - slope)), (startX+endX)/2, (startY+endY)/2);
        }
    }

    /**
     * draws trajectory
     * @param trj
     */
    public void drawTrajectory(ArrayList<Point2D.Float> trj){
        Color tmp = getColor();
        Color curColor = Color.BLUE;

        gImg.setColor(curColor);
        gImg.drawOval((int) trj.get(0).x - 3, imgPanelHeight - (int) trj.get(0).y - 3, 6, 6);
        for(int i=0; i<trj.size()-1; i++){
            Point2D.Float startPos = trj.get(i);
            Point2D.Float endPos = trj.get(i+1);

            if(i == trj.size()/2)
                drawArrow((int) startPos.x, imgPanelHeight - (int) startPos.y, (int) endPos.x, imgPanelHeight - (int) endPos.y, 8, ArrowLocation.Center);
            else if(i == trj.size()-2)
                drawArrow((int) startPos.x, imgPanelHeight - (int) startPos.y, (int) endPos.x, imgPanelHeight - (int) endPos.y, 8, ArrowLocation.End);
            else
                gImg.drawLine((int) startPos.x, imgPanelHeight - (int) startPos.y, (int) endPos.x, imgPanelHeight - (int) endPos.y);

            if(curColor == Color.BLUE)
                curColor = Color.RED;
            else
                curColor = Color.BLUE;
            gImg.setColor(curColor);
        }

        gImg.setColor(tmp);
    }

    /**
     * mouse motion listener
     * @param me
     */
    public void mouseMoved(MouseEvent me){
        
    }

    /**
     * mouse motion listener
     * @param me
     */
    public void mouseDragged(MouseEvent me){
        if(me.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK){
            Point2D.Float newPoint = new Point2D.Float();
            newPoint.x = me.getX();
            newPoint.y = imgPanelHeight - me.getY();
            trajectory.add(newPoint);

            if(trajectory.size() == 1)
                gImg.drawOval((int) newPoint.x - 3, imgPanelHeight - (int) newPoint.y - 3, 6, 6);
            
            if(trajectory.size() > 1){
                Point2D.Float prevPoint = trajectory.get(trajectory.size()-2);

                gImg.drawLine((int) prevPoint.x, imgPanelHeight - (int) prevPoint.y, (int) newPoint.x, imgPanelHeight - (int) newPoint.y);
            }
            repaint();
        }
    }

    /**
     * mouse listener
     * @param e
     */
    public void mouseClicked(MouseEvent e) {
        
    }

    /**
     * mouse listener
     * @param e
     */
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * mouse listener
     * @param e
     */
    public void mouseExited(MouseEvent e) {
        
    }

    /**
     * mouse listener
     * @param e
     */
    public void mousePressed(MouseEvent e) {

    }

    /**
     * mouse listener
     * @param e
     */
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            Point2D.Float newPoint = new Point2D.Float();
            newPoint.x = e.getX();
            newPoint.y = imgPanelHeight - e.getY();
            
            trajectory.add(newPoint);

            if(trajectory.size() == 1)
                gImg.drawOval((int) newPoint.x - 3, imgPanelHeight - (int) newPoint.y - 3, 6, 6);

            if(trajectory.size() > 1){
                Point2D.Float prevPoint;
                double distance;
                int timeDiff = 1;
                do{
                    prevPoint = trajectory.get(trajectory.size() - 1 - timeDiff);
                    distance = Math.sqrt(Math.pow(prevPoint.x - newPoint.x, 2.0) + Math.pow(prevPoint.y - newPoint.y, 2.0));
                    timeDiff++;
                } while (timeDiff < trajectory.size() && distance < 5.0);

                drawArrow((int) prevPoint.x, imgPanelHeight - (int) prevPoint.y, (int) newPoint.x, imgPanelHeight - (int) newPoint.y, 8, ArrowLocation.End);
            }
            repaint();
        }
    }


    /**
     * action listener
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        String buttonName = e.getActionCommand();

        if(buttonName.equals(clearButtonName)) {
            clearImage();
        } else {
            buttonAction(buttonName);
        }
    }


    /**
     * This funtions has to be implemented to define the action followed by a button click on the menu
     * Names of buttons are defined as componenetNames in Constructor
     * @param buttonName
     */
    abstract public void buttonAction(String buttonName);


    /**
     * window listener
     * @param e
     */
    public void windowActivated(WindowEvent e) {

    }

    /**
     * window listener
     * @param e
     */
    public void windowDeactivated(WindowEvent e) {

    }

    /**
     * window listener
     * @param e
     */
    public void windowDeiconified(WindowEvent e) {

    }

    /**
     * window listener
     * @param e
     */
    public void windowIconified(WindowEvent e) {

    }

    /**
     * window listener
     * @param e
     */
    public void windowOpened(WindowEvent e) {

    }

    /**
     * window listener
     * @param we
     */
    public void windowClosed(WindowEvent we) {

    }

    /**
     * window listener
     * @param we
     */
    public void windowClosing(WindowEvent we) {
        System.exit(0);
    }
}
