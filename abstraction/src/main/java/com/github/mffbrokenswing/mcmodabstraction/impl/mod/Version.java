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

import com.github.mffbrokenswing.mcmodabstraction.api.mod.IVersion;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode
public class Version implements IVersion
{

    private String version;

    public Version(String version)
    {
        Objects.requireNonNull(version, "The provided version musn't be null");
        if(version.isEmpty())
            throw new IllegalArgumentException("The provided version musn't be empty");

        this.version = version;
    }

    @Override
    public String getVersionString()
    {
        return this.version;
    }

    @Override
    public String toString() {
        return this.getVersionString();
    }

}
