package wbif.sjx.ModularImageAnalysis.GUI.InputOutput;

import wbif.sjx.ModularImageAnalysis.Exceptions.GenericMIAException;
import wbif.sjx.ModularImageAnalysis.Module.HCModule;
import wbif.sjx.ModularImageAnalysis.Object.*;

/**
 * Created by steph on 29/07/2017.
 */
public class InputControl extends HCModule {
    public static final String INPUT_MODE = "Input mode";
    public static final String SINGLE_FILE_PATH = "Single file path";
    public static final String BATCH_FOLDER_PATH = "Batch folder path";
    public static final String NUMBER_OF_THREADS = "Number of CPU threads";
    public static final String FILE_EXTENSION = "File extension";
    public static final String USE_FILENAME_FILTER_1 = "Use filename filter 1";
    public static final String USE_FILENAME_FILTER_2 = "Use filename filter 2";
    public static final String USE_FILENAME_FILTER_3 = "Use filename filter 3";
    public static final String FILENAME_FILTER_1 = "Filename filter 1";
    public static final String FILENAME_FILTER_2 = "Filename filter 2";
    public static final String FILENAME_FILTER_3 = "Filename filter 3";
    public static final String FILENAME_FILTER_TYPE_1 = "Filter type 1";
    public static final String FILENAME_FILTER_TYPE_2 = "Filter type 2";
    public static final String FILENAME_FILTER_TYPE_3 = "Filter type 3";

    public interface InputModes {
        String SINGLE_FILE = "Single file";
        String BATCH = "Batch";

        String[] ALL = new String[]{BATCH,SINGLE_FILE};

    }

    public interface FilterTypes {
        String INCLUDE_MATCHES_PARTIALLY = "Matches partially (include)";
        String INCLUDE_MATCHES_COMPLETELY = "Matches completely (include)";
        String EXCLUDE_MATCHES_PARTIALLY = "Matches partially (exclude)";
        String EXCLUDE_MATCHES_COMPLETELY = "Matches completely (exclude)";

        String[] ALL = new String[]{INCLUDE_MATCHES_PARTIALLY,INCLUDE_MATCHES_COMPLETELY,EXCLUDE_MATCHES_PARTIALLY,EXCLUDE_MATCHES_COMPLETELY};

    }


    @Override
    public String getTitle() {
        return "Input control";
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public void run(Workspace workspace, boolean verbose) throws GenericMIAException {

    }

    @Override
    public ParameterCollection initialiseParameters() {
        ParameterCollection returnedParameters = new ParameterCollection();

        returnedParameters.addParameter(new Parameter(INPUT_MODE, Parameter.CHOICE_ARRAY,InputModes.SINGLE_FILE,InputModes.ALL));
        returnedParameters.addParameter(new Parameter(SINGLE_FILE_PATH, Parameter.FILE_PATH,null));
        returnedParameters.addParameter(new Parameter(BATCH_FOLDER_PATH, Parameter.FOLDER_PATH,null));
        int nThreads = Runtime.getRuntime().availableProcessors()/2;
        returnedParameters.addParameter(new Parameter(NUMBER_OF_THREADS,Parameter.INTEGER,nThreads));
        returnedParameters.addParameter(new Parameter(FILE_EXTENSION, Parameter.STRING,"flex"));
        returnedParameters.addParameter(new Parameter(USE_FILENAME_FILTER_1,Parameter.BOOLEAN,false));
        returnedParameters.addParameter(new Parameter(FILENAME_FILTER_1,Parameter.STRING,""));
        returnedParameters.addParameter(new Parameter(FILENAME_FILTER_TYPE_1,Parameter.CHOICE_ARRAY,FilterTypes.INCLUDE_MATCHES_PARTIALLY,FilterTypes.ALL));
        returnedParameters.addParameter(new Parameter(USE_FILENAME_FILTER_2,Parameter.BOOLEAN,false));
        returnedParameters.addParameter(new Parameter(FILENAME_FILTER_2,Parameter.STRING,""));
        returnedParameters.addParameter(new Parameter(FILENAME_FILTER_TYPE_2,Parameter.CHOICE_ARRAY,FilterTypes.INCLUDE_MATCHES_PARTIALLY,FilterTypes.ALL));
        returnedParameters.addParameter(new Parameter(USE_FILENAME_FILTER_3,Parameter.BOOLEAN,false));
        returnedParameters.addParameter(new Parameter(FILENAME_FILTER_3,Parameter.STRING,""));
        returnedParameters.addParameter(new Parameter(FILENAME_FILTER_TYPE_3,Parameter.CHOICE_ARRAY,FilterTypes.INCLUDE_MATCHES_PARTIALLY,FilterTypes.ALL));

        return returnedParameters;

    }

    @Override
    public ParameterCollection updateAndGetParameters() {
        ParameterCollection returnedParameters = new ParameterCollection();

        returnedParameters.addParameter(parameters.getParameter(INPUT_MODE));

        switch ((String) parameters.getValue(INPUT_MODE)) {
            case InputModes.SINGLE_FILE:
                returnedParameters.addParameter(parameters.getParameter(SINGLE_FILE_PATH));
                break;

            case InputModes.BATCH:
                returnedParameters.addParameter(parameters.getParameter(BATCH_FOLDER_PATH));
                returnedParameters.addParameter(parameters.getParameter(NUMBER_OF_THREADS));
                break;

        }

        returnedParameters.addParameter(parameters.getParameter(FILE_EXTENSION));

        returnedParameters.addParameter(parameters.getParameter(USE_FILENAME_FILTER_1));
        if (returnedParameters.getValue(USE_FILENAME_FILTER_1)) {
            returnedParameters.addParameter(parameters.getParameter(FILENAME_FILTER_1));
            returnedParameters.addParameter(parameters.getParameter(FILENAME_FILTER_TYPE_1));
        }

        returnedParameters.addParameter(parameters.getParameter(USE_FILENAME_FILTER_2));
        if (returnedParameters.getValue(USE_FILENAME_FILTER_2)) {
            returnedParameters.addParameter(parameters.getParameter(FILENAME_FILTER_2));
            returnedParameters.addParameter(parameters.getParameter(FILENAME_FILTER_TYPE_2));
        }

        returnedParameters.addParameter(parameters.getParameter(USE_FILENAME_FILTER_3));
        if (returnedParameters.getValue(USE_FILENAME_FILTER_3)) {
            returnedParameters.addParameter(parameters.getParameter(FILENAME_FILTER_3));
            returnedParameters.addParameter(parameters.getParameter(FILENAME_FILTER_TYPE_3));
        }

        return returnedParameters;

    }

    @Override
    protected MeasurementReferenceCollection initialiseImageMeasurementReferences() {
        return null;
    }

    @Override
    public MeasurementReferenceCollection updateAndGetImageMeasurementReferences() {
        return null;
    }

    @Override
    protected MeasurementReferenceCollection initialiseObjectMeasurementReferences() {
        return null;
    }

    @Override
    public MeasurementReferenceCollection updateAndGetObjectMeasurementReferences() {
        return null;
    }

    @Override
    public void addRelationships(RelationshipCollection relationships) {

    }
}
