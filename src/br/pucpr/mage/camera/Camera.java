package br.pucpr.mage.camera;

import br.pucpr.mage.Shader;
import br.pucpr.mage.ShaderItem;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import static br.pucpr.mage.MathUtil.asString;
import static org.joml.Math.toRadians;
import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;


/**
 * Representa a camera. Toda camera é formada por dois tipos de transformacoes:
 * - View: Que posiciona a camera no mundo. Forma a partir da posicao da camera, local para onde a camera olha e vetor
 * up
 * - Projection: Que indica a abertura da camera. Formada pelos planos near, far, taxa de proporção (aspecto) e angulo
 * de abertura da camera (fov)
 */
public class Camera implements ShaderItem {
    private Vector3f position = new Vector3f(0,0,2);    //Onde a camera está
    private Vector3f up = new Vector3f(0, 1, 0);        //Deve apontar para "cima"
    private Vector3f target = new Vector3f(0,0,0);      //POSIÇÃO para onde a camera olha
    private float fov = toRadians(60.f);                  //Angulo de abertura em y
    private float near = 0.1f;                                    //Distancia mais proxima que a camera enxerga
    private float far = 1000.0f;                                  //Distancia mais afastada que a camera enxerga

    /**
     * @return A posição da câmera no mundo
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * @return Indica qual é o lado de "cima" para a camera. Incline esse vetor caso queira a camera deitada
     */
    public Vector3f getUp() {
        return up;
    }

    /**
     * @return A POSIÇÃO para onde a camera está olhando
     */
    public Vector3f getTarget() {
        return target;
    }

    /**
     * @return Angulo de abertura da camera em y, também chamado de campo de visão (Field of View)
     */
    public float getFov() {
        return fov;
    }

    /**
     * Troca o angulo de abertura da camera, também chamado de campo de visão (Field of View)
     * @param fov Novo angulo, em radianos. Utilize um valor entre 45 e 60 graus para uma visualização mais natural
     */
    public Camera setFov(float fov) {
        this.fov = fov;
        return this;
    }

    /**
     * Plano mais próximo de visualização da camera. Tudo o que estiver atrás desse plano não será visto.
     * @return A distância até o plano.
     */
    public float getNear() {
        return near;
    }

    /**
     * Define o plano mais próximo de visualização da camera. Tudo o que estiver atrás desse plano não será visto.
     * @param near A distância até o plano. Deve obrigatoriamente ser maior do que 0.
     */
    public Camera setNear(float near) {
        this.near = near;
        return this;
    }

    /**
     * Plano mais afastado de visualização da camera. Tudo o que estiver adiante desse plano não será visto.
     * @return A distancia até o plano
     */
    public float getFar() {
        return far;
    }

    /**
     * Define o plano mais afastado de visualização da camera. Tudo o que estiver adiante desse plano não será visto.
     * @param far A distância até o plano. Deve obrigatoriamente ser maior do que o plano near.
     */
    public Camera setFar(float far) {
        this.far = far;
        return this;
    }

    /**
     * @return A proporção da tela. A proporção é dada pela largura / altura.
     */
    public float getAspect() {
        try (var stack = MemoryStack.stackPush()) {
            var w = stack.mallocInt(1);
            var h = stack.mallocInt(1);
            long window = glfwGetCurrentContext();
            glfwGetWindowSize(window, w, h);
            return w.get() / (float) h.get();
        }
    }

    /**
     * @return A matriz view, calculada com base nos campos da camera.
     */
    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(getPosition(), getTarget(), getUp());
    }

    /**
     * @return A matriz projection, calculada com base nos campos da camera.
     */
    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().perspective(fov, getAspect(), near, far);
    }

    /**
     * Define as matrizes de projeção nas propriedades uProjection e uView do shader.
     * @param shader Shader onde será definido. Já deve ter sofrido bind.
     */
    public void apply(Shader shader) {
        shader.setUniform("uProjection", getProjectionMatrix())
              .setUniform("uView", getViewMatrix())
              .setUniform("uCameraPos", getPosition());
    }

    @Override
    public String toString() {
        return "Camera position=" + asString(getPosition()) + " target=" + asString(getTarget()) + " up: " + asString(getUp());
    }
}
