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
package com.github.mffbrokenswing.mcmodabstraction.forgebinding;

import com.github.mffbrokenswing.mcmodabstraction.api.mod.EDependencyConstraint;
import com.github.mffbrokenswing.mcmodabstraction.api.mod.IDependency;
import com.github.mffbrokenswing.mcmodabstraction.api.mod.IMod;
import com.github.mffbrokenswing.mcmodabstraction.api.mod.IVersion;
import com.github.mffbrokenswing.mcmodabstraction.forgebinding.container.CustomModContainer;
import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.relauncher.Side;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ContainerTest
{

    private IMod mod;
    private IDependency jei;
    private IDependency enderio;
    private IDependency other;

    @Before
    public void prepare()
    {
        this.jei = new IDependency()
        {
            @Override
            public String getId()
            {
                return "jei";
            }

            @Override
            public Set<EDependencyConstraint> getLoadingConstraints()
            {
                return EnumSet.of(EDependencyConstraint.REQUIRED, EDependencyConstraint.ON_CLIENT, EDependencyConstraint.LOAD_AFTER);
            }

            @Override
            public IVersion getMinimalVersion()
            {
                return () -> "1.2.5";
            }

            @Override
            public IVersion getMaximalVersion()
            {
                return IVersion.UNSPECIFIED;
            }
        };

        this.enderio = new IDependency()
        {
            @Override
            public String getId()
            {
                return "enderio";
            }

            @Override
            public Set<EDependencyConstraint> getLoadingConstraints()
            {
                return EnumSet.of(EDependencyConstraint.LOAD_BEFORE, EDependencyConstraint.ON_SERVER);
            }

            @Override
            public IVersion getMinimalVersion()
            {
                return IVersion.UNSPECIFIED;
            }

            @Override
            public IVersion getMaximalVersion()
            {
                return () -> "1.0.0";
            }
        };

        this.other = new IDependency()
        {
            @Override
            public String getId()
            {
                return "other";
            }

            @Override
            public Set<EDependencyConstraint> getLoadingConstraints()
            {
                return EnumSet.of(EDependencyConstraint.LOAD_AFTER);
            }

            @Override
            public IVersion getMinimalVersion()
            {
                return () -> "1.0.0";
            }

            @Override
            public IVersion getMaximalVersion()
            {
                return () -> "1.2.5";
            }
        };


        this.mod = new IMod()
        {
            @Override
            public String getId()
            {
                return "testmod";
            }

            @Override
            public String getName()
            {
                return "Test Mod";
            }

            @Override
            public IVersion getVersion()
            {
                return () -> "1.0.0";
            }

            @Override
            public List<IDependency> getDependencies()
            {
                return Lists.newArrayList(jei, enderio, other);
            }

            @Override
            public String getDescription()
            {
                return "A Test Mod";
            }
        };
    }

    @Test
    public void containerLinkingTest()
    {
        CustomModContainer container = new CustomModContainer(this.mod);

        assertThat(container.getModId()).isEqualTo(this.mod.getId());
        assertThat(container.getName()).isEqualTo(this.mod.getName());
        assertThat(container.getVersion()).isEqualTo(this.mod.getVersion().getVersionString());
        assertThat(container.getDisplayVersion()).isEqualTo(this.mod.getVersion().getVersionString());

        assertThat(container.getMod() == this.mod).isTrue();
        assertThat(container.matches(this.mod)).isTrue();
    }

    @Test
    public void wrongContainerMatchTest()
    {
        IMod otherMod = Mockito.mock(IMod.class);
        CustomModContainer container = new CustomModContainer(this.mod);

        assertThat(container.matches(otherMod)).isFalse();
    }

    @Test
    public void metadataFillingClientTest()
    {
        ModMetadata metadata = new ModMetadata();
        CustomModContainer.populateMetadata(metadata, this.mod, Side.CLIENT);

        assertThat(metadata.description).isEqualTo(this.mod.getDescription());
        assertThat(metadata.requiredMods).hasSize(1);
        assertThat(metadata.dependencies).hasSize(2);
        assertThat(metadata.dependants).hasSize(0);

        CustomModContainer container = new CustomModContainer(this.mod);
        CustomModContainer.populateMetadata(container.getMetadata(), this.mod, Side.CLIENT);

        assertThat(container.getRequirements()).hasSize(1);
        assertThat(container.getDependencies()).hasSize(2);
        assertThat(container.getDependants()).hasSize(0);
    }


    @Test
    public void metadataFillingServerTest()
    {
        ModMetadata metadata = new ModMetadata();
        CustomModContainer.populateMetadata(metadata, this.mod, Side.SERVER);

        assertThat(metadata.description).isEqualTo(this.mod.getDescription());
        assertThat(metadata.requiredMods).hasSize(0);
        assertThat(metadata.dependants).hasSize(1);
        assertThat(metadata.dependencies).hasSize(1);

        CustomModContainer container = new CustomModContainer(this.mod);
        CustomModContainer.populateMetadata(container.getMetadata(), this.mod, Side.SERVER);

        assertThat(container.getRequirements()).hasSize(0);
        assertThat(container.getDependants()).hasSize(1);
        assertThat(container.getDependencies()).hasSize(1);
    }

    @Test
    public void dependencyStringTest()
    {
        String jeiString = CustomModContainer.getDependencyString(this.jei);
        String enderioString = CustomModContainer.getDependencyString(this.enderio);
        String otherString = CustomModContainer.getDependencyString(this.other);

        assertThat(jeiString).isIn(Lists.newArrayList(
                "required-after-client:jei@[1.2.5,);",
                "required-client-after:jei@[1.2.5,);",
                "client-required-after:jei@[1.2.5,);",
                "client-after-required:jei@[1.2.5,);",
                "after-required-client:jei@[1.2.5,);",
                "after-client-required:jei@[1.2.5,);"
        ));

        assertThat(enderioString).isIn(Lists.newArrayList(
           "server-before:enderio@(,1.0.0];",
           "before-server:enderio@(,1.0.0];"
        ));

        assertThat(otherString).isEqualTo("after:other@[1.0.0,1.2.5];");
    }

    @Test
    public void constraintsTranslationTest()
    {
        assertThat(EDependencyConstraint.values()).hasSize(5);

        assertThat(CustomModContainer.getConstraintStringFromEnum(EDependencyConstraint.LOAD_AFTER)).isEqualTo("after");
        assertThat(CustomModContainer.getConstraintStringFromEnum(EDependencyConstraint.LOAD_BEFORE)).isEqualTo("before");
        assertThat(CustomModContainer.getConstraintStringFromEnum(EDependencyConstraint.REQUIRED)).isEqualTo("required");
        assertThat(CustomModContainer.getConstraintStringFromEnum(EDependencyConstraint.ON_CLIENT)).isEqualTo("client");
        assertThat(CustomModContainer.getConstraintStringFromEnum(EDependencyConstraint.ON_SERVER)).isEqualTo("server");
    }

    @Test
    public void busRegisteringTest()
    {
        EventBus bus = Mockito.mock(EventBus.class);
        LoadController controller = Mockito.mock(LoadController.class);

        CustomModContainer container = new CustomModContainer(this.mod);
        container.setEnabledState(true);

        assertThat(container.registerBus(bus, controller)).isTrue();

        container.setEnabledState(false);

        assertThat(container.registerBus(bus, controller)).isFalse();
    }

    @Test
    public void processedVersionTest()
    {
        CustomModContainer container = new CustomModContainer(this.mod);
        ArtifactVersion version = container.getProcessedVersion();

        assertThat(version.getLabel()).isEqualTo(this.mod.getId());
        assertThat(version.getVersionString()).isEqualTo(this.mod.getVersion().getVersionString());
    }

    @Test
    public void classVersionTest()
    {
        CustomModContainer container = new CustomModContainer(this.mod);

        container.setClassVersion(1);
        assertThat(container.getClassVersion()).isEqualTo(1);

        container.setClassVersion(500);
        assertThat(container.getClassVersion()).isEqualTo(500);
    }

}
