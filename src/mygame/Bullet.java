/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Alexander
 */
public class Bullet {
    
    private static Material bulletMat = null;
    Geometry bulletGeom;
    
    public Bullet(Main msa) {
        Sphere bulletSphere = new Sphere(32, 32, 0.3f);
        bulletGeom = new Geometry("bullet", bulletSphere);
        initMaterials(msa);
    }
    
    public void initMaterials(Main msa) {
        bulletMat = new Material(msa.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        bulletMat.setColor("Color", ColorRGBA.Gray);
        bulletGeom.setMaterial(bulletMat);
    }
    
}
