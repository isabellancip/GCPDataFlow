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
import java.util.Map;

import org.apache.beam.repackaged.beam_sdks_java_core.org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hsbc.training.pipeline.entity.CompositeID;

public class LoggingFn<T> extends DoFn<T, T> {
	private static final long serialVersionUID = -7191210194268990831L;
	private static final Logger LOG = LoggerFactory.getLogger(LoggingFn.class);
	private String logPrefix;

	public LoggingFn(String logPrefix) {
		this.logPrefix = logPrefix;
	}

	@ProcessElement
	public void processElement(@Element T input, OutputReceiver<T> out) {
		String logMsg = "";
		if (input instanceof KV && ((KV) input).getKey() instanceof CompositeID
				&& ((KV) input).getValue() instanceof Map) {
			StringBuilder builder = new StringBuilder();
			Map<String, double[]> value = (Map<String, double[]>) ((KV) input).getValue();
			value.keySet().forEach(
					key -> builder.append(String.format("timestep: %s, %s \n", key, Arrays.toString(value.get(key)))));
			logMsg = String.format(" -------> key: %s, value: %s", ((CompositeID) ((KV) input).getKey()).toString(),
					builder.toString());
		} else {
			logMsg = " -------> " + ToStringBuilder.reflectionToString(input);
		}
		LOG.info(logPrefix + logMsg);
		out.output(input);
	}
}
