package com.formulaone.domain.merchant;

public enum VerificationStatus {
	APPROVED("APPROVED"), DECLINED("DECLINED"), PENDING("PENDING");

	private final String value;

	private VerificationStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
