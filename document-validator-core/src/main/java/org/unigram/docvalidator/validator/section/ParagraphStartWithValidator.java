/**
 * redpen: a text inspection tool
 * Copyright (C) 2014 Recruit Technologies Co., Ltd. and contributors
 * (see CONTRIBUTORS.md)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.unigram.docvalidator.validator.section;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unigram.docvalidator.store.Paragraph;
import org.unigram.docvalidator.store.Section;
import org.unigram.docvalidator.store.Sentence;
import org.unigram.docvalidator.util.CharacterTable;
import org.unigram.docvalidator.util.ValidatorConfiguration;
import org.unigram.docvalidator.util.ValidationError;
import org.unigram.docvalidator.validator.SectionValidator;

/**
 * Validate whether paragraph start as specified.
 */
public class ParagraphStartWithValidator extends SectionValidator {
  /**
   * Default matter paragraph start with.
   */
  @SuppressWarnings("WeakerAccess")
  public static final String DEFAULT_PARAGRAPH_START_WITH = " ";

  /**
   * Constructor.
   */
  public ParagraphStartWithValidator() {
    super();
    this.beginningOfParagraph = DEFAULT_PARAGRAPH_START_WITH;
  }

  @Override
  protected List<ValidationError> check(Section section) {
    List<ValidationError> validationErrors = new ArrayList<ValidationError>();
    for (Iterator<Paragraph> paraIterator =
        section.getParagraphs(); paraIterator.hasNext();) {
      Paragraph currentParagraph = paraIterator.next();
      Sentence firstSentence = currentParagraph.getSentence(0);
      if (firstSentence.content.indexOf(this.beginningOfParagraph) != 0) {
        validationErrors.add(new ValidationError(
            "Found invalid beginning of paragraph: \"",
            firstSentence));
      }
    }
    return validationErrors;
  }

  @Override
  public boolean loadConfiguration(ValidatorConfiguration conf,
      CharacterTable characterTable) {
    if (conf.getAttribute("paragraph_start_with") == null) {
      this.beginningOfParagraph = DEFAULT_PARAGRAPH_START_WITH;
      LOG.info("Using the default value of paragraph_start_with.");
    } else {
      this.beginningOfParagraph = conf.getAttribute("paragraph_start_with");
    }
    return true;
  }

  private String beginningOfParagraph;

  private static final Logger LOG =
      LoggerFactory.getLogger(ParagraphStartWithValidator.class);

}
