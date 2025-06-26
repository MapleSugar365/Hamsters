package com.starfish_studios.hamsters.entity.util;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.*;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MMPathNavigatorGround extends GroundPathNavigation {

    // Credit to Mowzie's Mobs

    public MMPathNavigatorGround(Mob entity, Level world) {
        super(entity, world);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new WalkNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return new MMPathFinder(this.nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected void followThePath() {

        Path path = Objects.requireNonNull(this.path);
        Vec3 entityPos = this.getTempMobPos();
        int pathLength = path.getNodeCount();

        for (int nodeCount = path.getNextNodeIndex(); nodeCount < path.getNodeCount(); nodeCount++) {
            if (path.getNode(nodeCount).y != Math.floor(entityPos.y)) {
                pathLength = nodeCount;
                break;
            }
        }

        final Vec3 base = entityPos.add(-this.mob.getBbWidth() * 0.5F, 0.0F, -this.mob.getBbWidth() * 0.5F);
        final Vec3 max = base.add(this.mob.getBbWidth(), this.mob.getBbHeight(), this.mob.getBbWidth());

        if (this.tryShortcut(path, new Vec3(this.mob.getX(), this.mob.getY(), this.mob.getZ()), pathLength, base,
                max)) {
            if (this.isAt(path, 0.5F)
                    || this.atElevationChange(path) && this.isAt(path, this.mob.getBbWidth() * 0.5F)) {
                path.setNextNodeIndex(path.getNextNodeIndex() + 1);
            }
        }

        this.doStuckDetection(entityPos);
    }

    private boolean isAt(Path path, float threshold) {
        final Vec3 pathPos = path.getNextEntityPos(this.mob);
        return Mth.abs((float) (this.mob.getX() - pathPos.x)) < threshold
                && Mth.abs((float) (this.mob.getZ() - pathPos.z)) < threshold
                && Math.abs(this.mob.getY() - pathPos.y) < 1.0D;
    }

    private boolean atElevationChange(Path path) {

        final int curr = path.getNextNodeIndex();
        final int end = Math.min(path.getNodeCount(), curr + Mth.ceil(this.mob.getBbWidth() * 0.5F) + 1);
        final int currY = path.getNode(curr).y;

        for (int i = curr + 1; i < end; i++) {
            if (path.getNode(i).y != currY)
                return true;
        }

        return false;
    }

    private boolean tryShortcut(Path path, Vec3 entityPos, int pathLength, Vec3 base, Vec3 max) {

        for (int nextNode = pathLength; --nextNode > path.getNextNodeIndex();) {

            final Vec3 vec = path.getEntityPosAtNode(this.mob, nextNode).subtract(entityPos);

            if (this.sweep(vec, base, max)) {
                path.setNextNodeIndex(nextNode);
                return false;
            }
        }

        return true;
    }

    static final float EPSILON = 1.0E-8F;

    // Based off of
    // https://github.com/andyhall/voxel-aabb-sweep/blob/d3ef85b19c10e4c9d2395c186f9661b052c50dc7/index.js

    private boolean sweep(Vec3 vec, Vec3 base, Vec3 max) {

        float t = 0.0F;
        float max_t = (float) vec.length();
        if (max_t < EPSILON)
            return true;

        final float[] tr = new float[3];
        final int[] ldi = new int[3];
        final int[] tri = new int[3];
        final int[] step = new int[3];
        final float[] tDelta = new float[3];
        final float[] tNext = new float[3];
        final float[] normed = new float[3];

        for (int i = 0; i < 3; i++) {
            float value = element(vec, i);
            boolean dir = value >= 0.0F;
            step[i] = dir ? 1 : -1;
            float lead = element(dir ? max : base, i);
            tr[i] = element(dir ? base : max, i);
            ldi[i] = leadEdgeToInt(lead, step[i]);
            tri[i] = trailEdgeToInt(tr[i], step[i]);
            normed[i] = value / max_t;
            tDelta[i] = Mth.abs(max_t / value);
            float dist = dir ? (ldi[i] + 1 - lead) : (lead - ldi[i]);
            tNext[i] = tDelta[i] < Float.POSITIVE_INFINITY ? tDelta[i] * dist : Float.POSITIVE_INFINITY;
        }

        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        do {

            // stepForward

            int axis = (tNext[0] < tNext[1]) ? ((tNext[0] < tNext[2]) ? 0 : 2) : ((tNext[1] < tNext[2]) ? 1 : 2);
            float dt = tNext[axis] - t;
            t = tNext[axis];
            ldi[axis] += step[axis];
            tNext[axis] += tDelta[axis];

            for (int i = 0; i < 3; i++) {
                tr[i] += dt * normed[i];
                tri[i] = trailEdgeToInt(tr[i], step[i]);
            }

            // checkCollision

            int stepx = step[0];
            int x0 = (axis == 0) ? ldi[0] : tri[0];
            int x1 = ldi[0] + stepx;
            int stepy = step[1];
            int y0 = (axis == 1) ? ldi[1] : tri[1];
            int y1 = ldi[1] + stepy;
            int stepz = step[2];
            int z0 = (axis == 2) ? ldi[2] : tri[2];
            int z1 = ldi[2] + stepz;
            for (int x = x0; x != x1; x += stepx) {
                for (int z = z0; z != z1; z += stepz) {
                    for (int y = y0; y != y1; y += stepy) {
                        BlockState block = this.level.getBlockState(pos.set(x, y, z));
                        if (!block.isPathfindable(PathComputationType.LAND))
                            return false;
                    }
                    pos.set(x, y0 - 1, z);
                    PathType below = this.nodeEvaluator.getPathType(this.mob, pos);
                    if (below == PathType.WATER || below == PathType.LAVA || below == PathType.OPEN)
                        return false;
                    PathType in = this.nodeEvaluator.getPathType(this.mob, pos);
                    float priority = this.mob.getPathfindingMalus(in);
                    if (priority < 0.0F || priority >= 8.0F)
                        return false;
                    if (in == PathType.DAMAGE_FIRE || in == PathType.DANGER_FIRE || in == PathType.DAMAGE_OTHER)
                        return false;
                }
            }
        }

        while (t <= max_t);
        return true;
    }

    static int leadEdgeToInt(float coord, int step) {
        return Mth.floor(coord - step * EPSILON);
    }

    static int trailEdgeToInt(float coord, int step) {
        return Mth.floor(coord + step * EPSILON);
    }

    static float element(Vec3 v, int i) {
        return switch (i) {
            case 0 -> (float) v.x;
            case 1 -> (float) v.y;
            case 2 -> (float) v.z;
            default -> 0.0F;
        };
    }

    public static class MMPathFinder extends PathFinder {

        // Credit to Mowzie's Mobs

        public MMPathFinder(NodeEvaluator processor, int maxVisitedNodes) {
            super(processor, maxVisitedNodes);
        }

        @Override
        public Path findPath(@NotNull PathNavigationRegion regionIn, @NotNull Mob mob,
                @NotNull Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
            Path path = super.findPath(regionIn, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);
            return path == null ? null : new MMPathFinder.PatchedPath(path);
        }

        static class PatchedPath extends Path {

            public PatchedPath(Path original) {
                super(copyPathPoints(original), original.getTarget(), original.canReach());
            }

            @Override
            public @NotNull Vec3 getEntityPosAtNode(Entity entity, int index) {
                Node point = this.getNode(index);
                double x = point.x + Mth.floor(entity.getBbWidth() + 1.0F) * 0.5D;
                double y = point.y;
                double z = point.z + Mth.floor(entity.getBbWidth() + 1.0F) * 0.5D;
                return new Vec3(x, y, z);
            }

            private static List<Node> copyPathPoints(Path original) {
                List<Node> points = new ArrayList<>();
                for (int nodeCount = 0; nodeCount < original.getNodeCount(); nodeCount++)
                    points.add(original.getNode(nodeCount));
                return points;
            }
        }
    }
}