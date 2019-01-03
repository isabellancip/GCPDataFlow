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

public class LegalDoc implements Serializable {

	private static final long serialVersionUID = -4656677248150175281L;

	public LegalDoc(String legalDocId, String cptyId, String nettable, String collateralizable,
			String collateralBalance) {
		super();
		this.legalDocId = legalDocId;
		this.cptyId = cptyId;
		this.nettable = nettable;
		this.collateralizable = collateralizable;
		this.collateralBalance = collateralBalance;
	}

	private String legalDocId;

	private String cptyId;

	private String nettable;

	private String collateralizable;

	private String collateralBalance;

	public String getLegalDocId() {
		return legalDocId;
	}

	public void setLegalDocId(String legalDocId) {
		this.legalDocId = legalDocId;
	}

	public String getCptyId() {
		return cptyId;
	}

	public void setCptyId(String cptyId) {
		this.cptyId = cptyId;
	}

	public String getNettable() {
		return nettable;
	}

	public void setNettable(String nettable) {
		this.nettable = nettable;
	}

	public String getCollateralizable() {
		return collateralizable;
	}

	public void setCollateralizable(String collateralizable) {
		this.collateralizable = collateralizable;
	}

	public String getCollateralBalance() {
		return collateralBalance;
	}

	public void setCollateralBalance(String collateralBalance) {
		this.collateralBalance = collateralBalance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((collateralBalance == null) ? 0 : collateralBalance.hashCode());
		result = prime * result + ((collateralizable == null) ? 0 : collateralizable.hashCode());
		result = prime * result + ((cptyId == null) ? 0 : cptyId.hashCode());
		result = prime * result + ((legalDocId == null) ? 0 : legalDocId.hashCode());
		result = prime * result + ((nettable == null) ? 0 : nettable.hashCode());
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
		LegalDoc other = (LegalDoc) obj;
		if (collateralBalance == null) {
			if (other.collateralBalance != null)
				return false;
		} else if (!collateralBalance.equals(other.collateralBalance))
			return false;
		if (collateralizable == null) {
			if (other.collateralizable != null)
				return false;
		} else if (!collateralizable.equals(other.collateralizable))
			return false;
		if (cptyId == null) {
			if (other.cptyId != null)
				return false;
		} else if (!cptyId.equals(other.cptyId))
			return false;
		if (legalDocId == null) {
			if (other.legalDocId != null)
				return false;
		} else if (!legalDocId.equals(other.legalDocId))
			return false;
		if (nettable == null) {
			if (other.nettable != null)
				return false;
		} else if (!nettable.equals(other.nettable))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LegalDoc [legalDocId=" + legalDocId + ", cptyId=" + cptyId + ", nettable=" + nettable
				+ ", collateralizable=" + collateralizable + ", collateralBalance=" + collateralBalance + "]";
	}

}
