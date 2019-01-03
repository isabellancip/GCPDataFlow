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

package com.hsbc.training.pipeline.transform;

import org.apache.beam.sdk.transforms.Combine;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;

import com.hsbc.training.pipeline.entity.CombinedTradeResult;
import com.hsbc.training.pipeline.entity.TradeResult;
import com.hsbc.training.pipeline.function.CombineTradeResultFn;

public class CombineTimestepTradeResultCompositeTransform
		extends PTransform<PCollection<TradeResult>, PCollection<KV<String, CombinedTradeResult>>> {

	private static final long serialVersionUID = 1L;

	@Override
	public PCollection<KV<String, CombinedTradeResult>> expand(PCollection<TradeResult> input) {
		return input.apply("Key by trade ID", ParDo.of(new DoFn<TradeResult, KV<String, TradeResult>>() {
			private static final long serialVersionUID = 1L;

			@ProcessElement
			public void processElement(@Element TradeResult input, OutputReceiver<KV<String, TradeResult>> out) {
				out.output(KV.of(input.getTradeId(), input));
			}
		})).apply("Collect Trade Results", Combine.perKey(new CombineTradeResultFn()));
	}
}
