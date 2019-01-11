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
package com.github.mffbrokenswing.example;

import com.github.mffbrokenswing.api.mod.IVersion;
import com.github.mffbrokenswing.api.mod.Mod;
import com.github.mffbrokenswing.impl.mod.AbstractMod;
import com.github.mffbrokenswing.impl.mod.Version;

import static com.github.mffbrokenswing.api.mod.EDependencyConstraint.*;

@Mod
public class ExampleMod extends AbstractMod
{

    public ExampleMod()
    {
        this.dependsOn("jei").between("4.7.0.68", "4.13.1.225").noConstraints();
        this.dependsOn("enderio").min("3.2.5.90").constraints(REQUIRED, LOAD_AFTER);
        this.dependsOn("renderapi").max("4.8.7").constraints(REQUIRED, ON_CLIENT);
    }

    @Override
    public String getId()
    {
        return "examplemod";
    }

    @Override
    public String getName()
    {
        return "Example mod";
    }

    @Override
    public IVersion getVersion()
    {
        return new Version("1.0.0");
    }

}
