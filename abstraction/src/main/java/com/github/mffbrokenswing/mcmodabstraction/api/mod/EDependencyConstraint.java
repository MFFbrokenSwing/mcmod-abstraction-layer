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
package com.github.mffbrokenswing.mcmodabstraction.api.mod;

/**
 * This enum represents the possible constraints between mods loading.
 * Some of them are incompatible, you can't use together :
 *  - {@link EDependencyConstraint#LOAD_BEFORE} and {@link EDependencyConstraint#LOAD_AFTER}
 *  - {@link EDependencyConstraint#ON_CLIENT} and {@link EDependencyConstraint#ON_SERVER}
 */
public enum EDependencyConstraint
{
    /** The dependency is required */
    REQUIRED,
    /** The mod has to load before the dependency */
    LOAD_BEFORE,
    /** The mod has to load after the dependency */
    LOAD_AFTER,
    /** Indicates the dependency constraints are only applicable to client side */
    ON_CLIENT,
    /** Indicates the dependency constraints are only applicable to server side */
    ON_SERVER
}
