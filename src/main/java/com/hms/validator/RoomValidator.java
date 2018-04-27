package com.hms.validator;

import com.hms.model.Room;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.hms.helpers.InputValidatorHelpers.isHtmlSafe;

@Component("roomValidator")
public class RoomValidator implements Validator {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return Room.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        Room room = (Room) target;

        ValidationUtils.rejectIfEmpty(errors, "name", "room.name.empty");
        ValidationUtils.rejectIfEmpty(errors, "price", "room.price.empty");
        ValidationUtils.rejectIfEmpty(errors, "capacity", "room.capacity.empty");

        // check for field injection
        if (isHtmlSafe(room.getDescription())) {
            errors.rejectValue("description", "field.notallowed");
        }
        if (isHtmlSafe(room.getName())) {
            errors.rejectValue("name", "field.notallowed");
        }
        if (isHtmlSafe(room.getBed().toString())) {
            errors.rejectValue("bed", "field.notallowed");
        }
        if (isHtmlSafe(room.getCapacity().toString())) {
            errors.rejectValue("capacity", "field.notallowed");
        }
        if (isHtmlSafe(room.getPrice().toString())) {
            errors.rejectValue("price", "field.notallowed");
        }
    }
}
