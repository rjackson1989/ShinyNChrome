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
public class PlayerVehicle {

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
    Bullet bullet;

    public PlayerVehicle(Main m, int ID) {
        this.main = m;
        currentState = SELECTION;
        vehicleNode = new Node();
        shoot = false;
        this.ID = ID;
        vehicleNode = (Node) m.getAssetManager().loadModel("Models/CARS222.j3o");
        vehicleNode.setShadowMode(RenderQueue.ShadowMode.Cast);
        vehicleNode.setLocalTranslation(0, 3f, 0);
        m.getRootNode().attachChild(vehicleNode);
        vehicleNode.addControl(new FireControl());
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

    class FireControl extends AbstractControl {

        float time = 0;

        @Override
        protected void controlUpdate(float tpf) {

            if (shoot == true) {
                makeBullets(main);
                shoot = false;
            }
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
        }
    }

    protected void makeBullets(Main m) {
        bullet = new Bullet(m);
        bullet.bulletGeom.setLocalTranslation(0, 1f, 5f);
        vehicleNode.attachChild(bullet.bulletGeom);
        bulletBody = new RigidBodyControl(1.0f);
        bullet.bulletGeom.addControl(bulletBody);
        physics.getPhysicsSpace().add(bulletBody);
        Vector3f forward = vehicleNode.getLocalRotation().getRotationColumn(2);
        bulletBody.setLinearVelocity(forward.mult(150f));
    }
}
