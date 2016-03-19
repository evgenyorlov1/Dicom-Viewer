package dicomanalyser;


import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.display.SingleImagePanel;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;
import com.pixelmed.dicom.CompressedFrameDecoder;
import com.pixelmed.dicom.DicomException;
import java.io.File;
import com.pixelmed.display.ConsumerFormatImageMaker;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
        file();
        //imagePane();
        //convert();
        //imagePixel();
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
        //File img = new File(path);
        //System.out.println(CompressedFrameDecoder.canDecompress(img));
        JFrame frame = new JFrame(); 
        SourceImage sImg;
        try {                                               
            System.out.println("1");
            sImg = new SourceImage(path);                                    
           
            System.out.println("2");
            //SingleImagePanel panel = new SingleImagePanel(sImg);            
                       
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
 
    
    private static void imagePixel() {
        try {
            String path = System.getProperty("user.dir") + "/dicomImigies/IM-0001-0001.dcm";
            SourceImage sImg;
            
            //JFrame frame = new JFrame(); 
            
            sImg = new SourceImage(path);
            //System.out.println(sImg.);
            BufferedImage bImg = sImg.getBufferedImage();
            
            //frame.getContentPane().add(new JLabel(new ImageIcon(bImg)));
            //frame.pack();
            //frame.setVisible(true);
            
            System.out.println("Height: " + bImg.getHeight());
            System.out.println("Width: " + bImg.getWidth());
                        
            int color = bImg.getRGB(150, 150);
            
            //returns valid rgb
            System.out.println("Color: " + color);            
            System.out.println("Red1: " + ((color & 0x00ff0000) >> 16));
            System.out.println("Green1: " + ((color & 0x0000ff00) >> 8));
            System.out.println("Blue1: " + ((color & 0x000000ff)));
            
            //returns valid rgb
            Color col = new Color(bImg.getRGB(150, 150));
            int red = col.getRed();
            int blue = col.getGreen();
            int green = col.getBlue();
            
            System.out.println("Red2: " + red);
            System.out.println("Green2: " + blue);
            System.out.println("Blue2: " + green);
        } catch(Exception e) {
            System.out.println("Error:" + e);
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
}
