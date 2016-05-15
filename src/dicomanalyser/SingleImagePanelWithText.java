/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicomanalyser;

import com.pixelmed.display.SingleImagePanel;
import java.awt.Font;
import java.awt.Color;
import com.pixelmed.display.SourceImage;
import com.pixelmed.event.EventContext;

/**
 *
 * @author pc
 */
public class SingleImagePanelWithText extends SingleImagePanel {
    
    private float sliceLocation;
    private int instanceNumber;
    
    public SingleImagePanelWithText(SourceImage si) {                
        super(si);
        
        //this.setSideAndViewAnnotationString(getTextToDisplay(), 30, "SansSerif", 
        //        Font.BOLD, 14, Color.WHITE, true);
    }
    
    
    private String getTextToDisplay() {
        return "slice Location: " + this.sliceLocation + "\n; instance Number: " + this.instanceNumber;
    }
    
}
