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
import org.apache.beam.sdk.values.PCollectionView;

import com.hsbc.training.pipeline.entity.CompositeID;
import com.hsbc.training.pipeline.entity.LegalDoc;

public class CollateralBalanceFn
		extends DoFn<KV<CompositeID, Map<String, double[]>>, KV<CompositeID, Map<String, double[]>>> {
	private static final long serialVersionUID = 7896532371396572180L;

	private PCollectionView<Map<String, LegalDoc>> legalDocMap;

	public CollateralBalanceFn(PCollectionView<Map<String, LegalDoc>> legalDocMap) {
		this.legalDocMap = legalDocMap;
	}

	@ProcessElement
	public void processElement(@Element KV<CompositeID, Map<String, double[]>> input,
			OutputReceiver<KV<CompositeID, Map<String, double[]>>> out, ProcessContext c) {
		Map<String, LegalDoc> legalDoctoCptyMap = c.sideInput(legalDocMap);
		double collateralBalance = Double
				.parseDouble(legalDoctoCptyMap.get(input.getKey().getLegalDocId()).getCollateralBalance());
		Map<String, double[]> newResult = new HashMap<>();
		input.getValue().keySet().forEach(timestep -> {
			double[] timestepResult = input.getValue().get(timestep);
			double[] result = new double[timestepResult.length];
			Arrays.parallelSetAll(result, i -> timestepResult[i] + collateralBalance);
			newResult.put(timestep, result);
		});
		out.output(KV.of(input.getKey(), newResult));
	}

}
