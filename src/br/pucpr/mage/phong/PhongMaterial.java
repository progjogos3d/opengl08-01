package br.pucpr.mage.phong;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.HashMap;
import java.util.Map;

import org.joml.Vector3f;

import br.pucpr.mage.Material;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Texture;

public class PhongMaterial implements Material {
    private Vector3f ambientColor;
    private Vector3f diffuseColor;
    private Vector3f specularColor;
    private float specularPower;
    private Map<String, Texture> textures = new HashMap<>();
    private Shader shader = Shader.loadProgram("/br/pucpr/mage/resource/phong/phong");
    
    public PhongMaterial(Vector3f ambientColor, Vector3f diffuseColor, Vector3f specularColor, float specularPower) {
        super();
        this.ambientColor = ambientColor;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.specularPower = specularPower;
    }
    
    public PhongMaterial(Vector3f ambient, Vector3f diffuse) {
        this(ambient, diffuse, new Vector3f(), 0.0f);
    }

    public PhongMaterial(Vector3f color) {
        this(color, color, new Vector3f(), 0.0f);
    }
    
    public PhongMaterial() {
        this(new Vector3f(1.0f, 1.0f, 1.0f));
    }
    
    public Vector3f getAmbientColor() {
        return ambientColor;
    }
    public Vector3f getDiffuseColor() {
        return diffuseColor;
    }
    public Vector3f getSpecularColor() {
        return specularColor;
    }
    public float getSpecularPower() {
        return specularPower;
    }
    
    public void setSpecularPower(float specularPower) {
        this.specularPower = specularPower;
    }    
    
    public PhongMaterial setTexture(Texture texture) {
        return setTexture("uTexture", texture);
    }
    
    public PhongMaterial setTexture(String name, Texture texture) {
        if (texture == null) {
            textures.remove(name);
        } else {
            textures.put(name, texture);
        }
        return this;
    }
    
    @Override
    public void apply() {
        shader.setUniform("uAmbientMaterial", ambientColor);
        shader.setUniform("uDiffuseMaterial", diffuseColor);
        shader.setUniform("uSpecularMaterial", specularColor);
        shader.setUniform("uSpecularPower", specularPower);
        
        int texCount = 0;        
        for (Map.Entry<String, Texture> entry : textures.entrySet()) {
            glActiveTexture(GL_TEXTURE0 + texCount);
            entry.getValue().bind();
            shader.setUniform(entry.getKey(), texCount);
            texCount = texCount + 1;            
        }
    }

    @Override
    public Shader getShader() {
        return shader;
    }

    @Override
    public void setShader(Shader shader) {
        if (shader == null) {
            throw new IllegalArgumentException("Shader cannot be null!");
        }
        this.shader = shader;
    }
}
