package io.github.tprado.type.alias;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringTypeAliasTest {

    @Test
    void can_be_used_as_key() {
        var value = new Value("value");
        var sameValue = new Value("value");
        var anotherValue = new Value("another value");

        Map<Value, String> map = Map.of(value, "value");

        assertThat(map, hasKey(sameValue));
        assertThat(map, not(hasKey(anotherValue)));
    }

    @Test
    void is_not_equal_to_a_different_sub_class() {
        var name = new Value("name");
        var anotherName = new AnotherValue("name");

        assertThat(name, is(not(anotherName)));
    }

    @Test
    void has_a_string_representation() {
        assertThat(new Value("name").toString(), is("name"));
    }

    @Test
    void prevents_a_null_value() {
        var exception = assertThrows(NullPointerException.class, () -> new Value(null));

        assertThat(exception.getMessage(), is("value cannot be null"));
    }

    @Nested
    class when_regex_pattern_is_provided {

        @Test
        void prevents_a_value_that_does_not_match_the_pattern() {
            var exception = assertThrows(IllegalArgumentException.class, () -> new RegexValue("bad-format"));

            assertThat(exception.getMessage(), is("'bad-format' does not match '[A-Z]{3}'"));
        }

        @Test
        void accepts_a_value_that_matches_the_pattern() {
            assertThat(new RegexValue("ABC").toString(), is("ABC"));
        }
    }

    private static final class Value extends StringTypeAlias {
        protected Value(String value) {
            super(value);
        }
    }

    private static final class AnotherValue extends StringTypeAlias {
        protected AnotherValue(String value) {
            super(value);
        }
    }

    private static final class RegexValue extends StringTypeAlias {
        private static final Pattern PATTERN = Pattern.compile("[A-Z]{3}");

        protected RegexValue(String value) {
            super(PATTERN, value);
        }
    }

}
