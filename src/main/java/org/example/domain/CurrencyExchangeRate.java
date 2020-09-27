/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.example.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * The rate or multiplier to use for exchanging one currency for another.
 */
public class CurrencyExchangeRate
{
  private static final int MAXIMUM_PRECISION = 5;

  private String   provider;
  private Currency source, target;
  private BigDecimal value;

  /**
   * Gets the name of the provider from which the exchange rate has been
   * obtained.
   *
   * @return The name of the provider from which the exchange rate has been
   * obtained.
   */
  public String getProvider()
  {
    return provider;
  }

  /**
   * Gets the source currency for the conversion. This is the currency to
   * exchange.
   *
   * @return The source currency for the conversion.
   */
  public Currency getSource()
  {
    return source;
  }

  /**
   * Gets the target currency for the conversion. This is the currency to which
   * the exchange is desired.
   *
   * @return The target currency for the conversion.
   */
  public Currency getTarget()
  {
    return target;
  }

  /**
   * <p>
   * Gets the rate to use for converting from the source to the target currency.
   * This is used as a simple multiplier for an amount expressed in the source
   * currency to perform a conversion to an amount expressed in the target
   * currency.
   * </p>
   *
   * <p>
   * For example, if {@code source} is {@code EUR}, {@code target} is
   * {@code USD} and {@code value} is {@code 1.109}, it means that an amount
   * in {@code EUR} can be converted to an equivalent amount in {@code USD}
   * through multiplication with {@code 1.109}
   * ({@code 1000 EUR = 1000 x 1.109 = 1109 USD}).
   * </p>
   *
   * @return The rate to use for converting from the source to the target
   * currency.
   */
  public BigDecimal getValue()
  {
    return value;
  }

  /**
   * Sets the name of the provider from which the exchange rate has been
   * obtained.
   *
   * @param provider The name of the provider from which the exchange rate has
   *                 been obtained.
   */
  public void setProvider(final String provider)
  {
    this.provider = provider;
  }

  /**
   * Sets the source currency for the conversion.
   *
   * @param source The source currency for the conversion.
   */
  public void setSource(final Currency source)
  {
    this.source = source;
  }

  /**
   * Sets the target currency for the conversion.
   *
   * @param target The target currency for the conversion.
   */
  public void setTarget(final Currency target)
  {
    this.target = target;
  }

  /**
   * Sets the rate to use for converting from the source to the target currency.
   * The rate is rounded off to the nearest value, up to 5 decimal places.
   *
   * @param value The rate of exchange.
   */
  public void setValue(final BigDecimal value)
  {
    this.value = value.setScale(MAXIMUM_PRECISION, RoundingMode.HALF_UP);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString()
  {
    return "CurrencyExchangeRate(" +
        source + " -> " +
        target + " = " +
        value + ")";
  }
}
