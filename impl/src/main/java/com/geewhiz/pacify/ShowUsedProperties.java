package com.geewhiz.pacify;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.geewhiz.pacify.managers.EntityManager;
import com.geewhiz.pacify.model.PMarker;
import com.geewhiz.pacify.model.PProperty;
import com.geewhiz.pacify.utils.Utils;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

public class ShowUsedProperties {

    public enum OutputType {
        Stdout, File
    }

    private Logger     logger = LogManager.getLogger(ShowUsedProperties.class.getName());

    private File       packagePath;
    private File       targetFile;
    private String     targetEncoding;
    private OutputType outputType;
    private String     outputPrefix;

    public void execute() {
        EntityManager entityManager = new EntityManager(getPackagePath());

        logger.info("== Executing ShowUsedProperties [Version={}]", Utils.getJarVersion());
        logger.info("== Found [{}] pacify marker files", entityManager.getPMarkerCount());

        if (getOutputType() == OutputType.Stdout) {
            logger.info("== Getting Properties...");
            writeToStdout(entityManager);
        } else if (getOutputType() == OutputType.File) {
            logger.info("   [TargetFile={}]", getTargetFile().getPath());
            logger.info("== Getting Properties...");
            writeToFile(entityManager);
        } else {
            throw new IllegalArgumentException("OutputType not implemented! [" + getOutputType() + "]");
        }

        logger.info("== Successfully finished");
    }

    private void writeToFile(EntityManager entityManager) {
        if (getTargetFile().exists()) {
            throw new IllegalArgumentException("File [" + getTargetFile().getAbsolutePath() + "] allready exists.");
        }

        if (getTargetFile().getParentFile() != null) {
            getTargetFile().getParentFile().mkdirs();
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(targetFile, getOutputEncoding());
            for (String property : getAllProperties(entityManager)) {
                writer.println(getOutputPrefix() + property);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private void writeToStdout(EntityManager entityManager) {
        for (String usedProperty : getAllProperties(entityManager)) {
            System.out.println(getOutputPrefix() + usedProperty);
        }
    }

    private Set<String> getAllProperties(EntityManager entityManager) {
        Set<String> allUsedProperties = new TreeSet<String>();
        for (PMarker pMarker : entityManager.getPMarkers()) {
            logger.info("   [{}]", pMarker.getFile().getAbsolutePath());

            for (PProperty pProperty : pMarker.getProperties()) {
                allUsedProperties.add(pProperty.getName());
            }
        }
        return allUsedProperties;
    }

    public void setOutputEncoding(String targetEncoding) {
        this.targetEncoding = targetEncoding;
    }

    public String getOutputEncoding() {
        return targetEncoding;
    }

    public void setTargetFile(File targetFile) {
        this.targetFile = targetFile;
    }

    public File getTargetFile() {
        return targetFile;
    }

    private OutputType getOutputType() {
        return outputType;
    }

    public void setOutputType(OutputType outputType) {
        this.outputType = outputType;
    }

    public File getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(File packagePath) {
        this.packagePath = packagePath;
    }

    public void setOutputPrefix(String outputPrefix) {
        this.outputPrefix = outputPrefix;
    }

    public String getOutputPrefix() {
        return outputPrefix;
    }
}
