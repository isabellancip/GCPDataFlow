/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hsbc.training.pipeline.function;

import org.apache.beam.sdk.transforms.DoFn;

import com.hsbc.training.pipeline.entity.TradeResult;

public class TradeResultFn extends DoFn<String, TradeResult> {

	private static final long serialVersionUID = -4142366830337566681L;

	private static TradeResultFn instance = new TradeResultFn();

	private TradeResultFn() {
	}

	public static TradeResultFn readFile() {
		return instance;
	}

	@ProcessElement
	public void processElement(@Element String input, OutputReceiver<TradeResult> out) {
		String[] values = input.split(",");
		if (!"TradeId".equalsIgnoreCase(values[0])) {
			String resultStr = "";
			for (int i = 2; i < values.length; i++) {
				if (i != values.length - 1) {
					resultStr += values[i] + ",";
				} else {
					resultStr += values[i];
				}
			}
			TradeResult traderesult = new TradeResult(values[0], values[1], resultStr);
			out.output(traderesult);
		}
	}
}
