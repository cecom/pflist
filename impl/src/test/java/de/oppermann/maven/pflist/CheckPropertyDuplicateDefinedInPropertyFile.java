package de.oppermann.maven.pflist;

import de.oppermann.maven.pflist.checker.CheckPropertyDuplicateInPropertyFile;
import de.oppermann.maven.pflist.defect.Defect;
import de.oppermann.maven.pflist.property.PropertyFileProperties;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * User: sop
 * Date: 03.05.11
 * Time: 13:05
 */
public class CheckPropertyDuplicateDefinedInPropertyFile {
    @Test
    public void checkForNotCorrect() {
        File testStartPath = new File("target/test-classes/checkPropertyDuplicateDefinedInPropertyFile/wrong");
        File file = new File(testStartPath, "myProperties.properties");

        URL fileUrl = Util.getURLForFile(file);
        PropertyFileProperties propertyFileProperties = new PropertyFileProperties(fileUrl);

        CheckPropertyDuplicateInPropertyFile checker = new CheckPropertyDuplicateInPropertyFile(propertyFileProperties);

        List<Defect> defects = new ArrayList<Defect>();
        defects.addAll(checker.checkForErrors());

        Assert.assertEquals(2, defects.size());
    }

    @Test
    public void checkForCorrect() {
        File testStartPath = new File("target/test-classes/checkPropertyDuplicateDefinedInPropertyFile/correct");
        File file = new File(testStartPath, "myProperties.properties");

        URL fileUrl = Util.getURLForFile(file);
        PropertyFileProperties propertyFileProperties = new PropertyFileProperties(fileUrl);

        CheckPropertyDuplicateInPropertyFile checker = new CheckPropertyDuplicateInPropertyFile(propertyFileProperties);

        List<Defect> defects = new ArrayList<Defect>();
        defects.addAll(checker.checkForErrors());

        Assert.assertEquals(0, defects.size());
    }
}