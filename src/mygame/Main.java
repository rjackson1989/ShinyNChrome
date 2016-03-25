package mygame;

import com.jme3.ai.agents.Agent;
import com.jme3.app.SimpleApplication;
import com.jme3.input.JoyInput;
import com.jme3.input.Joystick;
import com.jme3.input.JoystickAxis;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.JoyAxisTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;


/**
 * 
 * @author Raymond
 */
public class Main extends SimpleApplication implements AnalogListener{

    final int MENU = 0;
    final int GAME_ON = 1;
    final int PAUSE = 2;
    final int GAME_OVER = 3;
    Geometry geom;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        geom = new Geometry("Box", b);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        initControls();
        

        rootNode.attachChild(geom);
    }
    
    private void initControls()
    {
        
        Joystick[] joysticks = joyInput.loadJoysticks(inputManager);
        if(joysticks == null)
        {
            throw new IllegalStateException("Cannot find Joystick!");
        }
        int i = 0;
        
        joysticks[i].getAxis(JoystickAxis.X_AXIS).assignAxis("Right", "Left");
        joysticks[i].getAxis(JoystickAxis.Y_AXIS).assignAxis("Down", "Up");
       // for(int i = 0; i < 2; i++)
        //{
//inputManager.addMapping("LS Up", new JoyAxisTrigger(i, 0, true));
//inputManager.addMapping("LS Down", new JoyAxisTrigger(i, 0, false));
//inputManager.addMapping("LS Left", new JoyAxisTrigger(i, 1, false));
//inputManager.addMapping("LS Right", new JoyAxisTrigger(i, 1, true));
//inputManager.addListener(this, "LS Left", "LS Right", "LS Down", "LS Up");
//inputManager.addMapping("RS Up", new JoyAxisTrigger(i, 2, true));
//inputManager.addMapping("RS Down", new JoyAxisTrigger(i, 2, false));
//inputManager.addMapping("RS Left", new JoyAxisTrigger(i, 3, false));
//inputManager.addMapping("RS Right", new JoyAxisTrigger(i, 3, true));
//inputManager.addListener(this, "RS Left", "RS Right", "RS Down", "RS Up");
//inputManager.addMapping("DPAD Left", new JoyAxisTrigger(i, JoyInput.AXIS_POV_X, true));
//inputManager.addMapping("DPAD Right", new JoyAxisTrigger(i, JoyInput.AXIS_POV_X, false));
//inputManager.addMapping("DPAD Down", new JoyAxisTrigger(i, JoyInput.AXIS_POV_Y, true));
//inputManager.addMapping("DPAD Up", new JoyAxisTrigger(i, JoyInput.AXIS_POV_Y, false));
//inputManager.addListener(this, "DPAD Left", "DPAD Right", "DPAD Down", "DPAD Up");
//inputManager.addMapping("Trigger L", new JoyAxisTrigger(i, 4, false));
//inputManager.addMapping("Trigger R", new JoyAxisTrigger(i, 4, true));
//inputManager.addListener(this, "Trigger L", "Trigger R");
//joysticks[i].assignButton("Button A", 0);
//joysticks[i].assignButton("Button B", 1);
//joysticks[i].assignButton("Button X", 2);
//joysticks[i].assignButton("Button Y", 3);
//inputManager.addListener(this, "Button A", "Button B", "Button X", "Button Y");
//joysticks[i].assignButton("Button LB", 4);
//joysticks[i].assignButton("Button RB", 5);
//joysticks[i].assignButton("Button Back", 6);
//joysticks[i].assignButton("Button Start", 7);
//inputManager.addListener(this, "Button LB", "Button RB", "Button Back", "Button Start");
//joysticks[i].assignButton("Button LS", 8 );
//joysticks[i].assignButton("Button RS", 9);
//inputManager.addListener(this, "Button LS", "Button RS");
       // }

    }
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }


    public void onAnalog(String name, float value, float tpf) {
            if(name.equals("Up"))
            {
                geom.move(0, tpf/5, 0);
            }
            if(name.equals("Right"))
            {
                geom.move(tpf/5, 0, 0);
            }
            if(name.equals("Down"))
            {
                geom.move(0, -tpf/5, 0);
            }
    }
}
