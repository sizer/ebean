package com.avaje.ebean.search;

/**
 * Options for the text match expression.
 */
public class Match extends BaseMatch {

  protected boolean phrase;

  protected boolean phrasePrefix;

  /**
   * Create and return Match options using AND operator.
   */
  public static Match AND() {
    return new Match().opAnd();
  }

  /**
   * Create and return Match options using OR operator.
   */
  public static Match OR() {
    return new Match().opOr();
  }

  /**
   * Set this to be a "Phrase" type expression.
   */
  public Match phrase() {
    phrase = true;
    return this;
  }

  /**
   * Set this to be a "Phrase Prefix" type expression.
   */
  public Match phrasePrefix() {
    phrasePrefix = true;
    return this;
  }

  /**
   * Use the AND operator (rather than OR).
   */
  public Match opAnd() {
    and = true;
    return this;
  }

  /**
   * Use the OR operator (rather than AND).
   */
  public Match opOr() {
    and = false;
    return this;
  }

  /**
   * Set the zero terms.
   */
  public Match zeroTerms(String zeroTerms) {
    this.zeroTerms = zeroTerms;
    return this;
  }

  /**
   * Set the cutoff frequency.
   */
  public Match cutoffFrequency(double cutoffFrequency) {
    this.cutoffFrequency = cutoffFrequency;
    return this;
  }

  /**
   * Set the max expansions (for phrase prefix only).
   */
  public Match maxExpansions(int maxExpansions) {
    this.maxExpansions = maxExpansions;
    return this;
  }

  /**
   * Set the Analyzer to use for this expression.
   */
  public Match analyzer(String analyzer) {
    this.analyzer = analyzer;
    return this;
  }

  /**
   * Set the boost.
   */
  public Match boost(double boost) {
    this.boost = boost;
    return this;
  }

  /**
   * Set the rewrite to use.
   */
  public Match minShouldMatch(String minShouldMatch) {
    this.minShouldMatch = minShouldMatch;
    return this;
  }

  /**
   * Set the rewrite to use.
   */
  public Match rewrite(String rewrite) {
    this.rewrite = rewrite;
    return this;
  }

  /**
   * Return true if this is a phrase query.
   */
  public boolean isPhrase() {
    return phrase;
  }

  /**
   * Return true if this is a phrase prefix query.
   */
  public boolean isPhrasePrefix() {
    return phrasePrefix;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Match match = (Match) o;

    if (phrase != match.phrase) return false;
    if (phrasePrefix != match.phrasePrefix) return false;
    return baseEquals(match);
  }

  @Override
  public int hashCode() {
    int result = (phrase ? 1 : 0);
    result = 31 * result + (phrasePrefix ? 1 : 0);
    result = 31 * result + baseHashCode();
    return result;
  }
}
