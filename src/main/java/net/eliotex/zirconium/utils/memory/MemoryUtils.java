package net.eliotex.zirconium.utils.memory;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MemoryUtilities {
    public static FloatBuffer memAllocFloat(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    public static IntBuffer memAllocInt(int size) {
        return BufferUtils.createIntBuffer(size);
    }

    public static ByteBuffer memAlloc(int size) {
        return BufferUtils.createByteBuffer(size);
    }

    public static FloatBuffer memRealloc(FloatBuffer oldBuffer, int newCapacity) {
        FloatBuffer newBuffer = memAllocFloat(newCapacity);
        oldBuffer.flip();
        newBuffer.put(oldBuffer).flip();
        return newBuffer;
    }

    public static IntBuffer memRealloc(IntBuffer oldBuffer, int newCapacity) {
        IntBuffer newBuffer = memAllocInt(newCapacity);
        oldBuffer.flip();
        newBuffer.put(oldBuffer).flip();
        return newBuffer;
    }

    public static ByteBuffer memRealloc(ByteBuffer oldBuffer, int newCapacity) {
        ByteBuffer newBuffer = memAlloc(newCapacity);
        oldBuffer.flip();
        newBuffer.put(oldBuffer).flip();
        return newBuffer;
    }
}