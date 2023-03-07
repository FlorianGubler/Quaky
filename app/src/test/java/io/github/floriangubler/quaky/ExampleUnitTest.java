package io.github.floriangubler.quaky;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import android.content.Context;

import java.io.IOException;

import io.github.floriangubler.quaky.entity.Earthquake;
import io.github.floriangubler.quaky.service.LastEarthquakeService;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    Context context;
    @Before
    public void start(){
        context = mock(Context.class);
    }


    @Test
    public void addition_isCorrect() throws IOException {
        //Doesnt work must mock context for service
        LastEarthquakeService fileService = new LastEarthquakeService();
        fileService.setLastEarthquake(new Earthquake());
        assertEquals(fileService.getLastEarthquake(), new Earthquake());
    }
}