package scales.github.utils;

import scales.github.constructors.*;
import scales.github.scene.Models;
import scales.github.scene.World;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class RenderUtil {
    private static double[][] depthBuffer;
    private static final double nearClipDistance = 1e-4d;

    private static final boolean showLightSources = true;

    public static void drawWorld() {
        // debugging
        if (showLightSources) {
            for (LightSource lightSource : World.lightSources) {
                Color lightSourceColour = new Color((float) lightSource.colour.x, (float)  lightSource.colour.y, (float)  lightSource.colour.z);
                drawModel(
                        new Model(Models.CUBE.modelFaces, ImageUtil.getTexture(lightSourceColour)),
                        // inverted scale because it reverses the normals therefore making it glow instead of black because all the normals face away.
                        new Transformations(lightSource.position, new Vec3(-1, -1, -1), Vec3.ZERO)
                );
            }
        }

        for (WorldModel worldModel : World.worldModels) {
            drawModel(worldModel.model, worldModel.transformations);
        }
    }

    public static void drawModel(Model model, Transformations transformations) {
        for (ModelFace modelFace : model.triangles) {
            ModelFace transformedTriangle = ProjectionUtil.worldToScreen(modelFace, transformations);

            boolean clip1 = transformedTriangle.triangle.pointA.z <= nearClipDistance;
            boolean clip2 = transformedTriangle.triangle.pointB.z <= nearClipDistance;
            boolean clip3 = transformedTriangle.triangle.pointC.z <= nearClipDistance;
            int clippedTotal = (clip1 ? 1 : 0) + (clip2 ? 1 : 0) + (clip3 ? 1 : 0);

            // maybe not my finest work
            switch (clippedTotal) {
                // if no points are behind the camera just draw normally
                case 0 -> {
                    draw3dTriangle(transformedTriangle, model.texture);
                }

                // if only one point is behind the camera we have to split the triangle into 2 new triangles in front of the camera
                case 1 -> {
                    int clipIndex = clip1 ? 0 : clip2 ? 1 : 2;
                    Triangle[] splitTriangles = splitTriangle(modelFace, transformedTriangle.triangle, clipIndex);

                    ModelFace newTriangleA = ProjectionUtil.worldToScreen(
                            new ModelFace(
                                    new Triangle(splitTriangles[4].pointA, splitTriangles[3].pointA, splitTriangles[2].pointA),
                                    new Triangle(splitTriangles[4].pointB, splitTriangles[3].pointB, splitTriangles[2].pointB),
                                    new Triangle(splitTriangles[4].pointC, splitTriangles[3].pointC, splitTriangles[2].pointC)
                            ),
                            transformations
                    );
                    ModelFace newTriangleB = ProjectionUtil.worldToScreen(
                            new ModelFace(
                                    new Triangle(splitTriangles[3].pointA, splitTriangles[1].pointA, splitTriangles[2].pointA),
                                    new Triangle(splitTriangles[3].pointB, splitTriangles[1].pointB, splitTriangles[2].pointB),
                                    new Triangle(splitTriangles[3].pointC, splitTriangles[1].pointC, splitTriangles[2].pointC)
                            ),
                            transformations
                    );

                    draw3dTriangle(newTriangleA, model.texture);
                    draw3dTriangle(newTriangleB, model.texture);
                }

                // if two points are behind the camera we clip the triangle into a smaller triangle in front of the camera.
                case 2 -> {
                    int notClippedIndex = !clip1 ? 0 : !clip2 ? 1 : 2;
                    Triangle[] splitTriangles = splitTriangle(modelFace, transformedTriangle.triangle, notClippedIndex);

                    ModelFace newTriangle = ProjectionUtil.worldToScreen(
                            new ModelFace(
                                    new Triangle(splitTriangles[4].pointA, splitTriangles[0].pointA, splitTriangles[3].pointA),
                                    new Triangle(splitTriangles[4].pointB, splitTriangles[0].pointB, splitTriangles[3].pointB),
                                    new Triangle(splitTriangles[4].pointC, splitTriangles[0].pointC, splitTriangles[3].pointC)
                            ),
                            transformations
                    );

                    draw3dTriangle(newTriangle, model.texture);
                }

                // if all points are behind the camera nothing happens, we havent yet added a rearview mirror.
            }
        }
    }

    /// returns
    /// 0: odd point out (clipped/not clipped point)
    /// 1: pointA
    /// 2: pointB
    /// 3: clippedPointA
    /// 4: clippedPointB
    private static Triangle[] splitTriangle(ModelFace modelFace, Triangle transformedTriangle, int clipIndex) {
        int notClippedA = (clipIndex + 1) % 3;
        int notClippedB = (clipIndex + 2) % 3;

        Triangle oddPointOut = modelFace.getPoint(clipIndex);
        double oddPointOutDepth = transformedTriangle.getPoint(clipIndex).z;

        Triangle pointA = modelFace.getPoint(notClippedA);
        double pointADepth = transformedTriangle.getPoint(notClippedA).z;

        Triangle pointB = modelFace.getPoint(notClippedB);
        double pointBDepth = transformedTriangle.getPoint(notClippedB).z;

        double fracA = (nearClipDistance - oddPointOutDepth) / (pointADepth - oddPointOutDepth);
        double fracB = (nearClipDistance - oddPointOutDepth) / (pointBDepth - oddPointOutDepth);

        Triangle clippedPointA = oddPointOut.lerp(pointA, fracA);
        Triangle clippedPointB = oddPointOut.lerp(pointB, fracB);

        return new Triangle[] {oddPointOut, pointA, pointB, clippedPointA, clippedPointB};
    }

    public static void beginFrame() {
        depthBuffer = new double[C.FRAME_IMAGE.getWidth()][C.FRAME_IMAGE.getHeight()];
    }

    public static void finishFrame(BufferStrategy bs) {
        bs.getDrawGraphics().drawImage(C.FRAME_IMAGE, 0, 0, null);
        bs.show();

        C.FRAME_IMAGE = new BufferedImage(C.FRAME_BOUNDS.width, C.FRAME_BOUNDS.height, BufferedImage.TYPE_INT_RGB);
    }

    private static void draw3dTriangle(ModelFace modelFace, BufferedImage texture) {
        int rectX = Math.clamp((int) modelFace.triangle.minX(), 0, C.FRAME_IMAGE.getWidth()-1);
        int rectY = Math.clamp((int) modelFace.triangle.minY(), 0, C.FRAME_IMAGE.getHeight()-1);

        int rectW = Math.clamp((int) modelFace.triangle.maxX(), 0, C.FRAME_IMAGE.getWidth()-1);
        int rectH = Math.clamp((int) modelFace.triangle.maxY(), 0, C.FRAME_IMAGE.getHeight()-1);

        Vec3 depths = new Vec3(modelFace.triangle.pointA.z, modelFace.triangle.pointB.z, modelFace.triangle.pointC.z);
        Vec3 inverseDepths = depths.inverse();

        Triangle worldPos = modelFace.worldPos.divide(depths);
        Triangle textureCoordinates = modelFace.textureCoordinates.divide(depths);
        Triangle normal = modelFace.normals.divide(depths);

        for (int x = rectX; x <= rectW; x++) {
            for (int y = rectY; y <= rectH; y++) {
                Vec3 pointInTriangle = modelFace.triangle.getPointInTriangle(new Vec2(x, y));
                if (pointInTriangle.x <= 0 || pointInTriangle.y <= 0 || pointInTriangle.z <= 0) continue;

                double depth = 1 / inverseDepths.dot(pointInTriangle);

                // depth - 0.01 to stop very close triangles z fighting
                if (depthBuffer[x][y] != 0 && depthBuffer[x][y] < depth - 0.01) continue;

                int colour = ColourUtil.getPixelColour(
                        worldPos.multiply(pointInTriangle).sum().multiply(depth),
                        textureCoordinates.multiply(pointInTriangle).sum().multiply(depth).toVec2(),
                        normal.multiply(pointInTriangle).sum().multiply(depth),
                        texture
                );

                C.FRAME_IMAGE.setRGB(x, y, colour);
                depthBuffer[x][y] = depth;
            }
        }
    }
}
