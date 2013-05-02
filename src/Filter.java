package ntu.csie.oop13spring;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

public class Filter {
	  public static Image filterOutBackground(Image image, Color bgColor) {
	    final int filterOutValue = bgColor.getRGB() & 0xFFFFFF;
	    RGBImageFilter filter = new RGBImageFilter() {
	      public int filterRGB(int x, int y, int rgb) {
	        return (rgb & 0xFFFFFF) == filterOutValue? 0 : rgb;
	      }
	    };
	    FilteredImageSource producer = new FilteredImageSource(image.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(producer);
	  }
	}
