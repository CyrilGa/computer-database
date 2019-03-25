package fr.cgaiton611.cdb.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import fr.cgaiton611.cdb.dto.ComputerForm;

public class DateAndTimeValidator implements ConstraintValidator<DateAndTimeAnnotation, ComputerForm> {

	public boolean isValid(ComputerForm computerForm, ConstraintValidatorContext context) {
		if (!(computerForm instanceof ComputerForm)) {
			throw new IllegalArgumentException("@DateAndTime only applies to ComputerForm");
		}
		if (("".equals(computerForm.getIntroducedDate()) && ! "".equals(computerForm.getIntroducedTime()))
				|| (! "".equals(computerForm.getIntroducedDate()) && "".equals(computerForm.getIntroducedTime()))) {
			return false;
		}
		if (("".equals(computerForm.getDiscontinuedDate()) && ! "".equals(computerForm.getDiscontinuedTime()))
				|| (! "".equals(computerForm.getDiscontinuedDate()) && "".equals(computerForm.getDiscontinuedTime()))) {
			return false;
		}
		return true;
	}

	@Override
	public void initialize(DateAndTimeAnnotation constraintAnnotation) {

	}

}
