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
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
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
    Geometry ground;
    ChaseCamera ccam1, ccam2;
    PlayerVehicle[] vehicles;
    BulletAppState physics;
    RigidBodyControl boxBody, groundBody;
    Camera cam2;
    Node terrainNode = new Node();
    Terrain terrain;
    
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setUseJoysticks(true);
        settings.setFullscreen(true);
        settings.setResolution(1366, 768);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        // get rid of the pesky statistics
        setDisplayFps(false);
        setDisplayStatView(false);

        flyCam.setEnabled(true);

//        flyCam.setEnabled(false);
//        flyCam.setMoveSpeed(20f);

        
        // calls to init functions to help create our scene
        initMaterials();
        initPhysics();
        
        vehicles = new PlayerVehicle[3];
        for(int i = 0; i < 2; i++)
        {
            vehicles[i] = new PlayerVehicle(this, i);
        }
        initControls();
        initLighting();
        initCamera();
        
        //cam.setLocation(new Vector3f(0, 10f, 0));
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
        terrain = new Terrain(this);
        rootNode.attachChild(terrainNode);
        
        Box targetBox = new Box(1, 1, 1);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        
////        // the ground that the vehicle drives on
//        Box groundBox = new Box(100, 0, 100);
//        ground = new Geometry("Box2", groundBox);
//        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        mat2.setColor("Color", ColorRGBA.Brown);
//        Texture text = assetManager.loadTexture("Textures/asphalt.jpg");
//        mat2.setTexture("ColorMap", text);
//        ground.setMaterial(mat2);
//        ground.setLocalTranslation(0, -1, 0);
//        rootNode.attachChild(ground);
    }
    private void initPhysics()
    {
     physics = new BulletAppState();
     stateManager.attach(physics);
    // boxBody = new RigidBodyControl(1.0f);
    // groundBody = new RigidBodyControl(0);
    // targetGeom.addControl(boxBody);

     // add terrain landscape to physics space
    // physics.getPhysicsSpace().add(boxBody);
     physics.getPhysicsSpace().add(terrain.landscape);
//     physics.getPhysicsSpace().add(groundBody);
     //targetGeom.addControl(boxBody);
    // ground.addControl(groundBody);
     //physics.getPhysicsSpace().add(boxBody);
    // physics.getPhysicsSpace().add(groundBody);
     
    }
    private void initControls()
    {
        
        Joystick[] joysticks = inputManager.getJoysticks();
        if(joysticks == null)
        {
            throw new IllegalStateException("Cannot find Joystick!");
        }
     

        for(int i = 0; i < joysticks.length; i++)
        {
inputManager.addMapping("LS Up"+i, new JoyAxisTrigger(i, 0, true));
inputManager.addMapping("LS Down"+i, new JoyAxisTrigger(i, 0, false));
inputManager.addMapping("LS Left"+i, new JoyAxisTrigger(i, 1, false));
inputManager.addMapping("LS Right"+i, new JoyAxisTrigger(i, 1, true));
inputManager.addListener(vehicles[i], "LS Left"+i, "LS Right"+i, "LS Down"+i, "LS Up"+i);
inputManager.addMapping("RS Up"+i, new JoyAxisTrigger(i, 2, true));
inputManager.addMapping("RS Down"+i, new JoyAxisTrigger(i, 2, false));
inputManager.addMapping("RS Left"+i, new JoyAxisTrigger(i, 3, false));
inputManager.addMapping("RS Right"+i, new JoyAxisTrigger(i, 3, true));
inputManager.addListener(vehicles[i], "RS Left"+i, "RS Right"+i, "RS Down"+i, "RS Up"+i);
inputManager.addMapping("Trigger L"+i, new JoyAxisTrigger(i, 4, false));
inputManager.addMapping("Trigger R"+i, new JoyAxisTrigger(i, 4, true));
inputManager.addListener(vehicles[i], "Trigger R"+i, "Trigger L"+i);
// proper usuage for deprecated assignButton

//joysticks[i].getButton("0").assignButton("Button A");
//joysticks[i].getButton("1").assignButton("Button B");
//joysticks[i].getButton("2").assignButton("Button X");
//joysticks[i].getButton("3").assignButton("Button Y");
//inputManager.addListener(this, "Button A", "Button B","Button X", "Button Y");

//joysticks[i].getButton("0").assignButton("Button A"+i);
//joysticks[i].getButton("1").assignButton("Button B"+i);
//joysticks[i].getButton("2").assignButton("Button X"+i);
//joysticks[i].getButton("3").assignButton("Button Y"+i);
//inputManager.addListener(vehicles[i], "Button A"+i, "Button B"+i,"Button X"+i, "Button Y"+i);
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
        }

    }
    private void initCamera()
    {
        cam2 = cam.clone();
        cam.setViewPort(0f, 0.5f, 0f, 1f);
        cam2.setViewPort(0.5f, 1f, 0f, 1f);
        ViewPort view2 = renderManager.createMainView("Right Half", cam2);
        view2.setClearFlags(true, true, true);
        view2.attachScene(rootNode);
        ccam1 = new ChaseCamera(cam, vehicles[0].vehicleNode, inputManager);
        ccam2 = new ChaseCamera(cam2, vehicles[1].vehicleNode, inputManager);
        
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
         
            
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        
    }
}
