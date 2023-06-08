/**
 * KusionStack. Copyright (c) 2020-2021 All Rights Reserved.
 */
package io.kusionstack.kcl.completion.keyword;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/**
 * @author amyxia
 * @version AsKeywordProvider: AsKeywordProvider.java, v 0.1 2021年02月23日 3:13 下午 amyxia Exp $
 */
public class AsKeywordProvider extends CompletionProvider<CompletionParameters> {
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context,
                                  @NotNull CompletionResultSet result) {
        result.addElement(LookupElementBuilder.create("as"));
    }
}