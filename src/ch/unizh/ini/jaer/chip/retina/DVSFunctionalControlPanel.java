/*
 * Tmpdiff128FunctionalBiasgenPanel.java
 *
 * Created on June 19, 2006, 1:48 PM
 */
package ch.unizh.ini.jaer.chip.retina;

import java.util.logging.Logger;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import net.sf.jaer.biasgen.PotTweaker;

/**
 * A panel for simplified control of DVS retina biases.

 * @author  tobi
 */
public class DVSFunctionalControlPanel extends javax.swing.JPanel  {

    AERetina chip;
    DVSTweaks biasgen;
    private static Logger log=Logger.getLogger("DVSFunctionalControlPanel");

    private void setFileModified() {
        if (chip != null && chip.getAeViewer() != null && chip.getAeViewer().getBiasgenFrame() != null) {
            chip.getAeViewer().getBiasgenFrame().setFileModified(true);
        }
    }

    /** Creates new form Tmpdiff128FunctionalBiasgenPanel */
    public DVSFunctionalControlPanel(AERetina chip) {
        initComponents();
        this.chip = chip;
        biasgen = (DVSTweaks) chip.getBiasgen();
        PotTweaker[] tweakers = {thresholdTweaker, onOffBalanceTweaker, maxFiringRateTweaker, bandwidthTweaker};
        for (PotTweaker tweaker : tweakers) {
            chip.getPrefs().addPreferenceChangeListener(tweaker); // to reset sliders on load/save of biases
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bandwidthTweaker = new net.sf.jaer.biasgen.PotTweaker();
        thresholdTweaker = new net.sf.jaer.biasgen.PotTweaker();
        maxFiringRateTweaker = new net.sf.jaer.biasgen.PotTweaker();
        onOffBalanceTweaker = new net.sf.jaer.biasgen.PotTweaker();

        setLayout(new java.awt.GridLayout(0, 1));

        bandwidthTweaker.setLessDescription("Slower");
        bandwidthTweaker.setMoreDescription("Faster");
        bandwidthTweaker.setName("Bandwidth"); // NOI18N
        bandwidthTweaker.setTweakDescription("Tweaks bandwidth of pixel front end.");
        bandwidthTweaker.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bandwidthTweakerStateChanged(evt);
            }
        });
        add(bandwidthTweaker);

        thresholdTweaker.setLessDescription("Lower/more events");
        thresholdTweaker.setMoreDescription("Higher/less events");
        thresholdTweaker.setName("Threshold"); // NOI18N
        thresholdTweaker.setTweakDescription("Adjusts event threshold");
        thresholdTweaker.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                thresholdTweakerStateChanged(evt);
            }
        });
        add(thresholdTweaker);

        maxFiringRateTweaker.setLessDescription("Slower");
        maxFiringRateTweaker.setMoreDescription("Faster");
        maxFiringRateTweaker.setName("Maximum firing rate"); // NOI18N
        maxFiringRateTweaker.setTweakDescription("Adjusts maximum pixel firing rate (1/refactory period)");
        maxFiringRateTweaker.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                maxFiringRateTweakerStateChanged(evt);
            }
        });
        add(maxFiringRateTweaker);

        onOffBalanceTweaker.setLessDescription("More Off events");
        onOffBalanceTweaker.setMoreDescription("More On events");
        onOffBalanceTweaker.setName("On/Off balance"); // NOI18N
        onOffBalanceTweaker.setTweakDescription("Adjusts balance bewteen On and Off events");
        onOffBalanceTweaker.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                onOffBalanceTweakerStateChanged(evt);
            }
        });
        add(onOffBalanceTweaker);
    }// </editor-fold>//GEN-END:initComponents

    private void bandwidthTweakerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bandwidthTweakerStateChanged
        biasgen.tweakBandwidth(bandwidthTweaker.getValue());
        setFileModified();
    }//GEN-LAST:event_bandwidthTweakerStateChanged

    private void thresholdTweakerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_thresholdTweakerStateChanged
        biasgen.tweakThreshold(thresholdTweaker.getValue());
        setFileModified();
    }//GEN-LAST:event_thresholdTweakerStateChanged

    private void maxFiringRateTweakerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_maxFiringRateTweakerStateChanged
        biasgen.tweakMaximumFiringRate(maxFiringRateTweaker.getValue());
        setFileModified();
    }//GEN-LAST:event_maxFiringRateTweakerStateChanged

    private void onOffBalanceTweakerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_onOffBalanceTweakerStateChanged
        biasgen.tweakOnOffBalance(onOffBalanceTweaker.getValue());
        setFileModified();
    }//GEN-LAST:event_onOffBalanceTweakerStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private net.sf.jaer.biasgen.PotTweaker bandwidthTweaker;
    private net.sf.jaer.biasgen.PotTweaker maxFiringRateTweaker;
    private net.sf.jaer.biasgen.PotTweaker onOffBalanceTweaker;
    private net.sf.jaer.biasgen.PotTweaker thresholdTweaker;
    // End of variables declaration//GEN-END:variables

}
