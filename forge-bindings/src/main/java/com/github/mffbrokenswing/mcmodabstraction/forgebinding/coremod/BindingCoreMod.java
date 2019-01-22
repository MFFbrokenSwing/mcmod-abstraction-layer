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
package com.github.mffbrokenswing.mcmodabstraction.forgebinding.coremod;

import com.github.mffbrokenswing.mcmodabstraction.api.mod.Mod;
import com.github.mffbrokenswing.mcmodabstraction.forgebinding.container.CustomModContainer;
import net.minecraftforge.fml.common.ModContainerFactory;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class BindingCoreMod implements IFMLLoadingPlugin
{

    private static final Logger LOGGER = LogManager.getLogger(BindingCoreMod.class);

    public BindingCoreMod()
    {
        ModContainerFactory.instance().registerContainerType(Type.getType(Mod.class), CustomModContainer.class);
        LOGGER.info("{} annotation and {} class are now bind.", Mod.class, CustomModContainer.class);
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) { }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
