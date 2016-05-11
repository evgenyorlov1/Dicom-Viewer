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
public class EntropyAlgorithm {
    
    
    public static void main(String[] args) throws IOException, DicomException {
        
        String path = System.getProperty("user.dir") + 
                "/dicomImigies/img/IM-0001-0003.dcm";        
        SourceImage sImg = new SourceImage(path);         
        BufferedImage original = sImg.getBufferedImage();
        BufferedImage binarized = binarize(original);
        writeImage(binarized);
    }
 
    
    // Return histogram of grayscale image
    private static float[] imageHistogram(BufferedImage input) {
        
        float[] histogram = new float[256];
        int N = 0;
        for(int i=0; i<histogram.length; i++) histogram[i] = 0;
        for(int i=0; i<input.getWidth(); i++) {
            for(int j=0; j<input.getHeight(); j++) {
                int red = new Color(input.getRGB (i, j)).getRed();                
                histogram[red]++;
                N++;
            }
        } 
        for(int i=0; i<histogram.length; i++) {
            histogram[i] /=  N; 
        }              
        return histogram;
    }
    
    
    // DOIT
    private static int entropyTreshold(BufferedImage original) {
         
        float[] histogram = imageHistogram(original);        
        int threshold = 0;                         
        float sA = calculateEntropy(0, histogram, true);
        float sB = calculateEntropy(0, histogram, false);        
        float entropy = sA + sB;
        System.out.println("entropy: " + entropy);
        for(int i=1; i<histogram.length; i++) {
            sA = calculateEntropy(i, histogram, true);
            sB = calculateEntropy(i, histogram, false);
            if(sA+sB > entropy) {
                threshold = i;
                entropy = sA + sB;
                System.out.println("entropy: " + entropy);
            }
        }
        System.out.println("Final entropy: " + entropy);
        System.out.println("Threshold: " + threshold);
        return threshold; 
    }       
    
    
    private static float calculateEntropy(int index, float[] histogram, 
            boolean isSa) {
        
        float entropy = 0;
        float probability = 0;
        if(isSa) {
            for(int i=0; i<=index; i++) {
                probability += histogram[i];
            }
            for(int i=0; i<=index; i++) {                
                entropy += (histogram[i]/probability)*
                        Math.log(histogram[i]/probability);
            }
            System.out.println("true: " + entropy);
        } else {
            for(int i=histogram.length-1; i>index; i--) {
                probability += histogram[i];
            }
            for(int i=histogram.length-1; i>index; i--) {
                entropy += (histogram[i]/probability)*
                        Math.log(histogram[i]/probability);
            }
            System.out.println("false: " + entropy);
        }        
        return (-entropy);
    }
    
    
    private static BufferedImage binarize(BufferedImage original) {
        
        int red;
        int newPixel;
        float threshold = entropyTreshold(original);  // can very
        System.exit(0);
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
        
        File file = new File("image333.jpg");
        ImageIO.write(binarized, "jpg", file);
        System.out.println("Done");        
    }                
    
    
    // 0, need to delete
    private static void printHistogram(float[] histogram) {
        
        for(int i=0; i<histogram.length; i++) {
            System.out.println(i + ": " + histogram[i]);            
        }        
    }
}

