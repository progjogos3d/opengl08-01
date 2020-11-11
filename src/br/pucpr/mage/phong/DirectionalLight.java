package br.pucpr.mage.phong;

import br.pucpr.mage.ShaderItem;
import org.joml.Vector3f;
import static br.pucpr.mage.MathUtil.asString;

import br.pucpr.mage.Shader;

public class DirectionalLight implements ShaderItem {
    private Vector3f direction;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    
    public DirectionalLight(Vector3f direction, Vector3f ambient, Vector3f diffuse, Vector3f specular) {
        super();
        this.direction = new Vector3f(direction);
        this.ambient = new Vector3f(ambient);
        this.diffuse = new Vector3f(diffuse);
        this.specular = specular;
    }

    public DirectionalLight(Vector3f direction, Vector3f ambient, Vector3f diffuse) {
        this(direction, ambient, diffuse, diffuse);
    }

    public DirectionalLight(Vector3f direction, float ambient, Vector3f diffuse) {
        this(direction, diffuse, new Vector3f(ambient, ambient, ambient));
    }

    public DirectionalLight(Vector3f direction, Vector3f diffuse) {
        this(direction, 0.1f, diffuse);
    }


    public DirectionalLight(Vector3f direction) {
        this(direction, new Vector3f(1.0f, 1.0f, 1.0f));
    }

    public DirectionalLight() {
        this(new Vector3f(1.0f, -1.0f, -1.0f).normalize());
    }

    public Vector3f getDirection() {
        return direction;
    }

    public DirectionalLight setDirection(float x, float y, float z) {
        this.getDirection().set(x, y, z);
        return this;
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

    public DirectionalLight setAmbient(float i) {
        return setAmbient(i, i, i);
    }
    public DirectionalLight setAmbient(float r, float g, float b) {
        getAmbient().set(r, g, b);
        return this;
    }

    public DirectionalLight setDiffuse(float i) {
        return setDiffuse(i, i, i);
    }
    public DirectionalLight setDiffuse(float r, float g, float b) {
        getDiffuse().set(r, g, b);
        return this;
    }

    public DirectionalLight setSpecular(float i) {
        return setSpecular(i, i, i);
    }
    public DirectionalLight setSpecular(float r, float g, float b) {
        getSpecular().set(r, g, b);
        return this;
    }

    public DirectionalLight setColor(float i) {
        return setColor(i, i, i);
    }
    public DirectionalLight setColor(float r, float g, float b) {
        return setDiffuse(r, g, b).setSpecular(r, g, b);
    }

    @Override
    public void apply(Shader shader) {
        shader.setUniform("uLightDir", direction)
            .setUniform("uAmbientLight", ambient)
            .setUniform("uDiffuseLight", diffuse)
            .setUniform("uSpecularLight", specular);
    }

    @Override
    public String toString() {
        return "DirectionalLight{" +
                "direction=" + asString(direction) +
                ", ambient=" + asString(ambient) +
                ", diffuse=" + asString(diffuse) +
                ", specular=" + asString(specular) +
                '}';
    }
}
