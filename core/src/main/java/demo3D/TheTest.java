package demo3D;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class TheTest implements ApplicationListener {
	public Environment environment;
	public PerspectiveCamera perspectiveCamera;
	public ModelBatch modelBatch;
	public ModelInstance boxInstance;


	@Override
	public void create() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1f, 0f, 0.1f, 1));

		DirectionalLight directionalLight = new DirectionalLight();
		Color lightColor = new Color(1f, 1f, 1f, 1);
		Vector3 lightVector = new Vector3(-0.5f, -0.75f, -0.25f);
		directionalLight.set(lightColor, lightVector);
		environment.add(directionalLight);

		perspectiveCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		perspectiveCamera.near = 0.1f;
		perspectiveCamera.far = 1000f;
		perspectiveCamera.position.set(15f, 5, 5f);
		perspectiveCamera.lookAt(0,1,1);
		perspectiveCamera.update();

		modelBatch = new ModelBatch();

		ModelBuilder modelBuilder = new ModelBuilder();
		Material boxMaterial = new Material();
		boxMaterial.set(ColorAttribute.createDiffuse(Color.GOLDENROD));

		int usageCode = Usage.Position + Usage.ColorPacked + Usage.Normal;

		Model boxModel = modelBuilder.createBox(5, 5, 5, boxMaterial, usageCode);
		boxInstance = new ModelInstance(boxModel);

	}

	@Override
	public void resize(int width, int height) {
		// Resize your application here. The parameters represent the new window size.
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.35f,0.1f,0.2f,1);
		Gdx.gl.glViewport(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(perspectiveCamera);
		modelBatch.render(boxInstance, environment);
		modelBatch.end();
	}

	@Override
	public void pause() {
		// Invoked when your application is paused.
	}

	@Override
	public void resume() {
		// Invoked when your application is resumed after pause.
	}

	@Override
	public void dispose() {
		// Destroy application's resources here.
	}
}