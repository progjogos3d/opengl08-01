package br.pucpr.cg;

import br.pucpr.mage.*;
import br.pucpr.mage.camera.CameraFPS;
import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.Material;
import org.joml.Matrix4f;

import static br.pucpr.mage.MathUtil.mul;
import static org.joml.Math.toRadians;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TerrainScene implements Scene {
    private Keyboard keys = Keyboard.getInstance();

    private static final String PATH = "/Users/vinigodoy/opengl";

    private final float WALK_SPEED = 20f;
    private final float TURN_SPEED = toRadians(120f);

    private Shader shader;
    private Mesh mesh;
    private CameraFPS camera = new CameraFPS();
    private DirectionalLight light;
    private Material material;

    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        shader = Shader.loadProgram("phongMT");
        mesh = MeshFactory.loadTerrain(shader, PATH + "/heights/river.jpg", 0.3f, 10);

        camera.getPosition().set(-68.28f, 16.78f, 34.79f);
        camera.setAngleY(toRadians(292.0f));

        light = new DirectionalLight()
                .setDirection(0.8f, -1.0f, 1.0f)
                .setAmbient(0.1f)
                .setColor(1.0f, 1.0f, 0.98f);

        material = new Material()
                .setColor(0.9f)
                .setSpecular(0.15f).setPower(64)
                .setTexture(PATH + "/textures/grass.jpg");
    }

    @Override
    public void update(float secs) {
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), true);
            return;
        }

        var strafe = keys.isDown(GLFW_KEY_LEFT_SHIFT);

        if (keys.isDown(GLFW_KEY_UP)) {
            camera.move(WALK_SPEED, secs);
        } else if (keys.isDown(GLFW_KEY_DOWN)) {
            camera.move(-WALK_SPEED, secs);
        }

        if (keys.isDown(GLFW_KEY_Z)) {
            camera.getPosition().add(mul(camera.getUp(), -1.0f));
        } else if (keys.isDown(GLFW_KEY_C)) {
            camera.getPosition().add(mul(camera.getUp(), 1.0f));
        }

        if (strafe) {
            if (keys.isDown(GLFW_KEY_LEFT)) {
                camera.strafe(WALK_SPEED, secs);
            } else if (keys.isDown(GLFW_KEY_RIGHT)) {
                camera.strafe(-WALK_SPEED, secs);
            }
        } else {
            if (keys.isDown(GLFW_KEY_LEFT)) {
                camera.turn(TURN_SPEED, secs);
            } else if (keys.isDown(GLFW_KEY_RIGHT)) {
                camera.turn(-TURN_SPEED, secs);
            }
        }
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.bind()
            .set(camera)
            .set(light)
        .unbind();

        mesh
            .setUniform("uWorld", new Matrix4f())
            .setItem("material", material)
        .draw(shader);
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new TerrainScene(), "Terrain sample", 1024, 768).show();
    }
}