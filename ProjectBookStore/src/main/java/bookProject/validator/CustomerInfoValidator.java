package bookProject.validator;

import bookProject.model.Cart;
import bookProject.model.CustomerInfo;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CustomerInfoValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass == Cart.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cart cart = (Cart) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.customerForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.customerForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.customerForm.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "NotEmpty.customerForm.phone");
        if (!emailValidator.isValid(cart.getEmail())) {
            errors.rejectValue("email", "Pattern.customerForm.email");
        }
    }
}
