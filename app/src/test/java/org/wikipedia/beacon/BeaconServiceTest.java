package org.wikipedia.beacon;

import android.content.Intent;

import org.altbeacon.beacon.Region;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;

public class BeaconServiceTest {

    private BeaconService beaconService = new BeaconService();
    private Intent intent = mock(Intent.class);
    private Region region = mock(Region.class);

    // positive result
    @Test
    public void onStartCommandTestTrueResult() {
        assertEquals(1, beaconService.onStartCommand(intent, 1, 1));
    }

    @Test
    public void onStartCommandTestFalseResult() {
        assertNotEquals(2, beaconService.onStartCommand(intent, 1, 1));
    }

    @Test
    public void didEnterRegionTest() {
        beaconService.didEnterRegion(region);

    }

}