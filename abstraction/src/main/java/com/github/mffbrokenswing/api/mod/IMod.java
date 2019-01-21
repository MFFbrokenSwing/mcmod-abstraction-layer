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
package com.github.mffbrokenswing.api.mod;

import java.util.List;

public interface IMod
{

    /**
     * Returns an unique identifier in order to differentiate this mod from others.
     * Requires to be lowercased and not longer than 64 characters.
     * @return the ID of the mod
     */
    String getId();

    /**
     * Returns the name which will be displayed to the user for this mod.
     * @return the user-friendly name of the mod
     */
    String getName();


    /**
     * Returns the current version for the mod, this will be used to check compatibility with other mods.
     * @return the version of the mod
     */
    IVersion getVersion();

    /**
     * Returns the dependencies for the mod, this will be used to prevent loading when if a mod isn't present, or the
     * provided version of a mod is incompatible. It will also allow to define the order mods will be loaded.
     * @return a list a dependencies
     */
    List<IDependency> getDependencies();

    /**
     * Returns the description for the mod. This one will be readable by the user in mods' GUI.
     * @return the mod description
     */
    String getDescription();

}
