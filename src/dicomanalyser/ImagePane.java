/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicomanalyser;

import com.pixelmed.display.*;
import java.util.ArrayList;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author pc
 */
public class ImagePane extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     * @param dcmFiles
     */
    public ImagePane(DICOMStore dcmFiles) {
        setResizable(false);
        setVisible(true);
        initComponents(dcmFiles);                                           
        
        tSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {   
                JSlider source = (JSlider) e.getSource();
                if(!source.getValueIsAdjusting()) {                    
                    DICOMImage image = dcmFiles.get(zSlider.getValue(), tSlider.getValue()); 
                    dcmPanel.dirty(image.si);                    
                    dcmPanel.repaint();
                }
            }                        
        });
        
        zSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if(!source.getValueIsAdjusting()) {                    
                    adjustTslider(zSlider.getValue());
                    DICOMImage image = dcmFiles.get(zSlider.getValue(), tSlider.getValue());                    
                    dcmPanel.dirty(image.si);               
                    dcmPanel.repaint();
                }
            }
            
            private void adjustTslider(int value) {
                
                tSlider.setValue(0);
                tSlider.setMinimum(0);        
                tSlider.setMaximum(dcmFiles.getTcount(value)); 
                tSlider.setMajorTickSpacing(1);                
                tSlider.setPaintTicks(true);                                
            }
        });        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents(DICOMStore dcmFiles) {
                
        jMenu7 = new javax.swing.JMenu();
        tSlider = new javax.swing.JSlider();
        zSlider = new javax.swing.JSlider();
        
        try {
            dcmPanel = new SingleImagePanel(dcmFiles.get(0, 0).si);
        } catch(Exception e) {System.out.println("ssdfsdf");}                
        
        
        //---------------------------------------------------------------------
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();

        jMenu7.setText("jMenu7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        tSlider.setToolTipText("Instance number");

        zSlider.setBackground(new java.awt.Color(52, 49, 255));
        zSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        zSlider.setToolTipText("Slice location");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(dcmPanel);
        dcmPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("View");
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Series");
        jMenuBar1.add(jMenu4);

        jMenu5.setText("Tools");
        jMenuBar1.add(jMenu5);

        jMenu6.setText("Help");
        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(zSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 796, Short.MAX_VALUE)
                    .addComponent(dcmPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(zSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                    .addComponent(dcmPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
                                           
        zSlider.setMinimum(0); 
        zSlider.setMaximum(dcmFiles.getZcount()); // issue with extra one   
        zSlider.setValue(0); 
        zSlider.setMajorTickSpacing(1);        
        zSlider.setPaintTicks(true);   
        
        tSlider.setMinimum(0);                 
        tSlider.setMaximum(dcmFiles.getTcount(0)); // adjust to z
        tSlider.setValue(0); 
        tSlider.setPaintTicks(true);
        tSlider.setMajorTickSpacing(1);                        
        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify                     
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private SingleImagePanel dcmPanel;
    private javax.swing.JSlider tSlider; //t
    private javax.swing.JSlider zSlider; //z
    // End of variables declaration                   
}
