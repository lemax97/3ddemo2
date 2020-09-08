package demo3D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import java.util.ArrayList;

public class GameScreen extends BaseScreen {
    BaseActor3D player;
    ArrayList<BaseActor3D> rockList;

    public GameScreen(BaseGame g) {
        super(g);
    }

    @Override
    public void create() {

        BaseActor3D sea = new BaseActor3D();
        Texture seaTexture = new Texture(Gdx.files.internal("water.jpg"), true);
        seaTexture.setFilter(TextureFilter.Linear,TextureFilter.Linear);
        ModelInstance seaInstance = ModelUtils.createBox(500, 0.1f, 500, seaTexture, Color.GRAY);
        sea.setModelInstance(seaInstance);
        mainStage3D.addActor(sea);

        player = new BaseActor3D();
        player.setPosition(0,0,0);
        ModelLoader loader = new ObjLoader();
        Model shipModel = loader.loadModel(Gdx.files.internal("ship.obj"));
        for (Mesh m : shipModel.meshes)
            m.transform(new Matrix4().setToRotation(0,1,0,180));
        ModelInstance shipInstance = new ModelInstance(shipModel);
        player.setModelInstance(shipInstance);
        player.setEllipseBase();
        mainStage3D.addActor(player);

        BaseActor3D skydome = new BaseActor3D();
        Texture skyTexture = new Texture(Gdx.files.internal("sky-sphere.png"),true);
        ModelInstance skyInstance = ModelUtils.createSphereInv(500, skyTexture, Color.WHITE);
        skydome.setModelInstance(skyInstance);
        skydome.setPosition(0,0,0);
        mainStage3D.addActor(skydome);

        rockList = new ArrayList<BaseActor3D>();

        Model rockModel = loader.loadModel(Gdx.files.internal("rock.obj"));
        ModelInstance rockInstance = new ModelInstance(rockModel);
        BaseActor3D baseRock = new BaseActor3D();
        baseRock.setModelInstance(rockInstance);
        baseRock.setEllipseBase();

        BaseActor3D rock1 = baseRock.clone();
        rock1.setPosition(2,0,2);
        mainStage3D.addActor(rock1);
        rockList.add(rock1);

        BaseActor3D rock2 = baseRock.clone();
        rock2.setPosition(-4, 0, 4);
        mainStage3D.addActor(rock2);
        rockList.add(rock2);

        BaseActor3D rock3 = baseRock.clone();
        rock3.setPosition(6,0,6);
        mainStage3D.addActor(rock3);
        rockList.add(rock3);

        mainStage3D.setCameraPosition(2,3,15);
    }

    @Override
    public void update(float dt) {

        for ( BaseActor3D rock : rockList )
            player.overlaps(rock, true);

        float speed = 3.0f;
        float rotateSpeed = 45.0f;

        if (Gdx.input.isKeyPressed(Keys.W))
            player.moveForward( speed * dt);

        if (Gdx.input.isKeyPressed(Keys.Q))
            player.turn( - rotateSpeed * dt );
        if (Gdx.input.isKeyPressed(Keys.E))
            player.turn( rotateSpeed * dt );

        mainStage3D.setCameraDirection(player.getPosition());

    }


    public boolean keyDown(int keycode)
    {
        if (keycode == Keys.Z)
            game.setScreen( new GameScreen(game) );

        return false;
    }


}
