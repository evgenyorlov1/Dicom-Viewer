package dicomanalyser;


import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.display.SingleImagePanel;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;
import com.pixelmed.dicom.CompressedFrameDecoder;
import java.io.File;
import com.pixelmed.display.ConsumerFormatImageMaker;
import javax.swing.JFrame;
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
        convert();
    }
    
    private static void file() {
        String path = System.getProperty("user.dir") + "/dicomImigies/IM-0001-0001.dcm";
        AttributeList list = new AttributeList();
        try {
            list.setDecompressPixelData(false);
            list.read(path);      
            
            System.out.println(Attribute.getDelimitedStringValuesOrEmptyString(list, TagFromName.FileMetaInformationGroupLength));
            System.out.println(list.toString());
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
            SingleImagePanel panel = new SingleImagePanel(sImg);            
            
            System.out.println("3");
            frame.add(panel);
            
            System.out.println("4");
            frame.setSize(sImg.getWidth(), sImg.getHeight());
            
            System.out.println("5");
            frame.setVisible(true);
        } catch(Exception e) {
            System.out.println(e);
        }       
    }
    
    private static void convert() {
        try {
            String path = System.getProperty("user.dir") + "/dicomImigies/IM-0001-0001.dcm";
            String output = System.getProperty("user.dir") + "/dicomImigies/test.jpg";
            ConsumerFormatImageMaker.convertFileToEightBitImage(path, output, "jpg", 0);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
   
}
