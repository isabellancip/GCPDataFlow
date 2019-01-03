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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;

import com.hsbc.training.pipeline.entity.CompositeID;

public class ApplyFloorZeroAndReduceKeyFn
		extends DoFn<KV<CompositeID, Map<String, double[]>>, KV<CompositeID, Map<String, double[]>>> {

	private static final long serialVersionUID = 8013226786592760648L;

	@ProcessElement
	public void processElement(@Element KV<CompositeID, Map<String, double[]>> input,
			OutputReceiver<KV<CompositeID, Map<String, double[]>>> out) {
		Map<String, double[]> newResult = new HashMap<>();
		input.getValue().keySet().forEach(timestep -> {
			double[] timestepResult = input.getValue().get(timestep);
			double[] result = new double[timestepResult.length];
			Arrays.parallelSetAll(result, i -> timestepResult[i] > 0 ? timestepResult[i] : 0);
			newResult.put(timestep, result);
		});
		out.output(KV.of(new CompositeID(null, null, input.getKey().getCptyId()), newResult));
	}

}
