/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmbeddedDVSSerialPortChooserFactory.java
 *
 * Created on Jul 19, 2011, 5:09:24 AM
 */
package net.sf.jaer.hardwareinterface.serial;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import net.sf.jaer.chip.AEChip;
import net.sf.jaer.hardwareinterface.HardwareInterface;
import net.sf.jaer.hardwareinterface.HardwareInterfaceChooserFactory;
import net.sf.jaer.hardwareinterface.HardwareInterfaceException;
import net.sf.jaer.hardwareinterface.HardwareInterfaceFactoryInterface;
import net.sf.jaer.hardwareinterface.serial.eDVS128.eDVS128_HardwareInterface;
import net.sf.jaer.hardwareinterface.serial.edvsviewer.HWP_RS232;
import net.sf.jaer.hardwareinterface.serial.edvsviewer.HWPort.PortAttribute;
import net.sf.jaer.hardwareinterface.serial.edvsviewer.HWPort.PortIdentifier;

/**
 * Allows choice of serial port for eDVS interface.
 * @author tobi
 */
public class EmbeddedDVSSerialPortChooserFactory extends javax.swing.JDialog implements HardwareInterfaceChooserFactory {

    private static final Logger log = Logger.getLogger("USBIOHardwareInterfaceFactory");
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;
    private HWP_RS232 portRS232 = null;
    private Preferences prefs = Preferences.userNodeForPackage(EmbeddedDVSSerialPortChooserFactory.class);
    int lastPortIndex = prefs.getInt("EmbeddedDVSSerialPortChooserFactory.lastPortIndex", 0);
    private List<PortAttribute> lpa = null;
    private List<PortIdentifier> lid = null;
    // singleton
    private static EmbeddedDVSSerialPortChooserFactory instance = new EmbeddedDVSSerialPortChooserFactory();
    private eDVS128_HardwareInterface chosenInterface = null;

    /** Creates new form EmbeddedDVSSerialPortChooserFactory */
    private EmbeddedDVSSerialPortChooserFactory() {
        super();
        portRS232 = new HWP_RS232();
        setModal(true);
        initComponents();

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        refreshPortList();
    }

    /** Use this singleton instance to make new interfaces */
    public static HardwareInterfaceFactoryInterface instance() {
        return instance;
    }

    /** Always returns 0. */
    @Override
    public int getNumInterfacesAvailable() {
        return 0;

    }

    /** Always returns null */
    @Override
    public HardwareInterface getFirstAvailableInterface() throws HardwareInterfaceException {
        return null;
    }

    /** Always returns null */
    @Override
    public HardwareInterface getInterface(int n) throws HardwareInterfaceException {
        return null;
    }

    @Override
    public String getGUID() {
        return "eDVS Serial port interface chooser";
    }

    @Override
    public JDialog getInterfaceChooser(AEChip chip) {
        return this;
    }

    @Override
    public HardwareInterface getChosenHardwareInterface() {
        return chosenInterface;
    }

    private void refreshPortList() {
        portCB.removeAllItems();
        // add available COM ports to menu
        lid = portRS232.getPortIdentifierList();
        for (PortIdentifier id : lid) {
            portCB.addItem(id);
        }
        if (lastPortIndex >= portCB.getItemCount()) {
            lastPortIndex = portCB.getItemCount() - 1;
        }
        portCB.setSelectedIndex(lastPortIndex);

    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    public PortIdentifier getPortIdentifier() {
        return (PortIdentifier) portCB.getSelectedItem();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        portCB = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        setTitle("Serial Port Chooser");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        portCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        portCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portCBActionPerformed(evt);
            }
        });

        jLabel1.setText("<html>Choose the serial port of the eDVS.<br>It is usually the first of a large numbered pair of ports.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton))
                    .addComponent(portCB, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(portCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(okButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void portCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portCBActionPerformed
        Object o = portCB.getSelectedItem();
        if (o instanceof PortIdentifier) {
            PortIdentifier pi = (PortIdentifier) o;
            if (pi.toString().contains("-rescan-")) {
                refreshPortList();
            }
        }
    }//GEN-LAST:event_portCBActionPerformed

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        if (retStatus == RET_OK) {
            // make interface based on chosen serial port
            Object o = portCB.getSelectedItem();
            if (o == null || !(o instanceof PortIdentifier)) {
                log.warning("Selected item " + o + " is not a PortIdentifier, can't use it to make eDVS128_HardwareInterface");
                chosenInterface=null;
            } else {
                lastPortIndex = portCB.getSelectedIndex();
                prefs.putInt("EmbeddedDVSSerialPortChooserFactory.lastPortIndex", lastPortIndex);
                PortIdentifier pi = (PortIdentifier) o;
                if (!(pi.getID() instanceof String)) {
                    log.warning("Port ID " + pi.getID() + " is not a String, can't use to open port");
                    chosenInterface=null;
                } else {
                    String s = (String) pi.getID();
                    try {
                        chosenInterface = new eDVS128_HardwareInterface(s);
                    } catch (FileNotFoundException ex) {
                        log.warning("Serial port not found: " + ex.toString());
                    }
                }
            }
        }
        setVisible(false);
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton okButton;
    private javax.swing.JComboBox portCB;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
