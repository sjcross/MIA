package wbif.sjx.ModularImageAnalysis.Module.ImageProcessing.Pixel;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import org.junit.Ignore;
import org.junit.Test;
import wbif.sjx.ModularImageAnalysis.Object.Image;
import wbif.sjx.ModularImageAnalysis.Object.Workspace;

import java.net.URLDecoder;

import static org.junit.Assert.*;

public class NormaliseIntensityTest {
    private float tolerance = 1E-6f;

    @Test
    public void testGetTitle() {
        assertNotNull(new NormaliseIntensity().getTitle());
    }

    @Test
    public void testNormaliseIntensity8bit2D() throws Exception {
        // Creating a new workspace
        Workspace workspace = new Workspace(0,null,1);

        // Setting calibration parameters
        double dppXY = 0.02;
        String calibratedUnits = "µm";

        // Loading the test image and adding to workspace
        String pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradient2D_8bit.tif").getPath(),"UTF-8");
        ImagePlus ipl = IJ.openImage(pathToImage);
        Image image = new Image("Test_image",ipl);
        workspace.addImage(image);

        pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradientNormalised2D_8bit.tif").getPath(),"UTF-8");
        Image expectedImage = new Image("Expected", IJ.openImage(pathToImage));

        // Initialising BinaryOperations
        NormaliseIntensity normaliseIntensity = new NormaliseIntensity();
        normaliseIntensity.updateParameterValue(NormaliseIntensity.INPUT_IMAGE,"Test_image");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.APPLY_TO_INPUT,false);
        normaliseIntensity.updateParameterValue(NormaliseIntensity.OUTPUT_IMAGE,"Test_output");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.SHOW_IMAGE,false);

        // Running NormaliseIntensity
        normaliseIntensity.run(workspace);

        // Checking the images in the workspace
        assertEquals(2,workspace.getImages().size());
        assertNotNull(workspace.getImage("Test_image"));
        assertNotNull(workspace.getImage("Test_output"));

        // Checking the output image has the expected calibration
        Image outputImage = workspace.getImage("Test_output");
        assertTrue(outputImage.equals(expectedImage));

    }

    @Test
    public void testNormaliseIntensity8bit3D() throws Exception {
        // Creating a new workspace
        Workspace workspace = new Workspace(0,null,1);

        // Setting calibration parameters
        double dppXY = 0.02;
        String calibratedUnits = "µm";

        // Loading the test image and adding to workspace
        String pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradient3D_8bit.tif").getPath(),"UTF-8");
        ImagePlus ipl = IJ.openImage(pathToImage);
        Image image = new Image("Test_image",ipl);
        workspace.addImage(image);

        pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradientNormalised3D_8bit.tif").getPath(),"UTF-8");
        Image expectedImage = new Image("Expected", IJ.openImage(pathToImage));

        // Initialising BinaryOperations
        NormaliseIntensity normaliseIntensity = new NormaliseIntensity();
        normaliseIntensity.updateParameterValue(NormaliseIntensity.INPUT_IMAGE,"Test_image");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.APPLY_TO_INPUT,false);
        normaliseIntensity.updateParameterValue(NormaliseIntensity.OUTPUT_IMAGE,"Test_output");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.SHOW_IMAGE,false);

        // Running NormaliseIntensity
        normaliseIntensity.run(workspace);

        // Checking the images in the workspace
        assertEquals(2,workspace.getImages().size());
        assertNotNull(workspace.getImage("Test_image"));
        assertNotNull(workspace.getImage("Test_output"));

        // Checking the output image has the expected calibration
        Image outputImage = workspace.getImage("Test_output");
        assertTrue(outputImage.equals(expectedImage));

    }

    @Test
    public void testNormaliseIntensity8bit4D() throws Exception {
        // Creating a new workspace
        Workspace workspace = new Workspace(0,null,1);

        // Setting calibration parameters
        double dppXY = 0.02;
        String calibratedUnits = "µm";

        // Loading the test image and adding to workspace
        String pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradient5D_8bit_C1.tif").getPath(),"UTF-8");
        ImagePlus ipl = IJ.openImage(pathToImage);
        Image image = new Image("Test_image",ipl);
        workspace.addImage(image);

        pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradientNormalised5D_8bit_C1.tif").getPath(),"UTF-8");
        Image expectedImage = new Image("Expected", IJ.openImage(pathToImage));

        // Initialising BinaryOperations
        NormaliseIntensity normaliseIntensity = new NormaliseIntensity();
        normaliseIntensity.updateParameterValue(NormaliseIntensity.INPUT_IMAGE,"Test_image");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.APPLY_TO_INPUT,false);
        normaliseIntensity.updateParameterValue(NormaliseIntensity.OUTPUT_IMAGE,"Test_output");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.SHOW_IMAGE,false);

        // Running NormaliseIntensity
        normaliseIntensity.run(workspace);

        // Checking the images in the workspace
        assertEquals(2,workspace.getImages().size());
        assertNotNull(workspace.getImage("Test_image"));
        assertNotNull(workspace.getImage("Test_output"));

        // Checking the output image has the expected calibration
        Image outputImage = workspace.getImage("Test_output");
        assertTrue(outputImage.equals(expectedImage));

    }

    @Test
    public void testNormaliseIntensity8bit5D() throws Exception {
        // Creating a new workspace
        Workspace workspace = new Workspace(0,null,1);

        // Setting calibration parameters
        double dppXY = 0.02;
        String calibratedUnits = "µm";

        // Loading the test image and adding to workspace
        String pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradient5D_8bit.tif").getPath(),"UTF-8");
        ImagePlus ipl = IJ.openImage(pathToImage);
        Image image = new Image("Test_image",ipl);
        workspace.addImage(image);

        pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradientNormalised5D_8bit.tif").getPath(),"UTF-8");
        Image expectedImage = new Image("Expected", IJ.openImage(pathToImage));

        // Initialising BinaryOperations
        NormaliseIntensity normaliseIntensity = new NormaliseIntensity();
        normaliseIntensity.updateParameterValue(NormaliseIntensity.INPUT_IMAGE,"Test_image");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.APPLY_TO_INPUT,false);
        normaliseIntensity.updateParameterValue(NormaliseIntensity.OUTPUT_IMAGE,"Test_output");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.SHOW_IMAGE,false);

        // Running NormaliseIntensity
        normaliseIntensity.run(workspace);

        // Checking the images in the workspace
        assertEquals(2,workspace.getImages().size());
        assertNotNull(workspace.getImage("Test_image"));
        assertNotNull(workspace.getImage("Test_output"));

        // Checking the output image has the expected calibration
        Image outputImage = workspace.getImage("Test_output");
        assertTrue(outputImage.equals(expectedImage));

    }

    @Test
    public void testNormaliseIntensity16bit3D() throws Exception {
        // Creating a new workspace
        Workspace workspace = new Workspace(0,null,1);

        // Setting calibration parameters
        double dppXY = 0.02;
        String calibratedUnits = "µm";

        // Loading the test image and adding to workspace
        String pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradient3D_16bit.tif").getPath(),"UTF-8");
        ImagePlus ipl = IJ.openImage(pathToImage);
        Image image = new Image("Test_image",ipl);
        workspace.addImage(image);

        pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradientNormalised3D_16bit.tif").getPath(),"UTF-8");
        Image expectedImage = new Image("Expected", IJ.openImage(pathToImage));

        // Initialising BinaryOperations
        NormaliseIntensity normaliseIntensity = new NormaliseIntensity();
        normaliseIntensity.updateParameterValue(NormaliseIntensity.INPUT_IMAGE,"Test_image");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.APPLY_TO_INPUT,false);
        normaliseIntensity.updateParameterValue(NormaliseIntensity.OUTPUT_IMAGE,"Test_output");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.SHOW_IMAGE,false);

        // Running NormaliseIntensity
        normaliseIntensity.run(workspace);

        // Checking the images in the workspace
        assertEquals(2,workspace.getImages().size());
        assertNotNull(workspace.getImage("Test_image"));
        assertNotNull(workspace.getImage("Test_output"));

        // Checking the output image has the expected calibration
        Image outputImage = workspace.getImage("Test_output");
        assertTrue(outputImage.equals(expectedImage));

    }

    @Test
    public void testNormaliseIntensity32bitOverOne3D() throws Exception {
        // Creating a new workspace
        Workspace workspace = new Workspace(0,null,1);

        // Setting calibration parameters
        double dppXY = 0.02;
        String calibratedUnits = "µm";

        // Loading the test image and adding to workspace
        String pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/LightNoisyGradient3D_32bit.tif").getPath(),"UTF-8");
        ImagePlus ipl = IJ.openImage(pathToImage);
        Image image = new Image("Test_image",ipl);
        workspace.addImage(image);

        pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/LightNoisyGradientNormalised3D_32bit.tif").getPath(),"UTF-8");
        Image expectedImage = new Image("Expected", IJ.openImage(pathToImage));

        // Initialising BinaryOperations
        NormaliseIntensity normaliseIntensity = new NormaliseIntensity();
        normaliseIntensity.updateParameterValue(NormaliseIntensity.INPUT_IMAGE,"Test_image");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.APPLY_TO_INPUT,false);
        normaliseIntensity.updateParameterValue(NormaliseIntensity.OUTPUT_IMAGE,"Test_output");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.SHOW_IMAGE,false);

        // Running NormaliseIntensity
        normaliseIntensity.run(workspace);

        // Checking the images in the workspace
        assertEquals(2,workspace.getImages().size());
        assertNotNull(workspace.getImage("Test_image"));
        assertNotNull(workspace.getImage("Test_output"));

        // Checking the output image has the expected calibration
        Image outputImage = workspace.getImage("Test_output");
        assertTrue(outputImage.equals(expectedImage));

    }

    @Test
    public void testNormaliseIntensity32bitUnderOne3D() throws Exception {
        // Creating a new workspace
        Workspace workspace = new Workspace(0,null,1);

        // Setting calibration parameters
        double dppXY = 0.02;
        String calibratedUnits = "µm";

        // Loading the test image and adding to workspace
        String pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradient3D_32bit.tif").getPath(),"UTF-8");
        ImagePlus ipl = IJ.openImage(pathToImage);
        Image image = new Image("Test_image",ipl);
        workspace.addImage(image);

        pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradientNormalised3D_32bit.tif").getPath(),"UTF-8");
        Image expectedImage = new Image("Expected", IJ.openImage(pathToImage));

        // Initialising BinaryOperations
        NormaliseIntensity normaliseIntensity = new NormaliseIntensity();
        normaliseIntensity.updateParameterValue(NormaliseIntensity.INPUT_IMAGE,"Test_image");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.APPLY_TO_INPUT,false);
        normaliseIntensity.updateParameterValue(NormaliseIntensity.OUTPUT_IMAGE,"Test_output");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.SHOW_IMAGE,false);

        // Running NormaliseIntensity
        normaliseIntensity.run(workspace);

        // Checking the images in the workspace
        assertEquals(2,workspace.getImages().size());
        assertNotNull(workspace.getImage("Test_image"));
        assertNotNull(workspace.getImage("Test_output"));

        // Checking the output image has the expected calibration
        Image outputImage = workspace.getImage("Test_output");
        assertTrue(outputImage.equals(expectedImage));

    }

    @Test
    public void testRunDoApply8bit3D() throws Exception {
        // Creating a new workspace
        Workspace workspace = new Workspace(0,null,1);

        // Setting calibration parameters
        double dppXY = 0.02;
        String calibratedUnits = "µm";

        // Loading the test image and adding to workspace
        String pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradient3D_8bit.tif").getPath(),"UTF-8");
        ImagePlus ipl = IJ.openImage(pathToImage);
        Image image = new Image("Test_image",ipl);
        workspace.addImage(image);

        pathToImage = URLDecoder.decode(this.getClass().getResource("/images/NormaliseIntensity/DarkNoisyGradientNormalised3D_8bit.tif").getPath(),"UTF-8");
        Image expectedImage = new Image("Expected", IJ.openImage(pathToImage));

        // Initialising BinaryOperations
        NormaliseIntensity normaliseIntensity = new NormaliseIntensity();
        normaliseIntensity.updateParameterValue(NormaliseIntensity.INPUT_IMAGE,"Test_image");
        normaliseIntensity.updateParameterValue(NormaliseIntensity.APPLY_TO_INPUT,true);
        normaliseIntensity.updateParameterValue(NormaliseIntensity.SHOW_IMAGE,false);

        // Running NormaliseIntensity
        normaliseIntensity.run(workspace);

        // Checking the images in the workspace
        assertEquals(1,workspace.getImages().size());
        assertNotNull(workspace.getImage("Test_image"));

        // Checking the output image has the expected calibration
        Image outputImage = workspace.getImage("Test_image");
        assertTrue(outputImage.equals(expectedImage));

    }
}