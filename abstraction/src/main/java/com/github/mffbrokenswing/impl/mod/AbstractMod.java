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
package com.github.mffbrokenswing.impl.mod;

import com.github.mffbrokenswing.api.mod.IDependency;
import com.github.mffbrokenswing.api.mod.IMod;
import com.github.mffbrokenswing.impl.mod.builder.dependency.DependencyBuilder;
import com.github.mffbrokenswing.impl.mod.builder.dependency.IDependencyBuilderVersionStep;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMod implements IMod
{

    private ArrayList<IDependency> dependencies;

    public AbstractMod()
    {
        this.dependencies = new ArrayList<>();
    }

    protected IDependencyBuilderVersionStep dependsOn(String id)
    {
        return new DependencyBuilder(id, this.dependencies);
    }

    @Override
    public List<IDependency> getDependencies()
    {
        return this.dependencies;
    }

}
