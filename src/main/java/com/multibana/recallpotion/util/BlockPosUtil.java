package com.multibana.recallpotion.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BlockPosUtil {
    public static BlockPos fromVec3d(Vec3d vec3d) {
        return new BlockPos((int)Math.round(vec3d.x), (int)Math.round(vec3d.y), (int)Math.round(vec3d.z));
    }
}
