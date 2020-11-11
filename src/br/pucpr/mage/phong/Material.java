package br.pucpr.mage.phong;

import br.pucpr.mage.Shader;
import br.pucpr.mage.ShaderItem;
import br.pucpr.mage.Texture;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static br.pucpr.mage.MathUtil.asString;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Material implements ShaderItem {
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    private float power;
    private Map<String, Texture> textures = new HashMap<>();

    public Material(Vector3f ambient, Vector3f diffuse, Vector3f specular, float power) {
        super();
        this.ambient = new Vector3f(ambient);
        this.diffuse = new Vector3f(diffuse);
        this.specular = new Vector3f(specular);
        this.power = power;
    }

    public Material(Vector3f ambient, Vector3f diffuse) {
        this(ambient, diffuse, new Vector3f(1.0f, 1.0f, 1.0f), 0.0f);
    }

    public Material(Vector3f color) {
        this(color, color);
    }

    public Material() {
        this(new Vector3f(1.0f, 1.0f, 1.0f));
    }

    public Vector3f getAmbient() {
        return ambient;
    }
    public Vector3f getDiffuse() {
        return diffuse;
    }
    public Vector3f getSpecular() {
        return specular;
    }
    public float getPower() {
        return power;
    }

    public Material setColor(float r, float g, float b) {
        return setAmbient(r, g, b).setDiffuse(r, g, b);
    }

    public Material setColor(float i) {
        return setColor(i, i, i);
    }

    public Material setAmbient(float r, float g, float b) {
        getAmbient().set(r, g, b);
        return this;
    }

    public Material setAmbient(float i) {
        return setAmbient(i, i, i);
    }

    public Material setDiffuse(float r, float g, float b) {
        getDiffuse().set(r, g, b);
        return this;
    }

    public Material setDiffuse(float i) {
        return setDiffuse(i, i, i);
    }

    public Material setSpecular(float r, float g, float b) {
        getSpecular().set(r, g, b);
        return this;
    }

    public Material setSpecular(float i) {
        return setSpecular(i, i, i);
    }

    public Material setPower(float power) {
        this.power = power;
        return this;
    }


    public Material setTexture(Texture texture) {
        return setTexture("uTexture", texture);
    }
    public Material setTexture(String path) {
        return setTexture(new Texture(path));
    }

    public Material setTexture(String name, String path) {
        return setTexture(name, new Texture(path));
    }
    public Material setTexture(String name, Texture texture) {
        if (texture == null) {
            textures.remove(name);
        } else {
            textures.put(name, texture);
        }
        return this;
    }

    public Material setTextures(String name, Texture ... textures) {
        for (int i = 0; i < textures.length; i++) {
            setTexture(name + i, textures[i]);
        }
        return this;
    }
    public Material setTextures(String name, String ... paths) {
        for (int i = 0; i < paths.length; i++) {
            setTexture(name + i, new Texture(paths[i]));
        }
        return this;
    }

    @Override
    public void apply(Shader shader) {
        shader.setUniform("uAmbientMaterial", ambient)
                .setUniform("uDiffuseMaterial", diffuse)
                .setUniform("uSpecularMaterial", specular)
                .setUniform("uSpecularPower", power);

        var texSlot = 0;
        for (var entry : textures.entrySet()) {
            glActiveTexture(GL_TEXTURE0 + texSlot);
            entry.getValue().bind();
            shader.setUniform(entry.getKey(), texSlot);
            texSlot = texSlot + 1;
        }
    }

    @Override
    public String toString() {
        return "Material{" +
                "ambient=" + asString(ambient) +
                ", diffuse=" + asString(diffuse) +
                ", specular=" + asString(specular) +
                ", power=" + String.format("%.2f", power) +
                ", textures=" + textures.keySet().toString() +
                '}';
    }
}