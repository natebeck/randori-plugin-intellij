/*
 * Copyright 2013 original Randori IntelliJ Plugin authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package randori.plugin.lang.css;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.css.CssElementDescriptorProvider;
import com.intellij.psi.css.CssPropertyDescriptor;
import com.intellij.psi.css.CssSimpleSelector;
import com.intellij.psi.css.CssTerm;
import randori.plugin.util.ProjectUtils;

/**
 * @author Roland Zwaga <roland@stackandheap.com>
 */
public class RandoriCssElementDescriptorProvider extends
        CssElementDescriptorProvider
{
    private static final String RANDORI_PREFIX = "-randori";
    private static final String[] randoriNames = new String[4];
    static
    {
        randoriNames[0] = RANDORI_PREFIX + "-behavior";
        randoriNames[1] = RANDORI_PREFIX + "-mediator";
        randoriNames[2] = RANDORI_PREFIX + "-context";
        randoriNames[3] = RANDORI_PREFIX + "-fragment";
    }
    private static final Map<String, String> superClassLookup;
    static
    {
        Map<String, String> tempMap = new HashMap<String, String>();
        tempMap.put(randoriNames[0], "randori.behaviors.AbstractBehavior");
        tempMap.put(randoriNames[1], "randori.behaviors.AbstractMediator");
        tempMap.put(randoriNames[2], "guice.GuiceModule");
        superClassLookup = Collections.unmodifiableMap(tempMap);
    }
    private static final Map<String, CssPropertyDescriptor> descriptorLookup;
    static
    {
        Map<String, CssPropertyDescriptor> tempMap = new HashMap<String, CssPropertyDescriptor>();
        tempMap.put(randoriNames[0], createCssPropertyDescriptor(randoriNames[0]));
        tempMap.put(randoriNames[1], createCssPropertyDescriptor(randoriNames[1]));
        tempMap.put(randoriNames[2], createCssPropertyDescriptor(randoriNames[2]));
        tempMap.put(randoriNames[3], createCssPropertyDescriptor(randoriNames[3]));
        descriptorLookup = Collections.unmodifiableMap(tempMap);
    }

    private static CssPropertyDescriptor createCssPropertyDescriptor(String randoriName) {
        return new RandoriCssPropertyDescriptor(randoriName, superClassLookup);
    }

    public RandoriCssElementDescriptorProvider()
    {
    }

    private CssElementDescriptorProvider _base;

    private CssElementDescriptorProvider getBaseProvider()
    {
        if (_base == null)
        {
            CssElementDescriptorProvider[] providers = CssElementDescriptorProvider.EP_NAME.getExtensions();
            _base = providers.length > 0 ? providers[providers.length - 1] : null;
        }
        return _base;
    }

    @Override
    public boolean isMyContext(@Nullable PsiElement context)
    {
        return getBaseProvider().isMyContext(context);
    }

    @Nullable
    @Override
    public CssPropertyDescriptor getPropertyDescriptor(
            @NotNull String propertyName, @Nullable PsiElement context)
    {
        if (descriptorLookup.containsKey(propertyName))
        {
            return descriptorLookup.get(propertyName);
        }
        else
        {
            return getBaseProvider().getPropertyDescriptor(propertyName, context);
        }
    }

    @Override
    public boolean isPossibleSelector(@NotNull String selector,
            @NotNull PsiElement context)
    {
        return getBaseProvider().isPossibleSelector(selector, context);
    }

    @Override
    public boolean isPossiblePseudoClass(@NotNull String pseudoClass,
            @NotNull PsiElement context)
    {
        return getBaseProvider().isPossiblePseudoClass(pseudoClass, context);
    }

    @NotNull
    @Override
    public String[] getPossiblePseudoClasses(@NotNull PsiElement context)
    {
        return getBaseProvider().getPossiblePseudoClasses(context);
    }

    @NotNull
    @Override
    public String[] getPropertyNames(@Nullable PsiElement context)
    {
        String[] names = getBaseProvider().getPropertyNames(context);
        if (context != null) {
            if (ProjectUtils.hasRandoriModuleType(context.getProject()))
            {
                int aLen = names.length;
                int bLen = randoriNames.length;
                String[] propertyNames = new String[aLen+bLen];
                System.arraycopy(names, 0, propertyNames, 0, aLen);
                System.arraycopy(randoriNames, 0, propertyNames, aLen, bLen);
                return propertyNames;
            }
        }
        return names;
    }

    @NotNull
    @Override
    public String[] getSimpleSelectors(@Nullable PsiElement context)
    {
        return getBaseProvider().getSimpleSelectors(context);
    }

    @NotNull
    @Override
    public PsiElement[] getDeclarationsForSimpleSelector(
            @NotNull CssSimpleSelector selector)
    {
        return getBaseProvider().getDeclarationsForSimpleSelector(selector);
    }

    @Nullable
    @Override
    public PsiElement getDocumentationElementForSelector(@NotNull String text,
            @Nullable PsiElement context)
    {
        return getBaseProvider().getDocumentationElementForSelector(text, context);
    }

    @Override
    public boolean providesClassicCss()
    {
        return getBaseProvider().providesClassicCss();
    }

    @Nullable
    @Override
    public String generateDocForSelector(@NotNull String s,
            @NotNull PsiElement context)
    {
        return getBaseProvider().generateDocForSelector(s, context);
    }

    @NotNull
    @Override
    public PsiReference getStyleReference(PsiElement element, int start,
            int end, boolean caseSensitive)
    {
        return getBaseProvider().getStyleReference(element, start, end, caseSensitive);
    }

    @Nullable
    @Override
    public Color getColorByValue(@NotNull String value)
    {
        return getBaseProvider().getColorByValue(value);
    }

    @Override
    public boolean isColorTerm(@NotNull CssTerm term)
    {
        return getBaseProvider().isColorTerm(term);
    }
}
