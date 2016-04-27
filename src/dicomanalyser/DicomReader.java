package dicomanalyser;


import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.display.SingleImagePanel;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;
import com.pixelmed.dicom.CompressedFrameDecoder;
import com.pixelmed.dicom.DicomException;
import java.util.ArrayList;
import java.io.File;
import java.util.Collections;
import com.pixelmed.display.ConsumerFormatImageMaker;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.vecmath.Tuple3d;
/**
 *
 * @author pc
 */
public class DicomReader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //file();
        //imagePane();
        //convert();
        otsuThresholding();
    }
    
    
    private static void file() {
        String path = System.getProperty("user.dir") + "/dicomImigies/img/IM-0001-0003.dcm";
        AttributeList list = new AttributeList();
        try {
            list.setDecompressPixelData(false);
            list.read(path);      
            
            System.out.println(Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.AcquisitionDate));
            System.out.println(Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.Time));
            System.out.println("Rows: " + Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.Rows));
            System.out.println("Colums: " + Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.Columns));
            System.out.println("Pixel spacing: " + Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.PixelSpacing));
            System.out.println(Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.InstanceNumber));
            //System.out.println(list.toString());
            
            String path01 = System.getProperty("user.dir") + "/dicomImigies/IM-0001-0001.dcm";
            AttributeList list01 = new AttributeList();
            list01.setDecompressPixelData(false);
            list01.read(path01);      
            System.out.println("IM01 date: " + Attribute.getDelimitedStringValuesOrEmptyString(list01, TagFromName.AcquisitionDate));            
            System.out.println("IM01 number: " + Attribute.getDelimitedStringValuesOrEmptyString(list01, TagFromName.InstanceNumber));
                        
            String path0361 = System.getProperty("user.dir") + "/dicomImigies/IM-0001-0361.dcm";
            AttributeList list0361 = new AttributeList();
            list0361.setDecompressPixelData(false);
            list0361.read(path0361);      
            System.out.println("IM0361 date: " + Attribute.getDelimitedStringValuesOrEmptyString(list0361, TagFromName.AcquisitionDate));            
            System.out.println("IM0361 number: " + Attribute.getDelimitedStringValuesOrEmptyString(list0361, TagFromName.InstanceNumber));
            
        } catch(Exception e) {
            System.out.println(e);
        }        
    }
    
    
    private static void imagePane() {
        String path = System.getProperty("user.dir") + "/dicomImigies/IM-0001-0001.dcm";
        //String path = System.getProperty("user.dir") + "/dicomImigies/CENOVIX.zip";
        File img = new File(path);
        //System.out.println(CompressedFrameDecoder.canDecompress(img));
        JFrame frame = new JFrame(); 
        SourceImage sImg;
        try {                                               
            System.out.println("1");
            sImg = new SourceImage(path);                                    
           
            System.out.println("2");
            SingleImagePanel panel = new SingleImagePanel(sImg);            
                       
            System.out.println("3");
            //frame.add(panel);
            
            System.out.println("4");
            //frame.setSize(sImg.getWidth(), sImg.getHeight());
            
            System.out.println("5");
            //frame.setVisible(true);
        } catch(Exception e) {
            System.out.println(e);
        }       
    }
    
    
    private static void convert() {
        try {
            //doesnt work need vertex
            String path = System.getProperty("user.dir") + "/dicomImigies/IM-0001-0001.dcm";
            String output = System.getProperty("user.dir") + "/dicomImigies/test.jpg";
            ConsumerFormatImageMaker.convertFileToEightBitImage(path, output, "jpg", 0);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
 
    
    private static void fileSort(String[] files) {
        for(int i = 0; i < files.length; i++) {
            //sort file path's before thresholding(time tags are epsent)
            try {
                AttributeList list = new AttributeList();
                list.setDecompressPixelData(false);
                list.read(files[i]);    
                Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.InstanceNumber);
            } catch(Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }
    
    
    private static void otsuThresholding() {
        //will have issues with 2 or 1 colored image
        try {
            String path = System.getProperty("user.dir") + "/dicomImigies/IM-0001-0361.dcm"; //IM-0001-0001.dcm
            SourceImage sImg = new SourceImage(path); 
            BufferedImage bImg = sImg.getBufferedImage(); 
            ArrayList<Object[]> histogram = new ArrayList<Object[]>(); //histogram  
            float[] withinClassVariance = new float[2]; //holds minimum class variance and index
                        
            //create histogram
            ArrayList numOfPixels = new ArrayList(); //number of pixels
            ArrayList numOfColors = new ArrayList(); //number of grey colors
            for(int i=0; i<bImg.getHeight(); i++) {
                for(int j=0; j<bImg.getWidth(); j++) {
                    numOfPixels.add(bImg.getRGB(j, i));
                    if(!numOfColors.contains(bImg.getRGB(j, i))) {
                        numOfColors.add(bImg.getRGB(j, i));
                    }
                }
            }            
            //histogram itself
            //SORT GREY COLORS!!
            for(int i=0; i<numOfColors.size(); i++) {
                Object[] temp = new Object[2];
                temp[0] = numOfColors.get(i); //pixel rgb                
                temp[1] = Collections.frequency(numOfPixels, numOfColors.get(i)); //frequency
                Color c = new Color((int)temp[0], true);
                /*System.out.println(c.getRed());
                System.out.println(c.getGreen());
                System.out.println(c.getBlue());
                System.out.println((int)temp[0]);
                System.out.println("----------");*/
                histogram.add(temp);                
            }
            
            //otsu algorythm, asume that there is distinct foreground and background
            for(int T=0; T<histogram.size()-1; T++) {
                //Background                
                int weightB_temp = 0;
                int meanB_temp = 0;                
                for(int j=0; j<=T; j++) {
                    weightB_temp += (int) histogram.get(j)[1];                    
                    meanB_temp += j*(int) histogram.get(j)[1];
                }                               
                float WeightB = (float)weightB_temp/numOfPixels.size(); //pixels weight                
                float MeanB = (float)meanB_temp/weightB_temp; //pixels mean                                                                            
                //background variance
                float varianceB_temp = 0; 
                for(int j=0; j<=T; j++) {                                        
                    varianceB_temp += (j-MeanB)*(j-MeanB)*(int) histogram.get(j)[1];
                }
                float VarianceB = varianceB_temp/weightB_temp;               
                
                //Foreground
                int weightF_temp = 0;
                int meanF_temp = 0;
                for(int j=T+1; j<histogram.size(); j++) {                 
                    weightF_temp += (int) histogram.get(j)[1];
                    meanF_temp += j*(int) histogram.get(j)[1];
                }                
                float WeightF = (float)weightF_temp/numOfPixels.size();
                float MeanF = (float)meanF_temp/weightF_temp;  
                //foreground variance
                float varianceF_temp = 0;
                for(int j=T+1; j<histogram.size(); j++) {
                    varianceF_temp += (j-MeanF)*(j-MeanF)*(int) histogram.get(j)[1];                    
                }
                float VarianceF = varianceF_temp/weightF_temp;                
                
                //Within Class Variance                
                if(T!=0) {                    
                    float withinClassVariance_temp = WeightB*VarianceB + WeightF*VarianceF;
                    if(withinClassVariance_temp < withinClassVariance[1]) {
                        withinClassVariance[0] = T; //minimum T index
                        withinClassVariance[1] = withinClassVariance_temp; //WCV value                        
                    }                    
                } else {                        
                    withinClassVariance[0] = T; //minimum T index
                    withinClassVariance[1] = WeightB*VarianceB + WeightF*VarianceF; //WCV value                    
                }                
            }
            
            //doesn't work
            BufferedImage finalImage = new BufferedImage(bImg.getWidth(), 
                    bImg.getHeight(), BufferedImage.TYPE_INT_RGB);
            
            for(int i=0; i<bImg.getHeight(); i++) {
                for(int j=0; j<bImg.getWidth(); j++) {
                    int rgb = bImg.getRGB(j, j);    
                    
                    for(int position=0; position<histogram.size(); position++) {                        
                        if(rgb==(int)histogram.get(position)[0]) {
                            if(position<=(int)withinClassVariance[0])
                                rgb = Color.BLACK.getRGB();
                            else
                                rgb = Color.WHITE.getRGB();
                        }
                    }
                    
                    finalImage.setRGB(j, i, rgb);
                }
            }
            File outputFile = new File("image.jpg");
            ImageIO.write(finalImage, "jpg", outputFile);
            System.out.println("Done");
        } catch(Exception e) {System.out.println("Error:" + e);}                
    }
    
    
    private static ArrayList<Object[]> otsuHistogram(BufferedImage bImg) {
        ArrayList<Object[]> histogram = new ArrayList<Object[]>(); //histogram  
        //create histogram
        ArrayList numOfPixels = new ArrayList(); //number of pixels
        ArrayList numOfColors = new ArrayList(); //number of grey colors
        for(int i=0; i<bImg.getHeight(); i++) {
            for(int j=0; j<bImg.getWidth(); j++) {
                numOfPixels.add(bImg.getRGB(j, i));
                if(!numOfColors.contains(bImg.getRGB(j, i))) {
                    numOfColors.add(bImg.getRGB(j, i));
                }
            }
        }            
        //histogram itself
        //DO I NEED TO SORT GREY COLORS?
        for(int i=0; i<numOfColors.size(); i++) {
            Object[] temp = new Object[2];
            temp[0] = numOfColors.get(i); //pixel rgb                
            temp[1] = Collections.frequency(numOfPixels, numOfColors.get(i)); //frequency
            Color c = new Color((int)temp[0], true);            
            histogram.add(temp);                
            }
        return histogram;
    }
    
    
    private static void otsuWithinClassVariance(ArrayList<Object[]> histogram, int numOfPixels) {
        float[] withinClassVariance = new float[2]; //holds minimum class variance and index
        
        for(int T=0; T<histogram.size()-1; T++) {
            //Background                
            int weightB_temp = 0;
            int meanB_temp = 0;                
            for(int j=0; j<=T; j++) {
                weightB_temp += (int) histogram.get(j)[1];                    
                meanB_temp += j*(int) histogram.get(j)[1];
            }                               
            float WeightB = (float)weightB_temp/numOfPixels; //pixels weight                
            float MeanB = (float)meanB_temp/weightB_temp; //pixels mean                                                                            
            //background variance
            float varianceB_temp = 0; 
            for(int j=0; j<=T; j++) {                                        
                varianceB_temp += (j-MeanB)*(j-MeanB)*(int) histogram.get(j)[1];
            }
            float VarianceB = varianceB_temp/weightB_temp;               
                
            //Foreground
            int weightF_temp = 0;
            int meanF_temp = 0;
            for(int j=T+1; j<histogram.size(); j++) {                 
                weightF_temp += (int) histogram.get(j)[1];
                meanF_temp += j*(int) histogram.get(j)[1];
            }                
            float WeightF = (float)weightF_temp/numOfPixels;
            float MeanF = (float)meanF_temp/weightF_temp;  
            //foreground variance
            float varianceF_temp = 0;
            for(int j=T+1; j<histogram.size(); j++) {
                varianceF_temp += (j-MeanF)*(j-MeanF)*(int) histogram.get(j)[1];                    
            }
            float VarianceF = varianceF_temp/weightF_temp;                
                
            //Within Class Variance                
            if(T!=0) {                    
                float withinClassVariance_temp = WeightB*VarianceB + WeightF*VarianceF;
                if(withinClassVariance_temp < withinClassVariance[1]) {
                    withinClassVariance[0] = T; //minimum T index
                    withinClassVariance[1] = withinClassVariance_temp; //WCV value                        
                }                    
            } else {                        
                withinClassVariance[0] = T; //minimum T index
                withinClassVariance[1] = WeightB*VarianceB + WeightF*VarianceF; //WCV value                    
            }                
        }
    }
    
    
    private static void thresholdImage() {
        
    }
    
}