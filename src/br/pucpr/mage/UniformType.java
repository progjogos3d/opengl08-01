package br.pucpr.mage;

import org.joml.*;

/**
 * Contém os tipos de uniformes que podem ser inseridos dentro da malha.
 */
enum UniformType {
    Matrix3f {
        @Override
        public void set(Shader shader, String name, Object value) {
            shader.setUniform(name, (Matrix3f) value);
        }
    },
    Matrix4f {
        @Override
        public void set(Shader shader, String name, Object value) {
            shader.setUniform(name, (Matrix4f) value);
        }
    },
    Vector2f {
        @Override
        public void set(Shader shader, String name, Object value) {
            shader.setUniform(name, (Vector2f) value);
        }
    },
    Vector3f {
        @Override
        public void set(Shader shader, String name, Object value) {
            shader.setUniform(name, (Vector3f) value);
        }
    },
    Vector4f {
        @Override
        public void set(Shader shader, String name, Object value) {
            shader.setUniform(name, (Vector4f) value);
        }
    },
    Integer {
        @Override
        public void set(Shader shader, String name, Object value) {
            shader.setUniform(name, (Integer) value);
        }
    },
    Float {
        @Override
        public void set(Shader shader, String name, Object value) {
            shader.setUniform(name, (Float) value);
        }
    },
    Boolean {
        @Override
        public void set(Shader shader, String name, Object value) {
            shader.setUniform(name, (Boolean) value);
        }
    };

    /**
     * Define o valor desse uniforme no shader passado por parâmetro. Faz o cast apropriado.
     * @param shader O shader a ser utilizado
     * @param name Nome do uniforme a ser definido
     * @param value Valor do uniforme.
     */
    public abstract void set(Shader shader, String name, Object value);
}
