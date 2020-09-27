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

package org.example.service;

import org.example.domain.CurrencyExchangeRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for an {@link ExchangeRateProvider}.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class ExchangeRateProviderTests
{
  private static final List<Currency> CURRENCIES = Arrays.asList(Currency.getInstance("CAD")
      , Currency.getInstance("EUR")
      , Currency.getInstance("GBP")
      , Currency.getInstance("INR")
      , Currency.getInstance("JPY")
      , Currency.getInstance("USD"));

  /**
   * Tests that exchange rates can be fetched correctly for currency pairs
   * supported by this provider.
   */
  @Test
  public void testGetExchangeRate()
  {
    CURRENCIES.forEach(source -> CURRENCIES.forEach(target -> {
      if (!source.equals(target))
      {
        final Mono<CurrencyExchangeRate> result = getProvider().getExchangeRate(source, target);

        assertNotNull(result);

        final CurrencyExchangeRate rate = result.block();

        assertNotNull(rate);
        assertNotNull(rate.getProvider());
        assertNotNull(rate.getSource());
        assertEquals(source, rate.getSource());
        assertNotNull(rate.getTarget());
        assertEquals(target, rate.getTarget());
        assertNotNull(rate.getValue());
      }
    }));
  }

  /**
   * Gets an {@link ExchangeRateProvider} that can be used to fetch currency
   * exchange rates.
   *
   * @return An {@link ExchangeRateProvider}.
   */
  protected abstract ExchangeRateProvider getProvider();
}
