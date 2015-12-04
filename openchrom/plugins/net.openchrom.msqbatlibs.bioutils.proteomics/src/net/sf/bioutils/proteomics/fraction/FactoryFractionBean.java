package net.sf.bioutils.proteomics.fraction;

public class FactoryFractionBean implements FactoryFraction {

	@Override
	public Fraction create() {

		return new FractionBean();
	}
}
