package de.oppermann.maven.pflist.replacer;

import de.oppermann.maven.pflist.defect.Defect;
import de.oppermann.maven.pflist.property.PropertyContainer;
import de.oppermann.maven.pflist.utils.Utils;
import org.apache.tools.ant.types.FilterSet;
import org.apache.tools.ant.types.FilterSetCollection;
import org.apache.tools.ant.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

/**
 * User: sop
 * Date: 21.05.11
 * Time: 10:45
 */
public class PropertyFileReplacer {

    public static final String BEGIN_TOKEN = "%{";
    public static final String END_TOKEN = "}";

    protected PropertyContainer propertyContainer;

    public PropertyFileReplacer(PropertyContainer propertyContainer) {
        this.propertyContainer = propertyContainer;
    }

    public List<Defect> replace(File file) {
        List<Defect> defects = new ArrayList<Defect>();

        File tmpFile = new File(file.getParentFile(), file.getName() + "_tmp");

        try {
            FileUtils.getFileUtils().copyFile(file, tmpFile, getFilterSetCollection(propertyContainer), true, true);
            if (!file.delete())
                throw new RuntimeException("Couldn't delete file [" + file.getPath() + "]... Aborting!");
            if (!tmpFile.renameTo(file))
                throw new RuntimeException("Couldn't rename filtered file from [" + tmpFile.getPath() + "] to [" + file.getPath() + "]... Aborting!");
            defects.addAll(Utils.checkFileForNotReplacedStuff(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return defects;
    }

    private FilterSetCollection getFilterSetCollection(PropertyContainer propertyContainer) {
        FilterSet filterSet = getFilterSet(propertyContainer);

        FilterSetCollection executionFilters = new FilterSetCollection();
        executionFilters.addFilterSet(filterSet);

        return executionFilters;
    }

    private FilterSet getFilterSet(PropertyContainer propertyContainer) {
        FilterSet filterSet = new FilterSet();

        filterSet.setBeginToken(BEGIN_TOKEN);
        filterSet.setEndToken(END_TOKEN);

        for (Enumeration e = propertyContainer.getProperties().propertyNames(); e.hasMoreElements();) {
            String propertyId = (String) e.nextElement();
            String propertyValue = propertyContainer.getPropertyValue(propertyId);

            filterSet.addFilter(propertyId, propertyValue);
        }

        return filterSet;
    }

    public static Pattern getPattern(String match, boolean quoteIt) {
        String searchPattern = Pattern.quote(PropertyFileReplacer.BEGIN_TOKEN) + (quoteIt ? Pattern.quote(match) : match) + Pattern.quote(PropertyFileReplacer.END_TOKEN);

        return Pattern.compile(searchPattern);
    }
}
