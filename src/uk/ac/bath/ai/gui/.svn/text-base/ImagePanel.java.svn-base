/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bath.ai.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import uk.ac.bath.ai.image.DisplayImage;
import uk.ac.bath.ai.image.FloatVectorImage;

/**
 * 
 * @author pjl
 */
public class ImagePanel extends JPanel {

	BufferedImage img;
	Graphics2D graphic;
	private int[] rgbarray;
	private int[] penarray;

	private Dimension imageSize;
	DisplayImage image;
	private MouseInputAdapter adt;
	private Rectangle dst; //

	public ImagePanel(int scale, final ImageClient client) {

		if (client != null) {
			adt = new MouseInputAdapter() {
				@Override
				public void mouseDragged(MouseEvent evt) {
					int x = (((evt.getX() - dst.x)) * img.getWidth())
							/ dst.width - 1;
					int y = (((evt.getY() - dst.y)) * img.getHeight())
							/ dst.height - 1;
					// System.out.println(x + "  " + y);
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							image.setPixel(x-1+j, y-1+i, 1.0f);
						}
					}
					repaint();
				}

				@Override
				public void mousePressed(MouseEvent evt) {
					int x = (((evt.getX() - dst.x)) * img.getWidth())
							/ dst.width - 1;
					int y = (((evt.getY() - dst.y)) * img.getHeight())
							/ dst.height - 1;
					// System.out.println(x + "  " + y);
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							image.setPixel(x-1+j, y-1+i, 1.0f);
						}
					}
					repaint();

				}

				@Override
				public void mouseReleased(MouseEvent evt) {
					if (client != null)
						client.processImage((FloatVectorImage) image);
				}
			};
			//
			// penarray=new int[9];
			// int c_b=255;
			// int c_g=255;
			// int c_r=255;
			// int val = (c_b) + (c_g << 8) + (c_r << 16) + (0xFF << 24);
			// Arrays.fill(penarray, val);
			this.addMouseListener(adt);
			this.addMouseMotionListener(adt);
		}
		int n = 28 * scale;
		setPreferredSize(new Dimension(n, n));
		setMinimumSize(new Dimension(n, n));
		setMaximumSize(new Dimension(n, n));
	}

	void setImage(DisplayImage image) {
		assert (image != null);
		this.image = image;
		setup();
		repaint();
	}

	// public ImagePanel(int width, int height) {
	//
	// setup(width, height);
	//
	// addMouseListener(new MouseAdapter() {
	// @Override
	// public void mouseDragged(MouseEvent ev) {
	// }
	// });
	//
	//
	//
	//
	// }
	void setup() {
		Dimension dim = image.getDimension();
		int width = dim.width;
		int height = dim.height;
		// assert( width == 28 && height == 28 );
		if (imageSize == null || imageSize.width != width
				|| imageSize.height != height) {
			imageSize = new Dimension(width, height);
			// rgbarray = new int[width * height];
			createGraphics();
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (graphic == null) {
			return;
		}
		rgbarray = image.getRGBArray();
		makeImageFromRGB();
		Insets inset = getInsets();

		// g.setColor(Color.YELLOW);
		dst = new Rectangle(inset.left, inset.top, getWidth() - inset.right
				- inset.left, getHeight() - inset.top - inset.bottom);
		// g2.draw(dst);
		// inset.right-inset.left,inset.bottom-inset.top);
		g.setColor(Color.WHITE);
		// g.drawImage(img, 0, 0, null);

		g.drawImage(img, dst.x, dst.y, dst.x + dst.width, dst.y + dst.height,
				0, 0, imageSize.width, imageSize.height, null);

	}

	void makeImageFromRGB() {
		img.setRGB(0, 0, imageSize.width, imageSize.height, rgbarray, 0,
				imageSize.width);
	}

	void testRGB() {

		int c_b = 255, c_r = 0, c_g = 0;
		for (int i = 0; i < imageSize.width * imageSize.height; i++) {
			c_g = i % 255;
			int color = (c_b) + (c_g << 8) + (c_r << 16) + (0xFF << 24);
			rgbarray[i] = color;
		}
	}

	// public void setData(byte[][] b) {
	// setup(b.length, b[0].length);
	// for (int i = 0; i < imageSize.width; i++) {
	// for (int j = 0; j < imageSize.height; j++) {
	// int c_b = b[j][i];
	// int c_r = c_b;
	// int c_g = c_r;
	// int color = (c_b) + (c_g << 8) + (c_r << 16) + (0xFF << 24);
	// rgbarray[j * imageSize.width + i] = color;
	// }
	// }
	//
	// repaint();
	// }
	void createGraphics() {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsConfiguration graphicsConfiguration = graphicsEnvironment
				.getDefaultScreenDevice().getDefaultConfiguration();
		img = graphicsConfiguration.createCompatibleImage(imageSize.width,
				imageSize.height, Transparency.BITMASK);
		graphic = img.createGraphics();
	}

}
