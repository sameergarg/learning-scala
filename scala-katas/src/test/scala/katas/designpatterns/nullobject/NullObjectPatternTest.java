package katas.designpatterns.nullobject;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 *
 */
public class NullObjectPatternTest {

    @Test
    public void shouldUseOptionalForNullCheck() throws Exception {
        //given
        NullObjectPattern nop = new NullObjectPattern();

        //then
        assertThat(nop.optional(null).isPresent() , is(false));

        assertThat(nop.optional(null).orElse("Null was passed"), is("Null was passed"));

        assertThat(nop.optional(null).map(value -> "Value passed"+value).isPresent(), is(false));

    }
}