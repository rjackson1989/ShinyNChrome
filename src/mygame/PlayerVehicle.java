/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author Raymond
 */
public class PlayerVehicle
{
    Node vehicleNode;
    Geometry vehicleGeom;
    int currentState;
    final int SELECTION = 0;
    final int ALIVE = 1;
    final int DEATH = 2;
    Main main;
    
    public PlayerVehicle(Main m)
    {
        this.main = m;
        currentState = SELECTION;
        vehicleNode = new Node();
        
    }
    public void onAction(String name, boolean isPressed, float tpf) {
    }

    public void onAnalog(String name, float value, float tpf) {
    }
    
}
