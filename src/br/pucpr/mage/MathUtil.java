package br.pucpr.mage;

import org.joml.*;

import java.util.Locale;

public class MathUtil {
    public static Matrix4f invert(Matrix4fc m) {
        return new Matrix4f(m).invert();
    }

    public static Matrix4f transpose(Matrix4fc m) {
        return new Matrix4f(m).transpose();
    }

    public static Matrix4f mul(Matrix4f l, Matrix4f r) {
        return new Matrix4f(l).mul(r);
    }

    public static Matrix4f scale(Matrix4fc m, float scale) {
        return new Matrix4f(m).scale(scale);
    }

    public static Matrix4f scale(Matrix4fc m, Vector3fc scale) {
        return new Matrix4f(m).scale(scale);
    }

    public static Matrix4f translate(Matrix4fc m, Vector3fc offset) {
        return new Matrix4f(m).translate(offset);
    }

    public static Matrix4f translate(Matrix4fc m, float x, float y, float z) {
        return new Matrix4f(m).translate(x, y, z);
    }

    public static Vector4fc transform(Matrix4fc m, Vector4fc v) {
        return m.transform(v, new Vector4f());
    }

    public static Vector4fc transform(Matrix4fc m, Vector3fc v, float w) {
        return m.transform(new Vector4f(v, w));
    }

    public static Matrix4f rotateX(Matrix4fc m, float angle) {
        return new Matrix4f(m).rotateX(angle);
    }

    public static Matrix4f rotateY(Matrix4fc m, float angle) {
        return new Matrix4f(m).rotateY(angle);
    }

    public static Matrix4f rotateZ(Matrix4fc m, float angle) {
        return new Matrix4f(m).rotateZ(angle);
    }

    public static Vector3f add(Vector3fc a, Vector3f b) {
        return new Vector3f(a).add(b);
    }

    public static Vector3f add(Vector3fc a, float x, float y, float z) {
        return new Vector3f(a).add(x, y, z);
    }

    public static Vector3f sub(Vector3fc a, Vector3f b) {
        return new Vector3f(a).sub(b);
    }

    public static Vector3f sub(Vector3fc a, float x, float y, float z) {
        return new Vector3f(a).sub(x, y, z);
    }

    public static Vector3f mul(Vector3fc v, float s) {
        return new Vector3f(v).mul(s);
    }

    public static Vector3f mul(Vector3fc a, Vector3fc b) {
        return new Vector3f(a).mul(b);
    }

    public static Vector3f mul(Vector3fc a, float x, float y, float z) {
        return new Vector3f(a).mul(x, y, z);
    }

    public static Vector3f cross(Vector3fc a, Vector3fc b) {
        return new Vector3f(a).cross(b);
    }

    public static Vector3f cross(Vector3fc a, float x, float y, float z) {
        return new Vector3f(a).cross(x, y, z);
    }

    public static Vector3f normalize(Vector3fc v) {
        return new Vector3f(v).normalize();
    }

    public static float dot(Vector3fc a, Vector3fc b) {
        return a.dot(b);
    }

    public static float dot(Vector3fc a, float x, float y, float z) {
        return a.dot(x, y, z);
    }

    public static Vector3f negate(Vector3fc vec) {
        return new Vector3f(vec);
    }

    public static Vector3f lerp(Vector3fc a, Vector3fc b, float t) {
        return a.lerp(b, t, new Vector3f());
    }

    public static Vector3f reflect(Vector3fc ray, Vector3fc normal) {
        return ray.reflect(normal, new Vector3f());
    }

    public static String asString(Vector3fc vec) {
        return String.format(Locale.US, "[%.2f, %.2f, %.2f]", vec.x(),  vec.y(), vec.z());
    }

}
