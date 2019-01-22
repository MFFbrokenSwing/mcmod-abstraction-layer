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
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class Dependency implements IDependency
{

    public String id;
    public IVersion minimalVersion;
    public IVersion maximalVersion;
    public Set<EDependencyConstraint> loadingConstraints;

    @Override
    public String getId()
    {
        return this.id;
    }

    @Override
    public Set<EDependencyConstraint> getLoadingConstraints()
    {
        return this.loadingConstraints;
    }

    @Override
    public IVersion getMinimalVersion()
    {
        return this.minimalVersion;
    }

    @Override
    public IVersion getMaximalVersion()
    {
        return this.maximalVersion;
    }

}
