import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.Painter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class view extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	// -- Vars --
	private BufferedImage image;
	private BufferedImage originalImage;
	final private JPanel imagePanel;
	private JLabel imageLabel;
	private painting newImage;
	
	
	// -- Visual Vars --
	private JLabel redLabel;
	private JLabel greenLabel;
	private JLabel blueLabel;
	private JLabel grayscaleLabel;
	private JLabel invertedGrayscaleLabel;
	private JLabel newLabel;

	// -- Sliders --
	private JSlider redSlider;
	private JSlider greenSlider;
	private JSlider blueSlider;
	private JSlider grayscaleSlider;
	private JSlider invertedGrayscaleSlider;
	private JSlider newSlider;
	
	// -- Buttons --
	private JButton heatMapButton;
	private JButton heatMapVar2;
	private JButton heatMapOutlineButton;
	private JButton resetButton;
	private JButton randomButton;
	private JButton horizontalReflect;
	private JButton verticalReflect;
	private JButton colorSwapButton;
	private JButton colorSwap2Button;
	private JButton rasterButton;
	private JButton rasterButton2;
	private JButton newButton;

	
	private JPanel settingsPanel;



	// -- Constructor for the GUI --
	view()
	{
		super("Photo Edit");
		this.setSize(950, 950);
		setLayout(new BorderLayout());
		
		// -- Initialize vars --	
		painting painter = new painting();
		imageLabel = new JLabel();
		newImage = new painting();
		settingsPanel = new JPanel();
		settingsPanel.setLayout(new GridLayout(6,2));
		
		
		// -- Add panels -- 
		image = painter.getImage();
		originalImage = painter.getOriginalImage();

		// -- Init Sliders --
		redSlider = new JSlider(JSlider.HORIZONTAL, 0, 25, 0);
		greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 25, 0);
		blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 25, 0);
		grayscaleSlider = new JSlider(JSlider.HORIZONTAL, 3, 6, 3);
		invertedGrayscaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 6, 1);
		newSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 0);
		
		
		// -- Init Labels --
		redLabel 	= new JLabel("RGB Red");
		greenLabel 	= new JLabel("RGB Green");
		blueLabel 	= new JLabel("RGB Blue");
		newLabel 	= new JLabel("");
		grayscaleLabel = new JLabel("Grayscale");
		invertedGrayscaleLabel = new JLabel("Inverted Grayscale");

		
		// -- Init Buttons -- 
		heatMapButton 		= new JButton("Heat Map");
		heatMapOutlineButton= new JButton("Heat Outline");
		heatMapVar2			= new JButton("Heat Map Var");
		randomButton 		= new JButton("Random Map");
		horizontalReflect 	= new JButton("Horizontal Reft");
		verticalReflect 	= new JButton("Vertical Reft");
		colorSwapButton 	= new JButton("Color Swap");
		colorSwap2Button 	= new JButton("Color Swap 2");
		rasterButton 		= new JButton("Raster Manip");
		rasterButton2 		= new JButton("Raster Manip 2");
		newButton 			= new JButton("Random Swap");
		resetButton 		= new JButton("RESET");

		
		// -- Add image to label and panel --
		JLabel picLabel = new JLabel(new ImageIcon(painter.getImage()));
		imagePanel = painter.getImagePanel();
		imagePanel.setSize(new Dimension(200,200));
		
		// -- Add sliders and labels --
		this.add(picLabel, BorderLayout.NORTH);
		
		settingsPanel.add(redLabel);
		settingsPanel.add(redSlider);
		settingsPanel.add(greenLabel);
		settingsPanel.add(greenSlider);
		settingsPanel.add(blueLabel);
		settingsPanel.add(blueSlider);
		settingsPanel.add(grayscaleLabel);
		settingsPanel.add(grayscaleSlider);
		settingsPanel.add(invertedGrayscaleLabel);
		settingsPanel.add(invertedGrayscaleSlider);
		settingsPanel.add(newLabel);
		settingsPanel.add(newSlider);
		
		// -- Adding buttons -- 
		settingsPanel.add(heatMapButton);
		settingsPanel.add(heatMapVar2);
		settingsPanel.add(heatMapOutlineButton);
		settingsPanel.add(randomButton);
		settingsPanel.add(horizontalReflect);
		settingsPanel.add(verticalReflect);
		settingsPanel.add(colorSwapButton);
		settingsPanel.add(colorSwap2Button);
		settingsPanel.add(rasterButton);
		settingsPanel.add(rasterButton2);
		settingsPanel.add(newButton);
		settingsPanel.add(resetButton);
		this.add(settingsPanel, BorderLayout.EAST);
		
		this.pack();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		
		
		// -- Below are all the listeners for the sliders and buttons -- 
		redSlider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {
		    	  
		    	int value = redSlider.getValue();    
		        painter.drawRedShifted(value, image);
		        repaint();		        
		      }
		    });
		
		greenSlider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {

		    	int value = greenSlider.getValue();
		        painter.drawGreenShifted(value, image);
		        repaint();		        
		      }
		    });
		
		blueSlider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {

		    	int value = blueSlider.getValue();
		        painter.drawBlueShifted(value, image);
		        repaint();		        
		      }
		    });
		
		grayscaleSlider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {
	
		    	int value = grayscaleSlider.getValue();
		        painter.drawGrayscale(value, image);
		        repaint();		        
		      }
		    });
		        
		invertedGrayscaleSlider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {
		        
		    	int value = invertedGrayscaleSlider.getValue();
		        painter.drawGrayscaleInverted(value, image);
		        repaint();		        
		      }
		    });
		
			newSlider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {

		    	int value = newSlider.getValue();
		        painter.newFunc(value, image);
		        repaint();		        
		      }
		    });

		
		resetButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				//painter.drawReset();
				new view();				
				dispose();
				System.out.println("reset button");
				
			}
		});
		
		heatMapButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawHeatMap(image);
				repaint();
			}
		});
		
		heatMapOutlineButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawHeatMapOutline(image);
				repaint();
			}
		});
		
		heatMapVar2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawHeatMap2(image);
				repaint();
			}
		});
		
		randomButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawRandomMap(image);
				repaint();
			}
		});
		
		horizontalReflect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawHorizontalReflect(image);
				repaint();
			}
		});
		
		verticalReflect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawVerticalReflect(image);
				repaint();
			}
		});

		colorSwapButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawColorSwap(image);
				repaint();
			}
		});
		
		colorSwap2Button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawColorSwap2(image);
				repaint();
			}
		});
		
		rasterButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawRasterManip(image);
				repaint();
			}
		});
		
		rasterButton2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawRasterManip2(image);
				repaint();
			}
		});
		
		newButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				painter.drawRandomMapSwap(image);
				repaint();
			}
		});		
	}
}
