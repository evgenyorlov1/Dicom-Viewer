package dicomanalyser;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class DemoJFileChooser extends JPanel
    implements ActionListener {
    JButton go;
   
    JFileChooser chooser;
    String choosertitle;
   
    public DemoJFileChooser() {
    go = new JButton("Do it");
    go.addActionListener(this);
    add(go);
   }

public void actionPerformed(ActionEvent e) {
    int result;
    String[] dcmFiles; //path to imagies. It is returned
        
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
        System.out.println("getCurrentDirectory(): " 
            +  chooser.getCurrentDirectory());
        System.out.println("getSelectedFile() : " 
            +  chooser.getSelectedFile());
        dcmFiles = chooser.getSelectedFile().list();
        //sort dcmFiles based on InstanceNumber
        //chooser.getSelectedFile() - current folder
        //dcmFiles[1] - file        
        
        System.out.println(chooser.getSelectedFile()+"/" + dcmFiles[1]);
        for(int i = 0; i < dcmFiles.length; i++) {
            dcmFiles[i] = chooser.getSelectedFile() + "/" + dcmFiles[i]; //make valid path            
        }        
    }
    else {
        System.out.println("No Selection ");
    }
}
   
    public Dimension getPreferredSize(){
        return new Dimension(200, 200);
    }
    
public static void main(String s[]) {
    JFrame frame = new JFrame("");
    DemoJFileChooser panel = new DemoJFileChooser();
    frame.addWindowListener(
        new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            System.exit(0);
            }
        }
      );
    frame.getContentPane().add(panel,"Center");
    frame.setSize(panel.getPreferredSize());
    frame.setVisible(true);
    }
}