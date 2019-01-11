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

import java.util.Set;

public interface IDependency
{
    /**
     * Returns the unique id which is returned by the dependency on {@link IMod#getId()}
     * @see IMod#getId() for more information on id
     * @return the unique if for the dependency
     */
    String getId();

    /**
     * Returns the constraints between the current mod and this dependency.
     * These constraints will be used to define the load order of mods and detect cyclic dependencies
     * @return a set of constraints
     */
    Set<EDependencyConstraint> getLoadingConstraints();

    /**
     * Returns the minimal required version for this dependency. If the detected dependency version is lower than the
     * returned version then this loading will stop and advert the user.
     * If the method returns {@link IVersion#UNSPECIFIED} then the mod will load with any version of the dependency
     * lower than {@link #getMaximalVersion()}.
     * @return the minimal version for the dependency
     */
    IVersion getMinimalVersion();

    /**
     * Returns the maximal required version for this dependency. If the detected dependency version is higher than the
     * returned version then this loading will stop and advert the user.
     * If the method returns {@link IVersion#UNSPECIFIED} then the mod will load with any version of the dependency
     * higher than {@link #getMinimalVersion()}.
     * @return the maximal version for the dependency
     */
    IVersion getMaximalVersion();

}
