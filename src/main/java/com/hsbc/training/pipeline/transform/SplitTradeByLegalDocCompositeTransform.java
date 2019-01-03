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

import java.util.Map;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.PCollectionView;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;

import com.hsbc.training.pipeline.entity.CombinedTradeResult;
import com.hsbc.training.pipeline.entity.CompositeID;
import com.hsbc.training.pipeline.entity.LegalDoc;
import com.hsbc.training.pipeline.entity.Trade;

public class SplitTradeByLegalDocCompositeTransform
		extends PTransform<PCollection<KV<String, CombinedTradeResult>>, PCollectionTuple> {

	private static final long serialVersionUID = 1L;

	private PCollectionView<Map<String, Trade>> tradeMap;

	PCollectionView<Map<String, LegalDoc>> legalDocMap;

	TupleTag<KV<CompositeID, Map<String, double[]>>>[] tuples;

	@SafeVarargs
	public SplitTradeByLegalDocCompositeTransform(PCollectionView<Map<String, Trade>> tradeMap,
			PCollectionView<Map<String, LegalDoc>> legalDocMap,
			TupleTag<KV<CompositeID, Map<String, double[]>>>... tuples) {
		super();
		this.tradeMap = tradeMap;
		this.legalDocMap = legalDocMap;
		this.tuples = tuples;
	}

	@Override
	public PCollectionTuple expand(PCollection<KV<String, CombinedTradeResult>> input) {
		return input
				.apply(ParDo.of(new DoFn<KV<String, CombinedTradeResult>, KV<CompositeID, Map<String, double[]>>>() {

					private static final long serialVersionUID = -4418430378360792718L;

					@ProcessElement
					public void processElement(@Element KV<String, CombinedTradeResult> input, MultiOutputReceiver out,
							ProcessContext c) {
						String tradeId = input.getKey();
						Map<String, Trade> tradeToLegalDocMap = c.sideInput(tradeMap);
						Map<String, LegalDoc> legalDoctoCptyMap = c.sideInput(legalDocMap);
						String legalDocId = tradeToLegalDocMap.get(tradeId).getLegalDoc();
						String nettable = legalDoctoCptyMap.get(legalDocId).getNettable();
						String collateralizable = legalDoctoCptyMap.get(legalDocId).getCollateralizable();
						String cptyId = legalDoctoCptyMap.get(legalDocId).getCptyId();
						if ("N".equalsIgnoreCase(nettable)) {
							out.get(tuples[0]).output(
									KV.of(new CompositeID(tradeId, legalDocId, cptyId), input.getValue().getResults()));
						} else if ("N".equalsIgnoreCase(collateralizable)) {
							out.get(tuples[1]).output(
									KV.of(new CompositeID(null, legalDocId, cptyId), input.getValue().getResults()));
						} else {
							out.get(tuples[2]).output(
									KV.of(new CompositeID(null, legalDocId, cptyId), input.getValue().getResults()));
						}
					}
				}).withSideInputs(tradeMap, legalDocMap).withOutputTags(tuples[0],
						TupleTagList.of(tuples[1]).and(tuples[2])));
	}
}
