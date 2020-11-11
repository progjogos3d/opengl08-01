package br.pucpr.mage;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.stb.STBImage.stbi_load_from_memory;

public class Image {
    private ByteBuffer pixels;

    private int width;
    private int height;
    private int channels;

    private ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
        var newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    private ByteBuffer loadResourceToBuffer(String resource) throws IOException {
        ByteBuffer buffer;

        var path = Paths.get(resource);
        if (Files.isReadable(path)) {
            try (var fc = Files.newByteChannel(path)) {
                buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
                while (fc.read(buffer) != -1) {
                    //Just keep reading
                }
            }
        } else {
            try (var source = getClass().getResourceAsStream("/br/pucpr/resource/" + resource);
                    var rbc = Channels.newChannel(source)) {
                buffer = BufferUtils.createByteBuffer(8 * 1024);

                while (true) {
                    var bytes = rbc.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    if (buffer.remaining() == 0) {
                        buffer = resizeBuffer(buffer, buffer.capacity() * 2);
                    }
                }
            }
        }

        buffer.flip();
        return buffer;
    }

    public Image(String path) {
        try (var stack = MemoryStack.stackPush()){
            var buffer = loadResourceToBuffer(path);
            var w = stack.mallocInt(1);
            var h = stack.mallocInt(1);
            var c = stack.mallocInt(1);
    
            pixels = stbi_load_from_memory(buffer, w, h, c, 0);
            if (pixels == null) {
                throw new RuntimeException("Failed to load image: " + path);
            }
    
            width = w.get();
            height = h.get();
            channels = c.get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load image: " + path, e);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getChannels() {
        return channels;
    }

    public ByteBuffer getPixels() {
        return pixels.asReadOnlyBuffer();
    }
}
