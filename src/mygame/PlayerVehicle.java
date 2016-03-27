/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Raymond
 */
public class PlayerVehicle
{
    Node vehicleNode;
    Geometry vehicleGeom;
    int currentState, ID;
    final int SELECTION = 0;
    final int ALIVE = 1;
    final int DEATH = 2;
    Main main;
    
    public PlayerVehicle(Main m, int ID)
    {
        this.main = m;
        currentState = SELECTION;
        vehicleNode = new Node();
        this.ID = ID;
        vehicleNode = (Node)m.getAssetManager().loadModel("Models/CARS222.j3o");
        vehicleNode.setShadowMode(RenderQueue.ShadowMode.Cast);
        vehicleNode.setLocalTranslation(0, 5f, 0);
        vehicleNode.rotate(0,0, 0);
//        Geometry chasis = findGeom(vehicleNode, "BDY");
       // vehicleGeom = findGeom(vehicleNode, "BDY");
       // vehicleNode.attachChild(vehicleGeom);
        m.getRootNode().attachChild(vehicleNode);
    }
    
    public void onAction(String name, boolean isPressed, float tpf) {
        
    }

    public void onAnalog(String name, float value, float tpf) {
    }
    private Geometry findGeom(Spatial spatial, String name) {
        if (spatial instanceof Node) {
            Node node = (Node) spatial;
            for (int i = 0; i < node.getQuantity(); i++) {
                Spatial child = node.getChild(i);
                Geometry result = findGeom(child, name);
                if (result != null) {
                    return result;
                }
            }
        } else if (spatial instanceof Geometry) {
            if (spatial.getName().startsWith(name)) {
                return (Geometry) spatial;
            }
        }
        return null;
    }
}
