import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDamage {
    @Test
    public void stupidTest() {
        Damage dmg = new Damage(10, 2);

        assertEquals(10, dmg.damage);
        assertEquals(2, dmg.damage);
    }
}
