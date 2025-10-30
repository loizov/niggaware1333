package ru.niggaware.util;

import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

@FunctionalInterface
public interface RotationAxis {
    RotationAxis NEGATIVE_X = (rad) -> new Quaternion(Vector3f.XP, -rad, true);
    RotationAxis POSITIVE_X = (rad) -> new Quaternion(Vector3f.XP, rad, true);
    RotationAxis NEGATIVE_Y = (rad) -> new Quaternion(Vector3f.YP, -rad, true);
    RotationAxis POSITIVE_Y = (rad) -> new Quaternion(Vector3f.YP, rad, true);
    RotationAxis NEGATIVE_Z = (rad) -> new Quaternion(Vector3f.ZP, -rad, true);
    RotationAxis POSITIVE_Z = (rad) -> new Quaternion(Vector3f.ZP, rad, true);

    static RotationAxis of(Vector3f axis) {
        return (rad) -> new Quaternion(axis, rad, true);
    }

    Quaternion rotation(float rad);

    default Quaternion rotationDegrees(float deg) {
        return this.rotation(deg * 0.017453292F);
    }
}