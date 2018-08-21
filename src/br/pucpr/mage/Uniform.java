package br.pucpr.mage;

/**
 * Representa um uniforme dentro da malha. Contém seu tipo e valor.
 */
class Uniform {
    private UniformType type;
    private Object value;

    /**
     * Cria um novo uniforme.
     * @param type Tipo do uniforme sendo armazenado
     * @param value Valor do uniform.
     */
    public Uniform(UniformType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Define o valor desse uniforme no shader passado por parâmetro
     * @param shader Shader a ser definido
     * @param name Nome do uniforme.
     */
    public void set(Shader shader, String name) {
        type.set(shader, name, value);
    }
}