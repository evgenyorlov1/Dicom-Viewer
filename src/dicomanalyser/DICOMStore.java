/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicomanalyser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author pc
 */
public class DICOMStore {
    
    
    public ArrayList<Float> uniqueSliceLocations = new ArrayList<>();   
    public ArrayList<DICOMImage> dcmImages = new ArrayList<>();    
    public Map<Float, ArrayList<Integer>> instanceNumbersBySlice = new LinkedHashMap<>();
    
    
    public void add(DICOMImage image) {
        
        try {
            dcmImages.add(image);                        
            if(!uniqueSliceLocations.contains(image.sliceLocation)) {                
                uniqueSliceLocations.add((float) image.sliceLocation);
                TagSorter.insertionSortFloat(uniqueSliceLocations);                
            }            
        } catch(Exception e) {System.out.println("DICOMStore.add error: " + e);}
    }
    
    
    public void sortBySlice() {
        try {
            for(int i=0; i<uniqueSliceLocations.size(); i++) {
                ArrayList<Integer> instancesPerSlice = new ArrayList<>();
                for(int j=0; j<dcmImages.size(); j++) {                
                    if(Float.compare(dcmImages.get(j).sliceLocation, 
                            uniqueSliceLocations.get(i)) == 0) {
                        instancesPerSlice.add(dcmImages.get(j).instanceNumber);                    
                    }
                }
                TagSorter.insertionSortInteger(instancesPerSlice);
                instanceNumbersBySlice.put(uniqueSliceLocations.get(i), 
                        instancesPerSlice);
            }
        } catch(Exception e) {System.out.println("DICOMStore.sortBySlice error: " + e);}
    }
    
    
    public DICOMImage get(int sliceLocationIndex, int instanceNumberIndex) {                        
        
        try {
            float sliceLocation = uniqueSliceLocations.get(sliceLocationIndex);        
            int instanceNumber = instanceNumbersBySlice.get(sliceLocation).
                    get(instanceNumberIndex);

            for(int i=0; i<dcmImages.size(); i++) {
                DICOMImage image = dcmImages.get(i);
                if(Float.compare(image.sliceLocation, sliceLocation) == 0 && 
                        (image.instanceNumber == instanceNumber)) {
                    System.out.println("-----------");
                    System.out.println("instance: " + image.instanceNumber);
                    System.out.println("slice: " + image.sliceLocation);
                    return image;
                }            
            }         
        } catch(Exception e) {System.out.println("DICOMStore.get error: " + e);}
        
        return get(0,0);
    }
    
    
    public int getZcount() {
        
        return uniqueSliceLocations.size() - 1;
    }
    
    
    public int getTcount(int sliceLocationIndex) {
        
        int numberOfInstances = 0;
        float sliceLocation = uniqueSliceLocations.get(sliceLocationIndex);
        numberOfInstances = instanceNumbersBySlice.get(sliceLocation).size();
        return numberOfInstances - 1;
    }
}
