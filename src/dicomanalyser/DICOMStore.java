/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicomanalyser;

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
    
    
    public DICOMImage get(float sliceLocation, int instanceNumber) {
        for(int i=0; i<dcmImages.size(); i++) {
            DICOMImage image = dcmImages.get(i);
            if((instanceNumber == image.instanceNumber) && (sliceLocation == image.sliceLocation)) {
                return image;
            }
        }
        return dcmImages.get(3);
    }
    
    
    public int getZ() {
        return z.size();
    }
    
    
    public int getT(float sliceLocation) {
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
