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
import java.io.IOException;

/**
 *
 * @author pc
 */
public class HistogramView {
    
    
    public static void main(String[] args) throws IOException, DicomException {
        
        String path = System.getProperty("user.dir") + 
                "/dicomImigies/alkhimova/030010.dcm"; 
        SourceImage sImg = new SourceImage(path);         
        BufferedImage original = sImg.getBufferedImage();  
        int[] histogram = imageHistogram(original);
    }
    
    
    private static int[] imageHistogram(BufferedImage input) {
        
        int[] histogram = new int[65536]; //16384
        int count = 0;
        
        for(int i=0; i<histogram.length; i++) histogram[i] = 0;
        for(int i=0; i<input.getWidth(); i++) {
            for(int j=0; j<input.getHeight(); j++) {
                int color = new Color(input.getRGB (i, j)).getRGB(); 
                Color col = new Color(input.getRGB (i, j));
                System.out.println(col.toString());
                count++;
                //histogram[color]++;
            }
        }
        System.out.println(count);
        System.out.println(input.getWidth());
        System.out.println(input.getHeight());
        return histogram;
    }
    
}
