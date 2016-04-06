/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Alexander
 */
public class Bullet {
    
    private static Material bulletMat = null;
    Geometry bulletGeom;
    PlayerVehicle vehicle;
    
    public Bullet(Main msa, PlayerVehicle vehicle) {
        this.vehicle = vehicle;
        Sphere bulletSphere = new Sphere(32, 32, 0.3f);
        bulletGeom = new Geometry("bullet", bulletSphere);
        initMaterials(msa);
        bulletGeom.addControl(new BulletControl());
    }
    
    public void initMaterials(Main msa) {
        bulletMat = new Material(msa.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        bulletMat.setColor("Color", ColorRGBA.Gray);
        bulletGeom.setMaterial(bulletMat);
    }

    class BulletControl extends AbstractControl {

        float time = 0;
        @Override
        protected void controlUpdate(float tpf) {
            time += tpf;
            
            if(time >= 0.2f)
            {
                time = 0;
                vehicle.detachBullet();
            }
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
        }

        
    }
    
    
    
}
