/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicomanalyser;

import com.pixelmed.dicom.DicomException;
import com.pixelmed.display.SourceImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author pc
 */
public class DICOMStore {
    public HashSet z = new HashSet<>(); //sliceLocation    
    public ArrayList<DICOMImage> dcmImages = new ArrayList<>();
    
    
    public void add(DICOMImage image) {
        try {
            dcmImages.add(image);            
            int instanceNumber = image.instanceNumber;              
            z.add(image.sliceLocation);
        } catch(Exception e) {System.out.println("DICOMStore.add error: " + e);}
    }
    
    public DICOMImage getOne() throws IOException, DicomException {
        
        return dcmImages.get(0);
    }
    
    public DICOMImage get(int sliceLocation, int instanceNumber) {
        
        ArrayList instanceBySlice = new ArrayList();        
        sliceLocation -= 1; // change slider to 0 and delete
        instanceNumber -= 1; // change slider to 0 and delete
        ArrayList list = new ArrayList(z); // move to header          
        TagSorter.insertionSort(list); //move to .add
        
        float slice = (float) list.get(sliceLocation); //
        for(int i=0; i<dcmImages.size(); i++) {
            DICOMImage image = dcmImages.get(i);            
            if((Float.compare(slice, image.sliceLocation) == 0)) {                
                instanceBySlice.add(image.instanceNumber);                
            }
        }
        
        TagSorter.insertionSortInteger(instanceBySlice);
        for(int i=0; i<dcmImages.size(); i++) {
            DICOMImage image = dcmImages.get(i);
            if((Float.compare(slice, image.sliceLocation) == 0) && 
                    (image.instanceNumber == (int) instanceBySlice.get(instanceNumber))) {
                System.out.println("------------------------");
                System.out.println(image.instanceNumber);
                System.out.println(image.sliceLocation);
                return image;
            }
        }
        
        return dcmImages.get(3);
    }
    
    
    public int getZ() {
        //z size
        return z.size();
    }
    
    
    public int getT(float sliceLocation) {
        // t size per each z
        int num = 0;
        for(int i=0; i<dcmImages.size(); i++) {
            if(sliceLocation == dcmImages.get(i).sliceLocation)
                num++;
        }
        return num;
    }
    
    
    public int getMinT(float sliceLocation) {
        int instanceNumber = 0;
        for(int i=1; i<dcmImages.size(); i++) {
            
        }
        return 0;
    }
}
