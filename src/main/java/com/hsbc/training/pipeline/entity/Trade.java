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

package com.hsbc.training.pipeline.entity;

import java.io.Serializable;

public class Trade implements Serializable {

	private static final long serialVersionUID = -4988091446152153085L;

	private String tradeId;

	private String legalDoc;

	public Trade(String tradeId, String legalDoc) {
		super();
		this.tradeId = tradeId;
		this.legalDoc = legalDoc;
	}

	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", legalDoc=" + legalDoc + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((legalDoc == null) ? 0 : legalDoc.hashCode());
		result = prime * result + ((tradeId == null) ? 0 : tradeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (legalDoc == null) {
			if (other.legalDoc != null)
				return false;
		} else if (!legalDoc.equals(other.legalDoc))
			return false;
		if (tradeId == null) {
			if (other.tradeId != null)
				return false;
		} else if (!tradeId.equals(other.tradeId))
			return false;
		return true;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getLegalDoc() {
		return legalDoc;
	}

	public void setLegalDoc(String legalDoc) {
		this.legalDoc = legalDoc;
	}

}
