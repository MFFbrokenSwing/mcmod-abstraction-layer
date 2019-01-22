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
import com.github.mffbrokenswing.mcmodabstraction.impl.mod.builder.dependency.DependencyBuilder;
import com.github.mffbrokenswing.mcmodabstraction.impl.mod.builder.dependency.IDependencyBuilderConstraintStep;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DependencyBuilderTest
{

    @Test(expected = IllegalStateException.class)
    public void disallowReuseTest()
    {
        List<IDependency> l = new ArrayList<>();
        IDependencyBuilderConstraintStep step = new DependencyBuilder("jei", l)
                .max("1.0.0");
        step.constraints(EDependencyConstraint.REQUIRED, EDependencyConstraint.LOAD_AFTER);
        step.constraints(EDependencyConstraint.REQUIRED, EDependencyConstraint.LOAD_AFTER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void atLeastOnConstraintTest()
    {
        List<IDependency> l = new ArrayList<>();
        new DependencyBuilder("jei", l).min("1.0.0").constraints();
    }

    @Test(expected = NullPointerException.class)
    public void nonNullDependencyIdTest()
    {
        List<IDependency> l = new ArrayList<>();
        new DependencyBuilder(null, l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonEmptyDependencyIdTest()
    {
        List<IDependency> l = new ArrayList<>();
        new DependencyBuilder("", l);
    }

    @Test
    public void correctlyAddDependencyToListMinVerTest()
    {
        String depId = "jei";
        String minVer = "1.0.0";
        EDependencyConstraint[] constraints = {EDependencyConstraint.REQUIRED, EDependencyConstraint.LOAD_AFTER};

        List<IDependency> l = new ArrayList<>();

        new DependencyBuilder(depId, l)
                .min(minVer)
                .constraints(constraints);

        assertThat(l).hasSize(1);

        IDependency dep = l.get(0);

        assertThat(dep.getId()).isEqualTo(depId);
        assertThat(dep.getMinimalVersion().getVersionString()).isEqualTo(minVer);
        assertThat(dep.getMaximalVersion()).isEqualTo(IVersion.UNSPECIFIED);
        assertThat(dep.getLoadingConstraints()).hasSize(2);
        assertThat(dep.getLoadingConstraints()).contains(constraints);

    }

    @Test
    public void correctlyAddDependencyToListBetweenVerTest()
    {
        String depId = "enderio";
        String minVer = "1.4.3";
        String maxVer = "1.7.2";
        EDependencyConstraint constraint = EDependencyConstraint.REQUIRED;

        List<IDependency> l = new ArrayList<>();

        new DependencyBuilder(depId, l)
                .between(minVer, maxVer)
                .constraints(constraint);

        assertThat(l).hasSize(1);

        IDependency dep = l.get(0);

        assertThat(dep.getId()).isEqualTo(depId);
        assertThat(dep.getMinimalVersion().getVersionString()).isEqualTo(minVer);
        assertThat(dep.getMaximalVersion().getVersionString()).isEqualTo(maxVer);
        assertThat(dep.getLoadingConstraints()).hasSize(1);
        assertThat(dep.getLoadingConstraints()).contains(constraint);
    }

    @Test
    public void correctlyAddDependencyToListMaxVerTest()
    {
        String depId = "jei";
        String maxVer = "1.0.0";
        EDependencyConstraint[] constraints = {EDependencyConstraint.REQUIRED, EDependencyConstraint.LOAD_AFTER};

        List<IDependency> l = new ArrayList<>();

        new DependencyBuilder(depId, l)
                .max(maxVer)
                .constraints(constraints);

        assertThat(l).hasSize(1);

        IDependency dep = l.get(0);

        assertThat(dep.getId()).isEqualTo(depId);
        assertThat(dep.getMinimalVersion()).isEqualTo(IVersion.UNSPECIFIED);
        assertThat(dep.getMaximalVersion().getVersionString()).isEqualTo(maxVer);
        assertThat(dep.getLoadingConstraints()).hasSize(2);
        assertThat(dep.getLoadingConstraints()).contains(constraints);
    }

}
