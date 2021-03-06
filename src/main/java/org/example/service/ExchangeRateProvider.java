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
import reactor.core.publisher.Mono;

import java.util.Currency;

/**
 * Contract for fetching currency exchange rates from an internet source.
 */
public interface ExchangeRateProvider
{
  /**
   * Gets current exchange rate for converting one currency (the source) to
   * another (the target), from an internet source.
   *
   * @param source The source currency for the conversion.
   * @param target The target currency for the conversion.
   * @return Exchange rate from the source to the target.
   */
  Mono<CurrencyExchangeRate> getExchangeRate(Currency source, Currency target);
}
