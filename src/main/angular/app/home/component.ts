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

import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

import { environment } from "../../environments/environment";

/*
 * Application home page.
 */
@Component({
  selector: 'app-home',
  templateUrl: './component.html'
})
export class HomeComponent {
  private static BASE_URL = environment.production ? "/rates/" : "http://localhost:8080/rates/";

  columns = ["source", "target", "value", "provider"];
  exchangeRateForm = new FormGroup({
    source: new FormControl(""),
    target: new FormControl("")
  });
  rates;

  constructor(private http: HttpClient) { }

  /**
   * Gets exchange rates for a selected currency pair.
   */
  getExchangeRates() {
    if (this.exchangeRateForm.valid) {
      const url = HomeComponent.BASE_URL + this.source.value + "/" + this.target.value;

      this.http
          .get(url)
          .toPromise()
          .then(data => this.rates = data); 

      this.exchangeRateForm.reset();
    }
  }

  get source() { return this.exchangeRateForm.get("source") }
  get target() { return this.exchangeRateForm.get("target") }
}
