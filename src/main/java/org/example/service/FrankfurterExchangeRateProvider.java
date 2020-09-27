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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

/**
 * <p>
 * Loads currency exchange rates from
 * <a href="https://api.frankfurter.app">Frankfurter</a>, a free service for
 * exchange rates.
 * </p>
 *
 * <p>
 * Frankfurter is built on top of data published by the
 * <a href="https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html">European Central Bank</a>
 * (ECB). Therefore, it supports only a limited number of currencies, namely
 * those for which ECB has direct conversion rates.
 * </p>
 */
@Component
public class FrankfurterExchangeRateProvider implements ExchangeRateProvider
{
  private static final String    URL_BASE   = "https://api.frankfurter.app";
  private static final String    URL_PATH   = "/latest?from=%s&to=%s";
  private static final WebClient WEB_CLIENT = WebClient.create(URL_BASE);

  /**
   * {@inheritDoc}
   */
  @Override
  public Mono<CurrencyExchangeRate> getExchangeRate(final Currency source, final Currency target)
  {
    // Invoke the API for finding the current exchange rate for the given
    // currency-pair.
    return WEB_CLIENT.get()
                     .uri(String.format(URL_PATH, source.getCurrencyCode(), target.getCurrencyCode()))
                     .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                     .retrieve()
                     .bodyToMono(FrankfurterResponse.class)
                     .map(response -> {
                       // Prepare to return the exchange rate.
                       final CurrencyExchangeRate exchangeRate = new CurrencyExchangeRate();
                       exchangeRate.setProvider(URL_BASE);
                       exchangeRate.setSource(source);
                       exchangeRate.setTarget(target);

                       // Check if the API responded without an error.
                       if (response != null
                           && response.getRates() != null
                           && response.getRates().size() == 1
                           && response.getRates().containsKey(target))
                       {
                         exchangeRate.setValue(response.getRates().get(target));
                       }

                       return exchangeRate;
                     });
  }

  /**
   * The response from Frankfurter, containing the exchange rates for a given
   * currency.
   */
  private static class FrankfurterResponse
  {
    private Currency                  base;
    private Map<Currency, BigDecimal> rates;

    /**
     * Gets the base currency for which the exchange rates were requested.
     *
     * @return A {@link Currency}.
     */
    public Currency getBase()
    {
      return base;
    }

    /**
     * Gets a map of exchange rates for the specified base currency. The keys
     * in the map are target currencies to which exchange rates from the base
     * currency are available. The values in the map are the actual exchange
     * rates from the base to the target currencies.
     *
     * @return A {@link Map} of exchange rates.
     */
    public Map<Currency, BigDecimal> getRates()
    {
      return rates;
    }

    /**
     * Sets the base currency for which the exchange rates were requested.
     *
     * @param base A {@link Currency}.
     */
    public void setBase(final Currency base)
    {
      this.base = base;
    }

    /**
     * Sets a map of exchange rates for the specified base currency.
     *
     * @param rates A {@link Map} of exchange rates.
     */
    public void setRates(final Map<Currency, BigDecimal> rates)
    {
      this.rates = rates;
    }
  }
}
