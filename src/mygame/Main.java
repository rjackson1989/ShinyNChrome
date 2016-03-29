package mygame;

//import com.jme3.ai.agents.Agent;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.JoyInput;
import com.jme3.input.Joystick;
import com.jme3.input.JoystickAxis;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.JoyAxisTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;


/**
 * 
 * @authors Raymond, Alexander
 */
public class Main extends SimpleApplication implements AnalogListener, ActionListener{

    final int MENU = 0;
    final int GAME_ON = 1;
    final int PAUSE = 2;
    final int GAME_OVER = 3;
    Geometry targetGeom, ground;
    ChaseCamera ccam;
    PlayerVehicle vehicle;
    BulletAppState physics;
    RigidBodyControl boxBody, groundBody;
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setUseJoysticks(true);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        // get rid of the pesky statistics
        setDisplayFps(false);
        setDisplayStatView(false);
        flyCam.setEnabled(false);
        
        // calls to init functions to help create our scene
        initMaterials();
        initControls();
        initLighting();
        initPhysics();
        
        vehicle = new PlayerVehicle(this, 0);
        ccam = new ChaseCamera(cam, vehicle.vehicleNode, inputManager);
    }
    
    private void initLighting() {
    viewPort.setBackgroundColor(ColorRGBA.LightGray);  
    /** Add a light source so we can see the model */
    DirectionalLight dl = new DirectionalLight();
    dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
    rootNode.addLight(dl);  
    }
    private void initMaterials()
    {
        Box targetBox = new Box(1, 1, 1);
        targetGeom = new Geometry("Box", targetBox);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        targetGeom.setMaterial(mat);
        targetGeom.setLocalTranslation(0, 0, 8);
        targetGeom.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        rootNode.attachChild(targetGeom);
        
        // the ground that the vehicle drives on
        Box groundBox = new Box(100, 0, 100);
        ground = new Geometry("Box2", groundBox);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Brown);
        Texture text = assetManager.loadTexture("Textures/asphalt.jpg");
        mat2.setTexture("ColorMap", text);
        ground.setMaterial(mat2);
        ground.setLocalTranslation(0, -1, 0);
        rootNode.attachChild(ground);
    }
    private void initPhysics()
    {
     physics = new BulletAppState();
     stateManager.attach(physics);
     boxBody = new RigidBodyControl(1.0f);
     groundBody = new RigidBodyControl(0);
     targetGeom.addControl(boxBody);
     ground.addControl(groundBody);
     physics.getPhysicsSpace().add(boxBody);
     physics.getPhysicsSpace().add(groundBody);
     
    }
    private void initControls()
    {
        
        Joystick[] joysticks = inputManager.getJoysticks();
        if(joysticks == null)
        {
            throw new IllegalStateException("Cannot find Joystick!");
        }
        int i = 0;

       // for(int i = 0; i < 2; i++)
        //{
inputManager.addMapping("LS Up", new JoyAxisTrigger(i, 0, true));
inputManager.addMapping("LS Down", new JoyAxisTrigger(i, 0, false));
inputManager.addMapping("LS Left", new JoyAxisTrigger(i, 1, false));
inputManager.addMapping("LS Right", new JoyAxisTrigger(i, 1, true));
inputManager.addListener(this, "LS Left", "LS Right", "LS Down", "LS Up");
inputManager.addMapping("RS Up", new JoyAxisTrigger(i, 2, true));
inputManager.addMapping("RS Down", new JoyAxisTrigger(i, 2, false));
inputManager.addMapping("RS Left", new JoyAxisTrigger(i, 3, false));
inputManager.addMapping("RS Right", new JoyAxisTrigger(i, 3, true));
inputManager.addListener(this, "RS Left", "RS Right", "RS Down", "RS Up");
inputManager.addMapping("Trigger L", new JoyAxisTrigger(i, 4, false));
inputManager.addMapping("Trigger R", new JoyAxisTrigger(i, 4, true));
inputManager.addListener(this, "Trigger R", "Trigger L");
// proper usuage for deprecated assignButton
joysticks[i].getButton("0").assignButton("Button A");
joysticks[i].getButton("1").assignButton("Button B");
joysticks[i].getButton("2").assignButton("Button X");
joysticks[i].getButton("3").assignButton("Button Y");
inputManager.addListener(this, "Button A", "Button B","Button X", "Button Y");
//joysticks[i].assignButton("Button LB", 4);
//joysticks[i].assignButton("Button RB", 5);
//joysticks[i].assignButton("Button Back", 6);
//joysticks[i].assignButton("Button Start", 7);
//inputManager.addListener(this, "Button LB", "Button RB", "Button Back", "Button Start");
//joysticks[i].assignButton("Button LS", 8 );
//joysticks[i].assignButton("Button RS", 9);
//inputManager.addListener(this, "Button LS", "Button RS");
//inputManager.addMapping("DPAD Left", new JoyAxisTrigger(i, 4, true));
//inputManager.addMapping("DPAD Right", new JoyAxisTrigger(i, 4, false));
//inputManager.addMapping("DPAD Down", new JoyAxisTrigger(i, 5, true));
//inputManager.addMapping("DPAD Up", new JoyAxisTrigger(i, 5, false));
//inputManager.addListener(this, "DPAD Left", "DPAD Right", "DPAD Down", "DPAD Up");
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
         
            // spins the vehicle to the right, pivoting around vehicle origin
            if(name.equals("LS Right"))
            {
                vehicle.vehicleNode.rotate(0,value, 0);
            }
            // spins the vehicle to the left, pivoting around vehicle origin
            if(name.equals("LS Left"))
            {
                vehicle.vehicleNode.rotate(0,-value, 0);
            }
            // moves the vehicle forward (gives it gas)
            if(name.equals("Trigger R"))
            {
                Vector3f forward = vehicle.vehicleNode.getLocalRotation().getRotationColumn(2);
                vehicle.vehicleNode.move(forward.mult(tpf * 5));
            }
            // reverses the vehicle (moves it backwards)
            if(name.equals("Trigger L"))
            {
                Vector3f forward = vehicle.vehicleNode.getLocalRotation().getRotationColumn(2);
                vehicle.vehicleNode.move(forward.mult(-tpf * 5));
            }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("Button A") && isPressed)
        {
           vehicle.shoot = true;
        }
    }
}
