package sources.net.thenoppy12.noppyaddon.util;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class VectorConversionUtil {
    public static Vec3i convert(Vec3d vec3d) {
        int x = (int) Math.floor(vec3d.getX());
        int y = (int) Math.floor(vec3d.getY());
        int z = (int) Math.floor(vec3d.getZ());
        return new Vec3i(x, y, z);
    }
}
