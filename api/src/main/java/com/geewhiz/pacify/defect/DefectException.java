package com.geewhiz.pacify.defect;

import com.geewhiz.pacify.model.PArchive;
import com.geewhiz.pacify.model.PFile;
import com.geewhiz.pacify.model.PMarker;
import com.geewhiz.pacify.model.PProperty;

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

public abstract class DefectException extends Exception implements Defect {

    private static final long serialVersionUID = 1L;

    private PMarker           pMarker;
    private PArchive          pArchive;
    private PFile             pFile;
    private PProperty         pProperty;

    public DefectException() {
    }

    public DefectException(PMarker pMarker) {
        this(pMarker, null, null, null);
    }

    public DefectException(PMarker pMarker, PArchive pArchive) {
        this(pMarker, pArchive, null, null);
    }

    public DefectException(PMarker pMarker, PFile pFile) {
        this(pMarker, null, pFile, null);
    }

    public DefectException(PMarker pMarker, PArchive pArchive, PFile pFile) {
        this(pMarker, pArchive, pFile, null);
    }

    public DefectException(PMarker pMarker, PFile pFile, PProperty pProperty) {
        this(pMarker, null, pFile, pProperty);
    }

    public DefectException(PMarker pMarker, PArchive pArchive, PFile pFile, PProperty pProperty) {
        this.pMarker = pMarker;
        this.pArchive = pArchive;
        this.pFile = pFile;
        this.pProperty = pProperty;
    }

    public PMarker getPMarker() {
        return pMarker;
    }

    public PArchive getPArchive() {
        return pArchive;
    }

    public PFile getPFile() {
        return pFile;
    }

    public PProperty getPProperty() {
        return pProperty;
    }

    public String getDefectMessage() {
        StringBuffer result = new StringBuffer();
        result.append(this.getClass().getSimpleName()).append(":");

        if (pMarker != null) {
            result.append(String.format("\n\t[MarkerFile=%s]", pMarker.getFile().getAbsolutePath()));
        }
        if (pArchive != null) {
            result.append(String.format("\n\t[Archive=%s]", pMarker.getAbsoluteFileFor(pArchive)));
            if (pFile != null) {
                result.append(String.format("\n\t[Archive File=%s]", pFile.getRelativePath()));
            }
        } else {
            if (pFile != null) {
                result.append(String.format("\n\t[File=%s]", pMarker.getAbsoluteFileFor(pFile)));
            }
        }

        if (pProperty != null) {
            result.append(String.format("\n\t[Property=%s]", pProperty.getName()));
        }

        return result.toString();
    }

}
