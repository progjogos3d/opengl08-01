package br.pucpr.mage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {
    private int id;
    private TextureParameters parameters;

    public Texture(Image image, TextureParameters parameters) {
        //Valida os parametros
        if (image.getChannels() < 3) {
            throw new IllegalArgumentException("Image must be RGB or RGBA!");
        }
        if (parameters == null) {
            throw new IllegalArgumentException("Parameters can't be null!");
        }

        int format = image.getChannels() == 3 ? GL_RGB : GL_RGBA;
        
        //Copia os dados        
        id = glGenTextures();        
        glBindTexture(GL_TEXTURE_2D, id);        
        glTexImage2D(GL_TEXTURE_2D, 0, format, 
                image.getWidth(), image.getHeight(), 0,  
                format, GL_UNSIGNED_BYTE, image.getPixels());
        
        //Ajuste dos parametros
        this.parameters = parameters;
        parameters.apply(GL_TEXTURE_2D);
        if (parameters.isMipMapped()) {
            glGenerateMipmap(GL_TEXTURE_2D);
        }
        
        //Limpeza
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public Texture(Image image) {
        this(image, new TextureParameters());
    }
    
    public Texture(String imagePath, TextureParameters params) {
        this(new Image(imagePath), params);
    }
    
    public Texture(String imagePath) {
        this(new Image(imagePath));
    }
    
    public int getId() {
        return id;
    }
    
    public TextureParameters getParameters() {
        return parameters;
    }
    
    public void setParameters(TextureParameters parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("Parameters can't be null!");
        }
    
        this.parameters = parameters;
    
        bind();
        parameters.apply(GL_TEXTURE_2D);
        unbind();
    }

    public Texture bind() {
        glBindTexture(GL_TEXTURE_2D, id);
        return this;
    }

    public Texture unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
        return this;
    }
}
