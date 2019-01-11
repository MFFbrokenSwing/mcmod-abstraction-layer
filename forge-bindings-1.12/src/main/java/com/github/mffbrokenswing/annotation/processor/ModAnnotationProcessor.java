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
package com.github.mffbrokenswing.annotation.processor;

import com.github.mffbrokenswing.api.mod.Mod;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("com.github.mffbrokenswing.api.mod.Mod")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ModAnnotationProcessor extends AbstractProcessor
{

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        Messager messager = this.processingEnv.getMessager();

        Filer filer = this.processingEnv.getFiler();

        messager.printMessage(Diagnostic.Kind.NOTE, "Processor starting !");

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Mod.class);
        elements.forEach(el -> {
            try
            {
                PrintWriter writer = new PrintWriter(
                        filer.createResource(StandardLocation.SOURCE_OUTPUT, "", "test.txt")
                        .openOutputStream()
                );
                writer.println("Working !");
                writer.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        return true;
    }

}
