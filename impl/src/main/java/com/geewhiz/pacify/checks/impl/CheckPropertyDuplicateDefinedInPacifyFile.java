package com.geewhiz.pacify.checks.impl;

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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.geewhiz.pacify.checks.PMarkerCheck;
import com.geewhiz.pacify.defect.Defect;
import com.geewhiz.pacify.defect.PropertyDuplicateDefinedInPMarkerDefect;
import com.geewhiz.pacify.managers.EntityManager;
import com.geewhiz.pacify.model.PArchive;
import com.geewhiz.pacify.model.PFile;
import com.geewhiz.pacify.model.PMarker;
import com.geewhiz.pacify.model.PProperty;

public class CheckPropertyDuplicateDefinedInPacifyFile implements PMarkerCheck {

    public LinkedHashSet<Defect> checkForErrors(EntityManager entityManager, PMarker pMarker) {
        LinkedHashSet<Defect> defects = new LinkedHashSet<Defect>();

        for (PArchive pArchive : entityManager.getPArchivesFrom(pMarker)) {
            checkPFiles(defects, pArchive.getPFiles());
        }

        checkPFiles(defects, entityManager.getPFilesFrom(pMarker));
        return defects;
    }

    private void checkPFiles(LinkedHashSet<Defect> defects, List<PFile> pFiles) {
        for (PFile pFile : pFiles) {
            List<String> properties = new ArrayList<String>();
            for (PProperty pProperty : pFile.getPProperties()) {
                if (properties.contains(pProperty.getName())) {
                    Defect defect = new PropertyDuplicateDefinedInPMarkerDefect(pFile, pProperty);
                    defects.add(defect);
                    continue;
                }
                properties.add(pProperty.getName());
            }
        }
    }
}
