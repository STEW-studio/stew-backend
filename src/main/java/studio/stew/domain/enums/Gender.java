package studio.stew.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Gender {
    MALE("남성"),
    FEMALE("여성");

    private final String viewName;
    @JsonValue
    public String getViewName() {
        return viewName;
    }
    @JsonCreator
    public static Gender toGender(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.viewName.equals(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid gender: " + value);
    }
}
