package org.smartregister.simprint;

import android.content.pm.PackageManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by ndegwamartin on 11/05/2021.
 */
public class SimPrintsUtilsTest extends BaseUnitTest {

    @Mock
    private PackageManager packageManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testIsPackageInstalledInvokesInfoForCorrectPackageName() throws PackageManager.NameNotFoundException {
        String testPackageName = "com.test.package.name";
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);

        SimPrintsUtils.isPackageInstalled(testPackageName, packageManager);

        Mockito.verify(packageManager).getPackageInfo(stringArgumentCaptor.capture(), ArgumentMatchers.eq(0));
        String capturedString = stringArgumentCaptor.getValue();
        Assert.assertEquals(testPackageName, capturedString);

    }

    @Test
    public void testIsPackageInstalledReturnsFalseIfNameNotFoundExceptionThrown() throws PackageManager.NameNotFoundException {
        String testPackageName = "com.test.package.name";

        Mockito.doThrow(new PackageManager.NameNotFoundException()).when(packageManager).getPackageInfo(ArgumentMatchers.anyString(), ArgumentMatchers.eq(0));

        boolean result = SimPrintsUtils.isPackageInstalled(testPackageName, packageManager);

        Assert.assertEquals(false, result);

    }

}