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
import com.github.mffbrokenswing.api.mod.IVersion;
import com.github.mffbrokenswing.impl.mod.builder.dependency.DependencyBuilder;
import com.github.mffbrokenswing.impl.mod.builder.dependency.IDependencyBuilderVersionStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractMod implements IMod
{

    private ArrayList<IDependency> dependencies;
    private String modId;
    private String name;
    private Version version;

    public AbstractMod(String modId, String name, String version)
    {
        this.modId = check(
                modId,
                id -> !id.isEmpty() && !id.contains(" ") && id.equals(id.toLowerCase()),
                "Mod ID '%s' must be a lowercased spaceless non empty string"
        );
        this.name = check(name, n -> !n.isEmpty(), "The mod name must not be empty.");
        this.version = new Version(check(version, v -> !v.isEmpty(), "The version must not be empty."));

        this.dependencies = new ArrayList<>();
    }

    @Override
    public String getId()
    {
        return this.modId;
    }

    @Override
    public String getName()
    {
        return this.name;
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

    private static <T> T check(T value, Function<T, Boolean> checker, String message)
    {
        if(checker.apply(value))
            return value;

        throw new IllegalArgumentException(String.format(message, value));
    }

    @Override
    public String getDescription()
    {
        return "The name of this mod is" + this.getName();
    }

    @Override
    public IVersion getVersion()
    {
        return this.version;
    }
}
