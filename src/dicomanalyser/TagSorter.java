/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicomanalyser;

/**
 *
 * @author pc
 */
import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.TagFromName;
import java.util.ArrayList;


public class TagSorter {
    
    
    public static ArrayList<String[]> tagSort(String[] dcmFiles) {         
        ArrayList<String[]> sortedDcm = new ArrayList<String[]>();                           
        try {      
            dcmFiles = insertionSort(dcmFiles, TagFromName.SliceLocation); //sort by Slice location            
            int slicer = Integer.valueOf(tagValue(dcmFiles[0], TagFromName.InstanceNumber));
            for(int i=1; i<dcmFiles.length; i++) {                
                int slicer_temp = Integer.valueOf(tagValue(dcmFiles[i], TagFromName.InstanceNumber));
                if(slicer != slicer_temp) {                    
                    String[] slicedFiles = sliceFiles(dcmFiles, TagFromName.InstanceNumber, slicer);                    
                    slicedFiles = insertionSort(slicedFiles, TagFromName.InstanceNumber);   
                    sortedDcm.add(slicedFiles);
                    slicer = slicer_temp;
                }                
            }            
            return sortedDcm;            
        } catch(Exception e) {System.out.println("TagSorter.sort error: " + e);}
        return sortedDcm;
    }
    
    
    private static String[] insertionSort(String[] dcmFiles, AttributeTag tag) {  
        //System.out.println("TagSorter.insertionSort");
        try {
            for(int i=1; i<dcmFiles.length; i++) {                               
                int j = i;
                while(j>0) {                    
                    float sliceLocation_current = Float.valueOf(tagValue(dcmFiles[j], tag));
                    float sliceLocation_previous = Float.valueOf(tagValue(dcmFiles[j-1], tag));                      
                    if(sliceLocation_previous > sliceLocation_current) {
                        String dcmFile_temp  = dcmFiles[j-1];
                        dcmFiles[j-1] = dcmFiles[j];
                        dcmFiles[j] = dcmFile_temp;
                    }
                    j -= 1;
                }                                                
            }   
            return dcmFiles;
        } catch(Exception e) {System.out.println("TagSorter.insertionSort error: " + e);}        
        return null;
    }
    
    
    private static String tagValue(String dcmFile, AttributeTag tag) {
        //System.out.println("TagSorter.tagValue");
        String tagValue = null;
        try {
            AttributeList list = new AttributeList();
            list.setDecompressPixelData(false);
            list.read(dcmFile);                
            tagValue = Attribute.getDelimitedStringValuesOrEmptyString(list, tag); 
            return tagValue;
        } catch(Exception e) {System.out.println("TagSorter.tagValue error: " + e);}
        return tagValue;
    }
    
    
    private static void showFiles(String[] dcmFiles, AttributeTag tag) {
        //System.out.println("TagSorter.showFiles");
        for(int i=0; i<dcmFiles.length; i++) {
            System.out.println(tagValue(dcmFiles[i], tag));
        }
    } 
    
    
    private static <Type> String[] sliceFiles(String[] dcmFiles, AttributeTag tag, Type slicer) {
        //System.out.println("TagSorter.sliceFiles");
        String[] slicedFiles = null;
        try {
            int j = 0;
            for(int i=0; i<=dcmFiles.length; i++) {
                Type value_temp = (Type)tagValue(dcmFiles[i], tag);
                if(value_temp == slicer) {
                    slicedFiles[j] = dcmFiles[i];
                    j++;
                }
            }
            return slicedFiles;
        } catch(Exception e) {System.out.println("TagSorter.sliceFiles error: " + e);}
        return slicedFiles;
    }
}