package io.github.tprado.type.alias;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringTypeAliasTest {

    @Test
    void can_be_used_as_key() {
        var name = new Name("name");
        var sameName = new Name("name");
        var anotherName = new Name("another name");

        Map<Name, String> map = Map.of(name, "value");

        assertThat(map, hasKey(sameName));
        assertThat(map, not(hasKey(anotherName)));
    }

    @Test
    void is_not_equal_to_a_different_sub_class() {
        var name = new Name("name");
        var anotherName = new AnotherName("name");

        assertThat(name, is(not(anotherName)));
    }

    @Test
    void has_a_string_representation() {
        assertThat(new Name("name").toString(), is("name"));
    }

    @Test
    void prevents_a_null_value() {
        var exception = assertThrows(NullPointerException.class, () -> new Name(null));

        assertThat(exception.getMessage(), is("value cannot be null"));
    }

    private static final class Name extends StringTypeAlias {
        protected Name(String value) {
            super(value);
        }
    }

    private static final class AnotherName extends StringTypeAlias {
        protected AnotherName(String value) {
            super(value);
        }
    }

}
