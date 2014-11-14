package ptk;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    GerberTest.class,
    PointTest.class,
    PolygonTest.class,
    ConverterTest.class,
})
public class AllTests {

}