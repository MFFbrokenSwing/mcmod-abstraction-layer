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

public interface IVersion
{
    /** Used to indicate the version isn't specified */
    IVersion UNSPECIFIED = () -> "UNSPECIFIED";

    /**
     * Must be numbers dot-separated version. Use 'major.minor.incremental.buildnumber'
     * Example : 10.25.36 or 1.25.65.456
     * @return the String representing the version
     */
    String getVersionString();

}
