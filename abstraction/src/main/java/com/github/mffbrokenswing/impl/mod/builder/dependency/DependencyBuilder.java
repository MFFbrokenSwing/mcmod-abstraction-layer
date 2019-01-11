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
package com.github.mffbrokenswing.impl.mod.builder.dependency;

import com.github.mffbrokenswing.api.mod.EDependencyConstraint;
import com.github.mffbrokenswing.api.mod.IDependency;
import com.github.mffbrokenswing.api.mod.IVersion;
import com.github.mffbrokenswing.impl.mod.Dependency;
import com.github.mffbrokenswing.impl.mod.Version;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class DependencyBuilder implements IDependencyBuilderVersionStep, IDependencyBuilderConstraintStep
{

    private boolean submitted;
    private String id;
    private IVersion minimalVersion;
    private IVersion maximalVersion;
    private Set<EDependencyConstraint> constraints;
    private List<IDependency> dependencies;

    public DependencyBuilder(String id, List<IDependency> dependencies)
    {
        this.id = id;
        this.minimalVersion = IVersion.UNSPECIFIED;
        this.maximalVersion = IVersion.UNSPECIFIED;
        this.constraints = EnumSet.noneOf(EDependencyConstraint.class);
        this.submitted = false;
        this.dependencies = dependencies;
    }

    @Override
    public IDependencyBuilderConstraintStep min(String version)
    {
        checkState();
        this.minimalVersion = new Version(version);
        return this;
    }

    @Override
    public IDependencyBuilderConstraintStep max(String version)
    {
        checkState();
        this.maximalVersion = new Version(version);
        return this;
    }

    @Override
    public IDependencyBuilderConstraintStep between(String minimalVersion, String maximalVersion)
    {
        checkState();
        this.min(minimalVersion);
        return this.max(maximalVersion);
    }

    @Override
    public void constraints(EDependencyConstraint ... constraints)
    {
        checkState();
        Collections.addAll(this.constraints, constraints);
        this.submit();
    }

    @Override
    public void noConstraints()
    {
        checkState();
        this.submit();
    }

    private void submit()
    {
        IDependency dependency = new Dependency(this.id, this.minimalVersion, this.maximalVersion, this.constraints);
        this.dependencies.add(dependency);
        this.submitted = true;
    }

    private void checkState()
    {
        if(this.submitted)
            throw new IllegalStateException("This dependency have already been submitted.");
    }

}
