/*
 * Copyright (C) 2019 - MFFBrokenSwing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mffbrokenswing.mcmodabstraction.impl.mod;

import com.github.mffbrokenswing.mcmodabstraction.api.mod.EDependencyConstraint;
import com.github.mffbrokenswing.mcmodabstraction.api.mod.IDependency;
import com.github.mffbrokenswing.mcmodabstraction.api.mod.IVersion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DependencyTest
{

    @Test
    public void ensureCorrectData()
    {
        String depId = "jei";
        IVersion minVersion = Mockito.mock(IVersion.class);
        IVersion maxVersion = Mockito.mock(IVersion.class);
        Set<EDependencyConstraint> constraints = new HashSet<>();

        IDependency dep = new Dependency(
                depId,
                minVersion,
                maxVersion,
                constraints
        );

        assertThat(dep.getId()).isEqualTo(depId);
        assertThat(dep.getMinimalVersion() == minVersion);
        assertThat(dep.getMaximalVersion() == maxVersion);
        assertThat(dep.getLoadingConstraints() == constraints);
    }

}
