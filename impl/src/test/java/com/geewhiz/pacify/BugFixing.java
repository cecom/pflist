/*-
 * ========================LICENSE_START=================================
 * com.geewhiz.pacify.impl
 * %%
 * Copyright (C) 2011 - 2017 Sven Oppermann
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

package com.geewhiz.pacify;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.geewhiz.pacify.defect.Defect;
import com.geewhiz.pacify.utils.LoggingUtils;

public class BugFixing extends TestBase {

    Map<String, String> propertiesToUseWhileResolving = new HashMap<String, String>();

    @Before
    public void before() {
        Logger logger = LogManager.getLogger();
        LoggingUtils.setLogLevel(logger, Level.ERROR);

        propertiesToUseWhileResolving.put("foobar", "foobarValue");
    }

    @Test
    public void Bug1() {
        String testFolder = "1_Bugfixing/Bug1";

        LinkedHashSet<Defect> defects = createPrepareAndExecutePacify(testFolder, propertiesToUseWhileResolving);

        Assert.assertEquals("We shouldnt get any defects.", 0, defects.size());
    }

    @Test
    public void Bug2() {
        String testFolder = "1_Bugfixing/Bug2";

        LinkedHashSet<Defect> defects = createPrepareAndExecutePacify(testFolder, propertiesToUseWhileResolving);

        Assert.assertEquals("We shouldnt get any defects.", 0, defects.size());
    }
}
