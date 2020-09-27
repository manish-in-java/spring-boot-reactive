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

package org.example.controller;

import org.example.domain.CurrencyExchangeRate;
import org.example.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Currency;

/**
 * Fetches exchange rates.
 */
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
@RestController
public class ExchangeRateController
{
  @Autowired
  private ExchangeRateService service;

  /**
   * Gets current exchange rates for a currency-pair.
   *
   * @param from The source currency for which exchange rates are required.
   * @param to   The target currency to which exchange rates are required.
   */
  @GetMapping("/rates/{from}/{to}")
  public Flux<CurrencyExchangeRate> getExchangeRates(@PathVariable final String from, @PathVariable final String to)
  {
    return service.getExchangeRates(Currency.getInstance(from), Currency.getInstance(to));
  }
}
