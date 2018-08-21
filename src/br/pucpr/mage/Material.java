package br.pucpr.mage;

public interface Material {
    void setShader(Shader shader);
    Shader getShader();
    void apply();
}
