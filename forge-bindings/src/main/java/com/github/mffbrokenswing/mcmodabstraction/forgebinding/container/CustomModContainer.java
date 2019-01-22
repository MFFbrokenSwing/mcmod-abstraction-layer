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
package com.github.mffbrokenswing.mcmodabstraction.forgebinding.container;

import com.github.mffbrokenswing.mcmodabstraction.api.mod.EDependencyConstraint;
import com.github.mffbrokenswing.mcmodabstraction.api.mod.IDependency;
import com.github.mffbrokenswing.mcmodabstraction.api.mod.IMod;
import com.github.mffbrokenswing.mcmodabstraction.api.mod.IVersion;
import com.google.common.base.Joiner;
import com.google.common.eventbus.EventBus;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.discovery.ModCandidate;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import net.minecraftforge.fml.common.versioning.DependencyParser;
import net.minecraftforge.fml.common.versioning.VersionRange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomModContainer implements ModContainer
{

    private static final Logger LOGGER = LogManager.getLogger(CustomModContainer.class);

    private IMod mod;
    private ModCandidate container;
    private ModMetadata metadata;
    private int classVersion;
    private boolean enabled;
    private EventBus eventBus;
    private LoadController controller;

    public CustomModContainer(String className, ModCandidate container, Map<String, Object> modDescriptor)
    {
        this.container = container;
        this.metadata = new ModMetadata();
        this.enabled = true;

        this.loadModClass(className);
        this.populateMetadata();

    }

    @Override
    public String getModId()
    {
        return this.mod.getId();
    }

    @Override
    public String getName()
    {
        return this.mod.getName();
    }

    @Override
    public String getVersion()
    {
        return this.mod.getVersion().getVersionString();
    }

    @Override
    public File getSource()
    {
        return this.container.getModContainer();
    }

    @Override
    public ModMetadata getMetadata()
    {
        return this.metadata;
    }

    @Override
    public void bindMetadata(MetadataCollection mc)
    {
        // Mod Metadata should be retrieved from the mod class
    }

    @Override
    public void setEnabledState(boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public Set<ArtifactVersion> getRequirements()
    {
        return this.metadata.requiredMods;
    }

    @Override
    public List<ArtifactVersion> getDependencies()
    {
        return this.metadata.dependencies;
    }

    @Override
    public List<ArtifactVersion> getDependants()
    {
        return this.metadata.dependants;
    }

    @Override
    public String getSortingRules()
    {
        return this.getDependencyString();
    }

    // Taken from FMLModContainer
    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        if (this.enabled)
        {
            FMLLog.log.debug("Enabling mod {}", getModId());
            this.eventBus = bus;
            this.controller = controller;
            eventBus.register(this);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean matches(Object mod)
    {
        return this.mod == mod;
    }

    @Override
    public Object getMod()
    {
        return this.mod;
    }

    private ArtifactVersion processedVersion = null;

    @Override
    public ArtifactVersion getProcessedVersion()
    {
        if(this.processedVersion == null)
        {
            this.processedVersion = new DefaultArtifactVersion(this.getModId(), this.getVersion());
        }
        return this.processedVersion;
    }

    @Override
    public boolean isImmutable()
    {
        return false;
    }

    @Override
    public String getDisplayVersion()
    {
        return this.getVersion();
    }

    @Override
    public VersionRange acceptableMinecraftVersionRange()
    {
        return Loader.instance().getMinecraftModContainer().getStaticVersionRange();
    }

    @Nullable
    @Override
    public Certificate getSigningCertificate()
    {
        return null;
    }

    @Override
    public Map<String, String> getCustomModProperties()
    {
        return new HashMap<>();
    }

    @Override
    public Class<?> getCustomResourcePackClass()
    {
        return null;
    }

    @Override
    public Map<String, String> getSharedModDescriptor()
    {
        Map<String, String> descriptor = new HashMap<>();
        descriptor.put("modsystem", "FML");
        descriptor.put("id", this.getModId());
        descriptor.put("version", this.getDisplayVersion());
        descriptor.put("name", this.getName());
        return descriptor;
    }

    @Override
    public Disableable canBeDisabled()
    {
        return Disableable.NEVER;
    }

    @Override
    public String getGuiClassName()
    {
        return null;
    }

    @Override
    public List<String> getOwnedPackages()
    {
        return this.container.getContainedPackages();
    }

    @Override
    public boolean shouldLoadInEnvironment()
    {
        return true; //TODO
    }

    @Override
    public URL getUpdateUrl()
    {
        return null;
    }

    @Override
    public void setClassVersion(int classVersion)
    {
        this.classVersion = classVersion;
    }

    @Override
    public int getClassVersion()
    {
        return this.classVersion;
    }

    private void loadModClass(String className)
    {
        Class<?> discoveredClass;

        try
        {
            discoveredClass = Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            FormattedMessage message = new FormattedMessage(
                    "{} loader failed to load class {}", CustomModContainer.class.getSimpleName(), className
            );
            throw new LoaderException(message.getFormattedMessage(), e);
        }

        if(!IMod.class.isAssignableFrom(discoveredClass))
        {
            FormattedMessage message = new FormattedMessage(
                    "Class {} isn't an instance of {}, can't load it.", className, IMod.class.getName()
            );
            throw new LoaderException(message.getFormattedMessage());
        }

        try
        {
            this.mod = (IMod) discoveredClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            FormattedMessage message = new FormattedMessage(
                    "Class {} does not provide an arg-less public constructor.", className
            );
            throw new LoaderException(message.getFormattedMessage(), e);
        }
    }

    private String getDependencyString()
    {
        return this.mod.getDependencies().stream()
                .map(this::getDependencyString)
                .reduce(String::concat).orElse("");
    }

    private String getConstraintStringFromEnum(EDependencyConstraint c)
    {
        switch(c)
        {
            case REQUIRED: return "required";
            case LOAD_AFTER: return "after";
            case ON_CLIENT: return "client";
            case ON_SERVER: return "server";
            case LOAD_BEFORE: return "before";
            default: return "";
        }
    }

    private String getDependencyString(IDependency dependency)
    {
        StringBuilder builder = new StringBuilder();

        // Constraints
        Joiner.on('-').appendTo(builder,
                dependency.getLoadingConstraints()
                        .stream()
                        .map(this::getConstraintStringFromEnum)
                        .collect(Collectors.toSet())
        );

        // Mod ID
        builder.append(':').append(dependency.getId()).append('@');

        // Minimal version
        if(dependency.getMinimalVersion() == IVersion.UNSPECIFIED)
        {
            builder.append("(");
        }
        else
        {
            builder.append("[").append(dependency.getMinimalVersion());
        }

        // Maximal version
        if(dependency.getMaximalVersion() == IVersion.UNSPECIFIED)
        {
            builder.append(",)");
        }
        else
        {
            builder.append(',').append(dependency.getMaximalVersion()).append(']');
        }

        // Close declaration
        builder.append(';');

        return builder.toString();
    }

    private void populateMetadata()
    {
        DependencyParser parser = new DependencyParser(getModId(), FMLCommonHandler.instance().getSide());
        DependencyParser.DependencyInfo info = parser.parseDependencies(this.getDependencyString());
        this.metadata.requiredMods = info.requirements;
        this.metadata.dependencies = info.dependencies;
        this.metadata.dependants = info.dependants;

        this.metadata.description = this.mod.getDescription();
    }
}
