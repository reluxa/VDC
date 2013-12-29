package org.reluxa.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SelfValidatingValidator implements ConstraintValidator<SelfValidating, Validatable> {

	@Override
  public void initialize(SelfValidating arg0) {
	  // TODO Auto-generated method stub
	  
  }

	@Override
  public boolean isValid(Validatable bean, ConstraintValidatorContext arg1) {
	  return bean.isValid();
  }

}
