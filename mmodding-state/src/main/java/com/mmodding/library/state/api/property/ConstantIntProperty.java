package com.mmodding.library.state.api.property;

import java.util.Optional;

public class ConstantIntProperty extends ConstantProperty<Integer> {

	protected ConstantIntProperty(String qualifier, int element) {
		super(qualifier, element, Integer.class);
	}

	public static ConstantIntProperty of(String qualifier, int constant) {
		return new ConstantIntProperty(qualifier, constant);
	}

	@Override
	public Optional<Integer> parse(String string) {
		try {
			int integer = Integer.parseInt(string);
			return integer != this.getConstant() ? Optional.of(integer) : Optional.empty();
		} catch (NumberFormatException exception) {
			return Optional.empty();
		}
	}
}
