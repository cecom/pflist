package com.geewhiz.pacify;

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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.geewhiz.pacify.checks.impl.CheckPropertyDuplicateInPropertyFile;
import com.geewhiz.pacify.defect.Defect;
import com.geewhiz.pacify.managers.PropertyResolveManager;
import com.geewhiz.pacify.property.resolver.fileresolver.FilePropertyResolver;
import com.geewhiz.pacify.resolver.PropertyResolver;
import com.geewhiz.pacify.test.TestUtil;

public class TestCheckPropertyDuplicateDefinedInPropertyFile {

    @Test
    public void checkForNotCorrect() {
        File testStartPath = new File("target/test-classes/checkPropertyDuplicateDefinedInPropertyFile/wrong");
        File file = new File(testStartPath, "myProperties.properties");

        URL fileUrl = TestUtil.getURLForFile(file);
        FilePropertyResolver filePropertyResolver = new FilePropertyResolver(fileUrl);

        Set<PropertyResolver> resolverList = new TreeSet<PropertyResolver>();
        resolverList.add(filePropertyResolver);

        PropertyResolveManager propertyResolveManager = new PropertyResolveManager(resolverList);

        CheckPropertyDuplicateInPropertyFile checker = new CheckPropertyDuplicateInPropertyFile(propertyResolveManager);

        List<Defect> defects = new ArrayList<Defect>();
        defects.addAll(checker.checkForErrors());

        Assert.assertEquals(2, defects.size());
    }

    @Test
    public void checkForCorrect() {
        File testStartPath = new File("target/test-classes/checkPropertyDuplicateDefinedInPropertyFile/correct");
        File file = new File(testStartPath, "myProperties.properties");

        URL fileUrl = TestUtil.getURLForFile(file);
        FilePropertyResolver filePropertyResolver = new FilePropertyResolver(fileUrl);

        Set<PropertyResolver> resolverList = new TreeSet<PropertyResolver>();
        resolverList.add(filePropertyResolver);

        PropertyResolveManager propertyResolveManager = new PropertyResolveManager(resolverList);

        CheckPropertyDuplicateInPropertyFile checker = new CheckPropertyDuplicateInPropertyFile(propertyResolveManager);

        List<Defect> defects = new ArrayList<Defect>();
        defects.addAll(checker.checkForErrors());

        Assert.assertEquals(0, defects.size());
    }
}
