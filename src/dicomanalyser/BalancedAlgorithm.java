/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicomanalyser;


import com.pixelmed.dicom.DicomException;
import com.pixelmed.display.SourceImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author pc
 */
public class BalancedAlgorithm {
    
    
    public static void main(String[] args) throws IOException, DicomException {
        
        String path = System.getProperty("user.dir") + 
                "/dicomImigies/img/030001.dcm";        
        SourceImage sImg = new SourceImage(path);         
        BufferedImage original = sImg.getBufferedImage();
        BufferedImage binarized = binarize(original);
        writeImage(binarized);
    }
 
    
    // Return histogram of grayscale image
    private static int[] imageHistogram(BufferedImage input) {
        
        int[] histogram = new int[256];
        for(int i=0; i<histogram.length; i++) histogram[i] = 0;
        for(int i=0; i<input.getWidth(); i++) {
            for(int j=0; j<input.getHeight(); j++) {
                int red = new Color(input.getRGB (i, j)).getRed();                
                histogram[red]++;
            }
        } 
        return histogram;
    }
    
    
    private static float balancedTreshold(BufferedImage original) {
         
        int[] histogram = imageHistogram(original);                
        int i_s = 0;        
        int i_e = getHistogramEnd(histogram) - 1;                
        float threshold = (float) ((i_s + i_e)/2.0f); //center of the weighting scale             
        int w_l = getWeight(i_s, threshold, threshold, histogram); //weight of the left wing         
        int w_r = getWeight(threshold, i_e, threshold, histogram); //weight of the right wing         
        while(i_s<=i_e) {
            if(w_l > w_r) { // left side is heavier                
                w_l -= histogram[i_s];
                i_s += 1;                
                threshold += 0.5;                      
                if(threshold - (int)threshold == 0) { // if .0
                    w_r -= histogram[(int) threshold];                    
                } else { // if .5
                    w_l += histogram[(int) threshold];                    
                }                 
            }
            else if(w_l < w_r) { // right side is heavier                
                w_r -= histogram[i_e];                                
                i_e -= 1;                
                threshold -= 0.5;                
                if(threshold - (int)threshold == 0) { // if .0
                    w_l -= histogram[(int) threshold];
                } else { // if .5
                    w_r += histogram[(int) threshold + 1];                    
                }                
            }
            if(w_l == w_r) 
                break;
        }                        
        return threshold; 
    }
    
    
    private static int getWeight(float i_s, float i_e, float i_m, int[] histogram) {               
        
        int weight = 0;
        if(i_s < i_m) {
            for(int i=(int) i_s; i<i_e; i++)
                weight += histogram[i];
        } else {
            for(int i=(int) i_e; i>i_s; i--) 
                weight += histogram[i];
        }
               
        return weight;
    }
    
    
    private static BufferedImage binarize(BufferedImage original) {
        
        int red;
        int newPixel;
        float threshold = balancedTreshold(original);  // can very
        BufferedImage binarized = new BufferedImage(original.getWidth(), 
                original.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int i=0; i<original.getWidth(); i++) {
            for(int j=0; j<original.getHeight(); j++) {
                // Get pixels
                red = new Color(original.getRGB(i, j)).getRed();
                int alpha = new Color(original.getRGB(i, j)).getAlpha();
                if(red > threshold) {
                    newPixel = 255;
                }
                else {
                    newPixel = 0;
                }
                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                binarized.setRGB(i, j, newPixel); 
 
            }
        }
        return binarized;        
    }
    
    
    // Convert R, G, B, Alpha to standard 8 bit
    private static int colorToRGB(int alpha, int red, int green, int blue) {
 
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;
        return newPixel;
    }
 

    private static void writeImage(BufferedImage binarized) throws IOException {
        
        File file = new File("imageBalanced.jpg");
        ImageIO.write(binarized, "jpg", file);
        System.out.println("Done");        
    }
        
    
    private static int getHistogramEnd(int[] histogram) {
        
        int count = 0;
        for(int i=0; i<histogram.length; i++) {
            if(histogram[i] == 0)
                break;
            count++;
        }        
        return count;
    }
    
    
    // 0, need to delete
    private static void printHistogram(int[] histogram) {
        int count = 0;
        for(int i=0; i<histogram.length; i++) {
            System.out.println(i + ": " + histogram[i]);
            count += histogram[i];
        }        
    }
}