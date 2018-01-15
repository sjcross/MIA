package wbif.sjx.ModularImageAnalysis.Module.ObjectProcessing;

import ij.ImagePlus;
import ij.measure.Calibration;
import ij.plugin.Duplicator;
import wbif.sjx.ModularImageAnalysis.Exceptions.GenericMIAException;
import wbif.sjx.ModularImageAnalysis.Module.HCModule;
import wbif.sjx.ModularImageAnalysis.Module.Visualisation.AddObjectsOverlay;
import wbif.sjx.ModularImageAnalysis.Object.*;
import wbif.sjx.common.MathFunc.Indexer;
import wbif.sjx.common.MathFunc.MidpointCircle;
import wbif.sjx.common.Process.HoughTransform.Transforms.CircleHoughTransform;
import wbif.sjx.common.Process.IntensityMinMax;

import java.util.ArrayList;

/**
 * Created by sc13967 on 15/01/2018.
 */
public class HoughObjectDetection extends HCModule {
    public static final String INPUT_IMAGE = "Input image";
    public static final String OUTPUT_OBJECTS = "Output objects";
    public static final String MIN_RADIUS = "Minimum radius (px)";
    public static final String MAX_RADIUS = "Maximum radius (px)";
    public static final String NORMALISE_SCORES = "Normalise scores";
    public static final String DETECTION_THRESHOLD = "Detection threshold";
    public static final String EXCLUSION_RADIUS = "Exclusion radius (px)";
    public static final String SHOW_TRANSFORM_IMAGE = "Show transform image";
    public static final String SHOW_OBJECTS = "Show detected objects";


    private interface Measurements {
        String SCORE = "HOUGH_DETECTION//SCORE";

    }

    @Override
    public String getTitle() {
        return "Hough-based detection";
    }

    @Override
    public String getHelp() {
        return "CURRENTLY ONLY WORKS ON A SINGLE 2D IMAGE";
    }

    @Override
    protected void run(Workspace workspace, boolean verbose) throws GenericMIAException {
        // Getting input image
        String inputImageName = parameters.getValue(INPUT_IMAGE);
        Image inputImage = workspace.getImages().get(inputImageName);
        ImagePlus inputImagePlus = inputImage.getImagePlus();

        // Getting output image name
        String outputObjectsName = parameters.getValue(OUTPUT_OBJECTS);
        ObjCollection outputObjects = new ObjCollection(outputObjectsName);

        // Getting parameters
        int minRadius = parameters.getValue(MIN_RADIUS);
        int maxRadius = parameters.getValue(MAX_RADIUS);
        boolean normaliseScores = parameters.getValue(NORMALISE_SCORES);
        double detectionThreshold = parameters.getValue(DETECTION_THRESHOLD);
        int exclusionRadius = parameters.getValue(EXCLUSION_RADIUS);
        boolean showTransformImage = parameters.getValue(SHOW_TRANSFORM_IMAGE);
        boolean showObjects = parameters.getValue(SHOW_OBJECTS);

        // Storing the image calibration
        Calibration calibration = inputImagePlus.getCalibration();
        double dppXY = calibration.getX(1);
        double dppZ = calibration.getZ(1);
        String calibrationUnits = calibration.getUnits();

        // Initialising the Hough transform
        int[][] parameterRanges =
                new int[][]{{0,inputImagePlus.getWidth()-1},{0,inputImagePlus.getHeight()-1},{minRadius,maxRadius}};
        CircleHoughTransform circleHoughTransform = new CircleHoughTransform(inputImagePlus.getProcessor(),parameterRanges);

        // Running the transforms
        if (verbose) System.out.println("[" + moduleName + "] Running transform");
        circleHoughTransform.run();

        // Normalising scores based on the number of points in that circle
        if (normaliseScores) {
            if (verbose) System.out.println("[" + moduleName + "] Normalising scores");
            circleHoughTransform.normaliseScores();
        }

        // Getting the accumulator as an image
        if (showTransformImage) circleHoughTransform.getAccumulatorAsImage().show();

        // Getting circle objects and adding to workspace
        if (verbose) System.out.println("[" + moduleName + "] Detecting objects");
        ArrayList<double[]> circles = circleHoughTransform.getObjects(35,50);
        Indexer indexer = new Indexer(inputImagePlus.getWidth(),inputImagePlus.getHeight());
        for (double[] circle:circles) {
            // Initialising the object
            Obj outputObject = new Obj(outputObjectsName,outputObjects.getNextID(),dppXY,dppZ,calibrationUnits);

            // Getting circle parameters
            int x = (int) Math.round(circle[0]);
            int y = (int) Math.round(circle[1]);
            int r = (int) Math.round(circle[2]);
            double score = circle[3];

            // Getting coordinates corresponding to circle
            MidpointCircle midpointCircle = new MidpointCircle(r);
            int[] xx = midpointCircle.getXCircleFill();
            int[] yy = midpointCircle.getYCircleFill();

            for (int i=0;i<xx.length;i++) {
                int idx = indexer.getIndex(new int[]{xx[i]+x,yy[i]+y});
                if (idx == -1) continue;

                outputObject.addCoord(xx[i]+x,yy[i]+y,0);

            }

            // Adding measurements
            outputObject.addMeasurement(new Measurement(Measurements.SCORE,score));

            // Adding object to object set
            outputObjects.add(outputObject);

        }

        inputImagePlus.setPosition(1,1,1);
        workspace.addObjects(outputObjects);

//        if (parameters.getValue(SHOW_OBJECTS)) {
//            // Adding image to workspace
//            if (verbose)
//                System.out.println("[" + moduleName + "] Adding objects (" + outputObjectsName + ") to workspace");
//
//            // Creating a duplicate of the input image
//            inputImagePlus = new Duplicator().run(inputImagePlus);
//            IntensityMinMax.run(inputImagePlus, true);
//
//            // Creating the overlay
//            AddObjectsOverlay.createOverlay(inputImagePlus, outputObjects, "",
//                    AddObjectsOverlay.ColourModes.RANDOM_COLOUR,"", AddObjectsOverlay.PositionModes.ALL_POINTS, "",
//                    "", "", false, false, 0, "");
//
//            // Displaying the overlay
//            inputImagePlus.show();
//
//        }
    }

    @Override
    protected void initialiseParameters() {
        parameters.add(new Parameter(INPUT_IMAGE,Parameter.INPUT_IMAGE,null));
        parameters.add(new Parameter(OUTPUT_OBJECTS,Parameter.OUTPUT_OBJECTS,null));
        parameters.add(new Parameter(MIN_RADIUS,Parameter.INTEGER,10));
        parameters.add(new Parameter(MAX_RADIUS,Parameter.INTEGER,20));
        parameters.add(new Parameter(NORMALISE_SCORES,Parameter.BOOLEAN,true));
        parameters.add(new Parameter(DETECTION_THRESHOLD,Parameter.DOUBLE,1.0));
        parameters.add(new Parameter(EXCLUSION_RADIUS,Parameter.INTEGER,10));
        parameters.add(new Parameter(SHOW_TRANSFORM_IMAGE,Parameter.BOOLEAN,false));
        parameters.add(new Parameter(SHOW_OBJECTS,Parameter.BOOLEAN,false));

    }

    @Override
    protected void initialiseMeasurementReferences() {
        objectMeasurementReferences.add(new MeasurementReference(Measurements.SCORE));
    }

    @Override
    public ParameterCollection updateAndGetParameters() {
        return parameters;
    }

    @Override
    public MeasurementReferenceCollection updateAndGetImageMeasurementReferences() {
        return null;
    }

    @Override
    public MeasurementReferenceCollection updateAndGetObjectMeasurementReferences() {
        MeasurementReference score = objectMeasurementReferences.get(Measurements.SCORE);
        score.setImageObjName(parameters.getValue(OUTPUT_OBJECTS));

        return objectMeasurementReferences;

    }

    @Override
    public void addRelationships(RelationshipCollection relationships) {

    }
}
