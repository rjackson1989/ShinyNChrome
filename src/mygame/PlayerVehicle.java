/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Raymond
 */
public class PlayerVehicle
{
    Node vehicleNode;
    Geometry bulletGeom;
    int currentState, ID;
    final int SELECTION = 0;
    final int ALIVE = 1;
    final int DEATH = 2;
    Main main;
    BulletAppState physics;
    RigidBodyControl rigidBody, bulletBody;
    boolean shoot;
    
    
    public PlayerVehicle(Main m, int ID)
    {
        this.main = m;
        currentState = SELECTION;
        vehicleNode = new Node();
        shoot = false;
        this.ID = ID;
        vehicleNode = (Node)m.getAssetManager().loadModel("Models/CARS222.j3o");
        vehicleNode.setShadowMode(RenderQueue.ShadowMode.Cast);
        vehicleNode.setLocalTranslation(0, 3f, 0);
        Sphere s = new Sphere(32, 32, 0.2f);
        bulletGeom = new Geometry("bullet", s);
        Material mat = new Material(m.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Gray);
        bulletGeom.setMaterial(mat);
        bulletGeom.setLocalTranslation(0, 0, 5f);
       // vehicleNode.attachChild(bulletGeom);
        m.getRootNode().attachChild(vehicleNode);
        vehicleNode.addControl(new bulletControl());
        initPhysics();
    }
     private void initPhysics() {
         physics = main.physics;
         rigidBody = new RigidBodyControl(1.0f);
         
         vehicleNode.addControl(rigidBody);
         
         //bulletBody.setKinematic(true);
         rigidBody.setKinematic(true);
         physics.getPhysicsSpace().add(rigidBody);
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

   class bulletControl extends AbstractControl{

        @Override
        protected void controlUpdate(float tpf) {
            
            if(shoot)
            {
                shoot = !shoot;
                vehicleNode.attachChild(bulletGeom);
                bulletBody = new RigidBodyControl(1.0f);
                bulletGeom.addControl(bulletBody);
         physics.getPhysicsSpace().add(bulletBody);
             Vector3f forward = vehicleNode.getLocalRotation().getRotationColumn(2);
             bulletBody.setLinearVelocity(forward.mult(25f));
             vehicleNode.detachChild(bulletGeom);
             
            }

        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
        }
    }
    
}
