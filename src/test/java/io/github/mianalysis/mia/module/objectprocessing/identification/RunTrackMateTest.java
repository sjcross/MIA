package io.github.mianalysis.mia.module.objectprocessing.identification;

import io.github.mianalysis.mia.module.ModuleTest;

import static org.junit.jupiter.api.Assertions.*;


public class RunTrackMateTest extends ModuleTest {

    @Override
    public void testGetHelp() {
        assertNotNull(new RunTrackMate(null).getDescription());
    }
}