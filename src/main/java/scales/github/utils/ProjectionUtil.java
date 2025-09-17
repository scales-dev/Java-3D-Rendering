package scales.github.utils;

import scales.github.constructors.*;

public class ProjectionUtil {
    public static ModelFace worldToScreen(ModelFace modelFace, Transformations transformations) {
        Matrix3x3[] modelMatrices = getRotationMatrices(transformations.rotation);
        Vec2 negativePlayerRotation = new Vec2(-Camera.rotation.x, -Camera.rotation.y);
        Matrix3x3[] cameraMatrices = getRotationMatrices(new Vec3(negativePlayerRotation.x, negativePlayerRotation.y, 0));

        Triangle worldTriangle = new Triangle(Vec3.ZERO, Vec3.ZERO, Vec3.ZERO);
        Triangle renderTriangle = new Triangle(Vec3.ZERO, Vec3.ZERO, Vec3.ZERO);
        Triangle normals = new Triangle(Vec3.ZERO, Vec3.ZERO, Vec3.ZERO);

        for (int i = 0; i < 3; i++) {
            // transform point on model, offset it, then transform from camera
            // apply roll -> yaw -> pitch
            Vec3 transformedOnModel = modelMatrices[0].transform(modelMatrices[1].transform(modelMatrices[2].transform(modelFace.triangle.getPoint(i))));
            // normals need to be rotated too for lighting to be correct
            Vec3 transformedNormal = modelMatrices[0].transform(modelMatrices[1].transform(modelMatrices[2].transform(modelFace.normals.getPoint(i))));
            // world point is needed for lighting and collisions
            Vec3 worldTrianglePos = transformedOnModel.multiply(transformations.scale).add(transformations.pos);

            Vec3 clientSideTrianglePos = worldTrianglePos.subtract(Camera.pos);

            Vec3 transformedFromCamera = cameraMatrices[0].transform(cameraMatrices[1].transform(cameraMatrices[2].transform(clientSideTrianglePos)));

            double pixelsPerWorldUnit = C.FRAME_BOUNDS.getHeight() / transformedFromCamera.z;

            Vec2 pixelOffset = new Vec2(transformedFromCamera.x * pixelsPerWorldUnit, transformedFromCamera.y * pixelsPerWorldUnit);
            Vec3 screenSpacePixel = new Vec3(pixelOffset.x + C.FRAME_IMAGE.getWidth() / 2f, C.FRAME_IMAGE.getHeight() / 2f - pixelOffset.y, transformedFromCamera.z);

            renderTriangle.setPoint(i, screenSpacePixel);
            worldTriangle.setPoint(i, worldTrianglePos);
            normals.setPoint(i, transformedNormal);
        }

        return new ModelFace(renderTriangle, worldTriangle, modelFace.textureCoordinates, normals);
    }

    public static Matrix3x3[] getRotationMatrices(Vec3 rotation) {
        double pitch = Math.toRadians(rotation.x);
        double yaw = Math.toRadians(rotation.y);
        double roll = Math.toRadians(rotation.z);

        // https://wikimedia.org/api/rest_v1/media/math/render/svg/41412701e4dd3dd0d85ad01077b00a882469687b
        Matrix3x3 xTransform = new Matrix3x3(
                new Vec3(1, 0, 0),
                new Vec3(0, Math.cos(yaw), Math.sin(yaw)),
                new Vec3(0, -Math.sin(yaw), Math.cos(yaw))
        );

        Matrix3x3 yTransform = new Matrix3x3(
                new Vec3(Math.cos(pitch), 0, -Math.sin(pitch)),
                new Vec3(0, 1, 0),
                new Vec3(Math.sin(pitch), 0, Math.cos(pitch))
        );

        Matrix3x3 zTransform = new Matrix3x3(
                new Vec3(Math.cos(roll), Math.sin(roll), 0),
                new Vec3(-Math.sin(roll), Math.cos(roll), 0),
                new Vec3(0, 0, 1)
        );

        return new Matrix3x3[] {xTransform, yTransform, zTransform};
    }
}
