package io.github.mianalysis.mia.gui.regions.menubar;

import io.github.mianalysis.mia.gui.GUIAnalysisHandler;
import io.github.mianalysis.mia.gui.GUI;
import io.github.mianalysis.mia.gui.UndoRedoStore;
import io.github.mianalysis.mia.MIA;
import io.github.mianalysis.mia.process.logging.LogRenderer;
import io.github.mianalysis.mia.process.logging.LogRenderer.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class CustomMenuBar extends JMenuBar implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 779793751255990466L;

    private static JMenu fileMenu = new JMenu("File");
    private static JMenu editMenu = new JMenu("Edit");
    private static JMenu analysisMenu = new JMenu("Analysis");
    private static JMenu viewMenu = new JMenu("View");
    private static JMenu helpMenu = new JMenu("Help");
    private static JMenu logMenu = new JMenu("Logging");

    private static MenuItem newPipeline = new MenuItem(MenuItem.NEW_PIPELINE);
    private static MenuItem loadPipeline = new MenuItem(MenuItem.LOAD_PIPELINE);
    private static MenuItem savePipeline = new MenuItem(MenuItem.SAVE_PIPELINE);
    private static MenuItem savePipelineAs = new MenuItem(MenuItem.SAVE_PIPELINE_AS);

    private static MenuItem resetAnalysis = new MenuItem(MenuItem.RESET_ANALYSIS);
    private static MenuItem enableAllModules = new MenuItem(MenuItem.ENABLE_ALL);
    private static MenuItem disableAllModules = new MenuItem(MenuItem.DISABLE_ALL);
    private static MenuItem outputAllModules = new MenuItem(MenuItem.OUTPUT_ALL);
    private static MenuItem silenceAllModules = new MenuItem(MenuItem.SILENCE_ALL);

    private static MenuItem undo = new MenuItem(MenuItem.UNDO);
    private static MenuItem redo = new MenuItem(MenuItem.REDO);
    private static MenuCheckbox helpCheckbox = new MenuCheckbox(MenuCheckbox.TOGGLE_HELP);
    private static MenuCheckbox notesCheckbox = new MenuCheckbox(MenuCheckbox.TOGGLE_NOTES);
    private static MenuCheckbox fileListCheckbox = new MenuCheckbox(MenuCheckbox.TOGGLE_FILE_LIST);
    private static MenuCheckbox searchCheckbox = new MenuCheckbox(MenuCheckbox.TOGGLE_SEARCH);

    public CustomMenuBar() {
        // Creating the file menu
        fileMenu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        add(fileMenu);
        fileMenu.add(newPipeline);
        fileMenu.add(loadPipeline);
        fileMenu.add(savePipeline);
        fileMenu.add(savePipelineAs);

        // Creating the edit menu
        editMenu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        add(editMenu);
        editMenu.add(undo);
        editMenu.add(redo);
        editMenu.addSeparator();
        editMenu.add(new MenuItem(MenuItem.COPY));
        editMenu.add(new MenuItem(MenuItem.PASTE));
        editMenu.addSeparator();
        editMenu.add(new MenuItem(MenuItem.PREFERENCES));

        // Creating the analysis menu
        analysisMenu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        add(analysisMenu);
        analysisMenu.add(new MenuItem(MenuItem.RUN_ANALYSIS));
        analysisMenu.add(new MenuItem(MenuItem.STOP_ANALYSIS));
        analysisMenu.add(resetAnalysis);
        analysisMenu.addSeparator();
        analysisMenu.add(enableAllModules);
        analysisMenu.add(disableAllModules);
        analysisMenu.add(outputAllModules);
        analysisMenu.add(silenceAllModules);

        // Creating the new menu
        viewMenu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        add(viewMenu);
        if (MIA.isDebug())
            viewMenu.add(new MenuItem(MenuItem.BASIC_VIEW));
        else
            viewMenu.add(new MenuItem(MenuItem.EDITING_VIEW));

        helpCheckbox.setSelected(GUI.showHelp());
        viewMenu.add(helpCheckbox);
        notesCheckbox.setSelected(GUI.showNotes());
        viewMenu.add(notesCheckbox);
        fileListCheckbox.setSelected(GUI.showFileList());
        viewMenu.add(fileListCheckbox);
        searchCheckbox.setSelected(GUI.showSearch());
        viewMenu.add(searchCheckbox);

        // Creating the help menu
        add(helpMenu);
        helpMenu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        helpMenu.add(new MenuItem(MenuItem.SHOW_ABOUT));
        helpMenu.add(new MenuItem(MenuItem.SHOW_GETTING_STARTED));

        helpMenu.add(logMenu);
        logMenu.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        LogRenderer renderer = MIA.getMainRenderer();

        Level level = Level.DEBUG;
        MenuLogCheckbox menuLogCheckbox = new MenuLogCheckbox(level, renderer.isWriteEnabled(level));
        logMenu.add(menuLogCheckbox);

        level = Level.MEMORY;
        menuLogCheckbox = new MenuLogCheckbox(level, renderer.isWriteEnabled(level));
        logMenu.add(menuLogCheckbox);

        level = Level.MESSAGE;
        menuLogCheckbox = new MenuLogCheckbox(level, renderer.isWriteEnabled(level));
        logMenu.add(menuLogCheckbox);

        level = Level.STATUS;
        menuLogCheckbox = new MenuLogCheckbox(level, renderer.isWriteEnabled(level));
        logMenu.add(menuLogCheckbox);

        level = Level.WARNING;
        menuLogCheckbox = new MenuLogCheckbox(level, renderer.isWriteEnabled(level));
        logMenu.add(menuLogCheckbox);

        add(Box.createHorizontalGlue());
        JMenu menu = new JMenu("");
        add(menu);
        menu.add(new MenuItem(MenuItem.SHOW_PONY));

        KeyStroke saveAnalysis = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(this, "Save", saveAnalysis, JComponent.WHEN_IN_FOCUSED_WINDOW);

        KeyStroke newAnalysis = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(this, "New", newAnalysis, JComponent.WHEN_IN_FOCUSED_WINDOW);

        KeyStroke undoAction = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(this, "Undo", undoAction, JComponent.WHEN_IN_FOCUSED_WINDOW);

        KeyStroke redoAction = KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(this, "Redo", redoAction, JComponent.WHEN_IN_FOCUSED_WINDOW);

    }

    public void update() {
        newPipeline.setVisible(!GUI.isBasicGUI());

        editMenu.setVisible(!GUI.isBasicGUI());

        resetAnalysis.setVisible(!GUI.isBasicGUI());
        enableAllModules.setVisible(!GUI.isBasicGUI());
        disableAllModules.setVisible(!GUI.isBasicGUI());
        outputAllModules.setVisible(!GUI.isBasicGUI());
        silenceAllModules.setVisible(!GUI.isBasicGUI());

        searchCheckbox.setVisible(!GUI.isBasicGUI());
    }

    public void setHelpSelected(Boolean showHelp) {
        helpCheckbox.setSelected(showHelp);

    }

    public void setFileListSelected(boolean showFileList) {
        fileListCheckbox.setSelected(showFileList);
    }

    public void setNotesSelected(Boolean showNotes) {
        notesCheckbox.setSelected(showNotes);

    }

    public void setSearchSelected(Boolean showSearch) {
        searchCheckbox.setSelected(showSearch);

    }

    public void setUndoRedoStatus(UndoRedoStore undoRedoStatus) {
        undo.setEnabled(undoRedoStatus.getUndoSize() != 0);
        redo.setEnabled(undoRedoStatus.getRedoSize() != 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "New":
                GUIAnalysisHandler.newAnalysis();
                break;
            case "Save":
                GUIAnalysisHandler.saveAnalysis();
                break;
            case "Undo":
                GUI.undo();
                break;
            case "Redo":
                GUI.redo();
                break;
        }
    }
}
