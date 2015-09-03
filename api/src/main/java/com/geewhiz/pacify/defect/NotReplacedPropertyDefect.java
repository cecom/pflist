package com.geewhiz.pacify.defect;

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

import com.geewhiz.pacify.model.PArchive;
import com.geewhiz.pacify.model.PFile;
import com.geewhiz.pacify.model.PMarker;

public class NotReplacedPropertyDefect extends DefectException {

    private static final long serialVersionUID = 1L;

    private String            propertyId;

    public NotReplacedPropertyDefect(PMarker pMarker, PFile pFile, String propertyId) {
        super(pMarker, pFile);
        this.propertyId = propertyId;
    }

    public NotReplacedPropertyDefect(PMarker pMarker, PArchive pArchive, PFile pFile, String propertyId) {
        super(pMarker, pArchive, pFile);
        this.propertyId = propertyId;
    }

    @Override
    public String getDefectMessage() {
        return super.getDefectMessage() +
                String.format("\n\t[Property=%s]", getPropertyId());
    }

    public String getPropertyId() {
        return propertyId;
    }

}