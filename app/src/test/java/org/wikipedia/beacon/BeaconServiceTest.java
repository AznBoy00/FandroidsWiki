package org.wikipedia.beacon;

import android.content.Intent;

import org.altbeacon.beacon.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BeaconServiceTest {

    @Mock
    Intent intent;
    Region region;

    @Test
    public void didEnterRegionTest(){
        Region region = mock(Region.class);

        BeaconService beaconService = new BeaconService();

        beaconService.didEnterRegion(region);
        verify(beaconService).showNotification("Found Beacon in the range","For more info go the app");
    }

    @Test
    public void startBroadcastingTest() {
        BeaconService beaconService = new BeaconService();

        beaconService.startBroadcasting(intent);

        verify(beaconService).sendBroadcast(intent);
    }
}