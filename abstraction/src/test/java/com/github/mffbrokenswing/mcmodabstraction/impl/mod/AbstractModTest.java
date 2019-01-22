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
import com.github.mffbrokenswing.mcmodabstraction.api.mod.IMod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AbstractModTest
{

    @Test(expected = IllegalArgumentException.class)
    public void nonNullModIdTest()
    {
        new AbstractMod("", "the name", "1.0.0") {};
    }

    @Test(expected = IllegalArgumentException.class)
    public void spaceLessModIdTest()
    {
        new AbstractMod("test test", "the name", "1.0.0") {};
    }

    @Test(expected = IllegalArgumentException.class)
    public void lowerCasedModIdTest()
    {
        new AbstractMod("ThisIsNotLowerCased", "the name", "1.0.0") {};
    }

    @Test
    public void correctlyReturnsDataTest()
    {
        String modid = "goodmodid";
        String name = "The name";
        String version = "1.0.0";
        
        IMod mod = new AbstractMod(modid, name, version) {
            {
                this.dependsOn("jei").min("1.0.0").constraints(EDependencyConstraint.REQUIRED);
            }
        };

        assertThat(mod.getId()).isEqualTo(modid);
        assertThat(mod.getName()).isEqualTo(name);
        assertThat(mod.getVersion().getVersionString()).isEqualTo(version);
        assertThat(mod.getDependencies()).hasSize(1);
    }

}
