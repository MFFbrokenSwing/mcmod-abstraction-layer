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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class VersionTest
{

    @Test
    public void toStringTest()
    {
        String strVer = "1.2.5";
        IVersion version = new Version(strVer);

        assertThat(version.toString()).isEqualTo(strVer);

        assertThat(version.getVersionString()).isEqualTo(strVer);
    }

    @Test(expected = NullPointerException.class)
    public void nonNullArgTest()
    {
        new Version(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonEmptyVersionTest()
    {
        new Version("");
    }

}
