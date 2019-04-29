package br.pucpr.mage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * Representa a malha poligonal estática. A malha é formada por um conjunto de vértices, definidos por buffers de
 * atributos, um indexbuffer (opcional) que indica como os triangulos são compostos, um conjunto de uniforms (como a
 * textura). A malha é desenhada utilizando um material, definido durante a pintura.
 */
public class Mesh {
    private int id;
    private IndexBuffer indexBuffer;

    private Map<String, ArrayBuffer> attributes = new HashMap<>();
    private Map<String, Uniform> uniforms = new HashMap<>();
    private boolean wireframe = false;

    Mesh() {
        id = glGenVertexArrays();
    }

    /**
     * @return O id dessa malha (VAO) no OpenGL
     */
    public int getId() {
        return id;
    }

    /**
     * Define o index buffer da malha. Esse método não pode ser chamado diretamente. Utilize os métodos de index buffer
     * da classe MeshBuilder para isso.
     * @param indexBuffer O index buffer a ser definido.
     * @return A própria malha
     * @see MeshBuilder#setIndexBuffer(IndexBuffer)
     */
    Mesh setIndexBuffer(IndexBuffer indexBuffer) {
        this.indexBuffer = indexBuffer;
        return this;
    }

    /**
     * Associa um buffer a malha.
     * @param name O nome do buffer a ser associado. Uma vez associado, o buffer não pode ser substituído.
     * @param data O ArrayBuffer com os dados do atributo.
     */
    void addAttribute(String name, ArrayBuffer data) {
        if (attributes.containsKey(name)) {
            throw new IllegalArgumentException("Attribute already exists: " + name);
        }
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null!");
        }

        attributes.put(name, data);
    }

    /**
     * Define o valor de um uniforme dentro da malha
     * @param name Nome do uniforme
     * @param type O tipo do uniforme
     * @param value valor a ser definido
     * @return A própria malha
     */
    private Mesh setUniform(String name, UniformType type, Object value) {
        if (value == null)
            uniforms.remove(name);
        else {
            uniforms.put(name, new Uniform(type, value));
        }
        return this;
    }

    /**
     * Define o valor de um uniforme dentro da malha
     * @param name Nome do uniforme
     * @param matrix valor a ser definido
     * @return A própria malha
     */
    public Mesh setUniform(String name, Matrix3f matrix) {
        return setUniform(name, UniformType.Matrix3f, matrix);
    }

    /**
     * Define o valor de um uniforme dentro da malha
     * @param name Nome do uniforme
     * @param matrix valor a ser definido
     * @return A própria malha
     */
    public Mesh setUniform(String name, Matrix4f matrix) {
        return setUniform(name, UniformType.Matrix4f, matrix);
    }

    /**
     * Define o valor de um uniforme dentro da malha
     * @param name Nome do uniforme
     * @param vector valor a ser definido
     * @return A própria malha
     */
    public Mesh setUniform(String name, Vector2f vector) {
        return setUniform(name, UniformType.Vector2f, vector);
    }

    /**
     * Define o valor de um uniforme dentro da malha
     * @param name Nome do uniforme
     * @param vector valor a ser definido
     * @return A própria malha
     */
    public Mesh setUniform(String name, Vector3f vector) {
        return setUniform(name, UniformType.Vector3f, vector);
    }

    /**
     * Define o valor de um uniforme dentro da malha
     * @param name Nome do uniforme
     * @param vector valor a ser definido
     * @return A própria malha
     */
    public Mesh setUniform(String name, Vector4f vector) {
        return setUniform(name, UniformType.Vector4f, vector);
    }

    /**
     * Define o valor de um uniforme dentro da malha
     * @param name Nome do uniforme
     * @param value valor a ser definido
     * @return A própria malha
     */
    public Mesh setUniform(String name, float value) {
        return setUniform(name, UniformType.Float, value);
    }

    /**
     * Define o valor de um uniforme dentro da malha
     * @param name Nome do uniforme
     * @param value valor a ser definido
     * @return A própria malha
     */
    public Mesh setUniform(String name, int value) {
        return setUniform(name, UniformType.Integer, value);
    }

    /**
     * Define o valor de um uniforme dentro da malha
     * @param name Nome do uniforme
     * @param value valor a ser definido
     * @return A própria malha
     */
    public Mesh setUniform(String name, boolean value) {
        return setUniform(name, UniformType.Boolean, value);
    }

    /**
     * Define se a malha sera desenhada no modo wireframe ou não
     * @param wireframe True para desenhar wireframe
     * @return A própria malha.
     */
    public Mesh setWireframe(boolean wireframe) {
        this.wireframe = wireframe;
        return this;
    }

    /**
     * @return True se desenhará apenas o wireframe, falso se a malha será desenhada de maneira sólida
     */
    public boolean isWireframe() {
        return wireframe;
    }

    /**
     * Desenha a malha utilizando o material especificado.
     * @return A própria mesh
     */
    public Mesh draw(Material material) {
        if (material == null || attributes.size() == 0) {
            return this;
        }

        glPolygonMode(GL_FRONT_AND_BACK, wireframe ? GL_LINE : GL_FILL);

        Shader shader = material.getShader();
        glBindVertexArray(id);
        shader.bind();
        for (Map.Entry<String, ArrayBuffer> attribute : attributes.entrySet()) {
            ArrayBuffer buffer = attribute.getValue();
            buffer.bind();
            shader.setAttribute(attribute.getKey(), buffer);
            buffer.unbind();
        }

        for (Map.Entry<String, Uniform> entry : uniforms.entrySet()) {
            entry.getValue().set(shader, entry.getKey());
        }

        material.apply();

        if (indexBuffer == null) {
            attributes.values().iterator().next().draw();
        } else {
            indexBuffer.draw();
        }

        // Faxina
        for (String attribute : attributes.keySet()) {
            shader.setAttribute(attribute, null);
        }

        shader.unbind();
        glBindVertexArray(0);
        return this;
    }
}