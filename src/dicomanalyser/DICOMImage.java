/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicomanalyser;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;

/**
 *
 * @author pc
 */
public class DICOMImage {      
    public String path;
    public int instanceNumber;  
    public float sliceLocation;    
    public SourceImage si;
    
    
    public DICOMImage(String path) {
        try {
            this.path = path;
            this.instanceNumber = Integer.valueOf(tagValue(this.path, 
                    TagFromName.InstanceNumber));
            this.sliceLocation = Float.valueOf(tagValue(this.path, 
                    TagFromName.SliceLocation));
            this.si = new SourceImage(path); 
        } catch(Exception e) {System.out.println("DICOMImage.DICOMImage error: " + e);}
    }
    
    
    private String tagValue(String dcmFile, AttributeTag tag) {        
        String tagValue = null;
        try {
            AttributeList list = new AttributeList();
            list.setDecompressPixelData(false);
            list.read(dcmFile);              
            tagValue = Attribute.getDelimitedStringValuesOrEmptyString(list, tag); 
            return tagValue;
        } catch(Exception e) {System.out.println("DICOMImage.tagValue error: " + e);}
        return tagValue;
    }
}
