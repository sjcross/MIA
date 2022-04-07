package io.github.mianalysis.mia.module.images.transform;

import org.scijava.Priority;
import org.scijava.plugin.Plugin;

import io.github.mianalysis.mia.MIA;
import io.github.mianalysis.mia.module.Categories;
import io.github.mianalysis.mia.module.Category;
import io.github.mianalysis.mia.module.IL2Support;
import io.github.mianalysis.mia.module.Module;
import io.github.mianalysis.mia.module.Modules;
import io.github.mianalysis.mia.object.Workspace;
import io.github.mianalysis.mia.object.image.Image;
import io.github.mianalysis.mia.object.image.ImageFactory;
import io.github.mianalysis.mia.object.image.ImgPlusImage;
import io.github.mianalysis.mia.object.image.ImgPlusTools;
import io.github.mianalysis.mia.object.parameters.BooleanP;
import io.github.mianalysis.mia.object.parameters.ChoiceP;
import io.github.mianalysis.mia.object.parameters.InputImageP;
import io.github.mianalysis.mia.object.parameters.OutputImageP;
import io.github.mianalysis.mia.object.parameters.Parameters;
import io.github.mianalysis.mia.object.parameters.SeparatorP;
import io.github.mianalysis.mia.object.refs.collections.ImageMeasurementRefs;
import io.github.mianalysis.mia.object.refs.collections.MetadataRefs;
import io.github.mianalysis.mia.object.refs.collections.ObjMeasurementRefs;
import io.github.mianalysis.mia.object.refs.collections.ParentChildRefs;
import io.github.mianalysis.mia.object.refs.collections.PartnerRefs;
import io.github.mianalysis.mia.object.system.Preferences;
import io.github.mianalysis.mia.object.system.Status;
import net.imagej.ImgPlus;
import net.imagej.axis.Axes;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.cache.img.DiskCachedCellImgFactory;
import net.imglib2.cache.img.DiskCachedCellImgOptions;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.Views;

@Plugin(type = Module.class, priority=Priority.LOW, visible=true)
public class FlipStack <T extends RealType<T> & NativeType<T>> extends Module {
    public static final String INPUT_SEPARATOR = "Image input/output";
    public static final String INPUT_IMAGE = "Input image";
    public static final String APPLY_TO_INPUT = "Apply to input image";
    public static final String OUTPUT_IMAGE = "Output image";

    public static final String FLIP_SEPARATOR = "Stack flip controls";
    public static final String AXIS_MODE = "Axis mode";

    public FlipStack(Modules modules) {
        super("Flip stack", modules);
        il2Support = IL2Support.FULL;
    }


    public interface AxisModes {
        String X = "X";
        String Y = "Y";
        String Z = "Z";
        String CHANNEL = "Channel";
        String TIME = "Time";

        String[] ALL = new String[]{X,Y,Z,CHANNEL,TIME};

    }


    private int getAxesIndex(ImgPlus<T> img, String axis) {
        switch (axis) {
            case AxisModes.X:
            default:
                return img.dimensionIndex(Axes.X);
            case AxisModes.Y:
                return img.dimensionIndex(Axes.Y);
            case AxisModes.Z:
                return img.dimensionIndex(Axes.Z);
            case AxisModes.CHANNEL:
                return img.dimensionIndex(Axes.CHANNEL);
            case AxisModes.TIME:
                return img.dimensionIndex(Axes.TIME);
        }
    }

    public Image<T> applyFlip(Image<T> inputImage, String axis, String outputImageName) {
        ImgPlus<T> inputImg = inputImage.getImgPlus();

        // Creating the new Img       
        long[] dims = new long[inputImg.numDimensions()];
        for (int i = 0; i < inputImg.numDimensions(); i++)
            dims[i] = inputImg.dimension(i);
        
        T type = (T) inputImg.firstElement();
        DiskCachedCellImgOptions options = ImgPlusImage.getCellImgOptions();
        ImgPlus<T> outputImg = new ImgPlus<>(new DiskCachedCellImgFactory(type,options).create(dims));
        
        ImgPlusTools.copyAxes(inputImg,outputImg);

        // Determining the axis index
        int axisIndex = getAxesIndex(inputImg, axis);
        if (axisIndex == -1) {
            MIA.log.writeError("[FlipStack] Specified axis for image flipping doesn't exist.");
            return null;
        }

        long[] offsetIn = new long[inputImg.numDimensions()];
        long[] offsetOut = new long[outputImg.numDimensions()];
        offsetOut[axisIndex] = -dims[axisIndex] + 1;

        Cursor<T> targetCursor = Views.offsetInterval(Views.invertAxis(outputImg,axisIndex),offsetOut,dims).localizingCursor();
        RandomAccess<T> sourceRandomAccess = Views.offsetInterval(inputImg, offsetIn, dims).randomAccess();

        long count = 0;
        long total = inputImg.size();
        long interval = Math.floorDiv(total, 100);
        while (targetCursor.hasNext()) {
            targetCursor.fwd();
            sourceRandomAccess.setPosition(targetCursor);
            targetCursor.get().set(sourceRandomAccess.get());
            count++;

            if (count % interval == 0)
                writeStatus("Processed " + Math.floorDiv(100 * count, total) + "%");

        }
        
        // // For some reason the ImagePlus produced by ImageJFunctions.wrap() behaves strangely, but this can be remedied
        //     // by duplicating it
        // if (MIA.preferences.getDataStorageMode().equals(Preferences.DataStorageModes.KEEP_IN_RAM)) {            
        //     ImagePlus outputImagePlus = ImageJFunctions.wrap(outputImg, outputImageName);
        //     outputImagePlus.setCalibration(inputImage.getImagePlus().getCalibration());
        //     ImgPlusTools.applyAxes(outputImg, outputImagePlus);
        // }
        
        return ImageFactory.createImage(outputImageName,outputImg);

    }


    @Override
    public Category getCategory() {
        return Categories.IMAGES_TRANSFORM;
    }

    @Override
    public String getDescription() {
        return "Flips the order of slices in stack.  This operation can be performed on the channel, time or Z axis.";
    }

    @Override
    protected Status process(Workspace workspace) {
        // Getting input image
        String inputImageName = parameters.getValue(INPUT_IMAGE);
        Image inputImage = workspace.getImages().get(inputImageName);

        // Getting parameters
        boolean applyToInput = parameters.getValue(APPLY_TO_INPUT);
        String outputImageName = parameters.getValue(OUTPUT_IMAGE);
        String axisMode = parameters.getValue(AXIS_MODE);

        // If applying to a new image, the input image is duplicated
        if (applyToInput) outputImageName = inputImageName;

        // Applying flip
        Image outputImage = applyFlip(inputImage, axisMode, outputImageName);
        if (outputImage == null) return Status.FAIL;

        if (showOutput) outputImage.showImage();
        if (applyToInput) {
            inputImage.setImgPlus(outputImage.getImgPlus());
        } else {
            workspace.addImage(outputImage);
        }

        return Status.PASS;

    }

    @Override
    protected void initialiseParameters() {
        parameters.add(new SeparatorP(INPUT_SEPARATOR,this));
        parameters.add(new InputImageP(INPUT_IMAGE, this, "", "Image to process."));
        parameters.add(new BooleanP(APPLY_TO_INPUT, this,true, "If selected, the flipped image will replace the input image in the workspace.  All measurements associated with the input image will be transferred to the flipped image."));
        parameters.add(new OutputImageP(OUTPUT_IMAGE, this, "", "Name of the output flipped image."));

        parameters.add(new SeparatorP(FLIP_SEPARATOR,this));
        parameters.add(new ChoiceP(AXIS_MODE, this,AxisModes.X,AxisModes.ALL,"Axis along which to flip the image."));

    }

    @Override
    public Parameters updateAndGetParameters() {
        Parameters returnedParameters = new Parameters();

        returnedParameters.add(parameters.getParameter(INPUT_SEPARATOR));
        returnedParameters.add(parameters.getParameter(INPUT_IMAGE));
        returnedParameters.add(parameters.getParameter(APPLY_TO_INPUT));

        if (!(boolean) parameters.getValue(APPLY_TO_INPUT)) {
            returnedParameters.add(parameters.getParameter(OUTPUT_IMAGE));
        }

        returnedParameters.add(parameters.getParameter(FLIP_SEPARATOR));
        returnedParameters.add(parameters.getParameter(AXIS_MODE));

        return returnedParameters;

    }

    @Override
    public ImageMeasurementRefs updateAndGetImageMeasurementRefs() {
        return null;
    }

    @Override
    public ObjMeasurementRefs updateAndGetObjectMeasurementRefs() {
        return null;
    }

    @Override
    public MetadataRefs updateAndGetMetadataReferences() {
        return null;
    }

    @Override
    public ParentChildRefs updateAndGetParentChildRefs() {
        return null;
    }

    @Override
    public PartnerRefs updateAndGetPartnerRefs() {
        return null;
    }

    @Override
    public boolean verify() {
        String storageMode = MIA.preferences.getDataStorageMode();
        if (storageMode.equals(Preferences.DataStorageModes.STREAM_FROM_DRIVE) & il2Support.equals(IL2Support.NONE))
            return false;

        return true;
    }
}
