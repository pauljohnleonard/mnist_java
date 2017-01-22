/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.bath.ai.gui;

/**
 *
 *  Interface for things that we want to see the progress off and might want to stop
 *
 * @author pjl
 */
public interface Progressable {
    public float getProgress();
    public void stop();
}
