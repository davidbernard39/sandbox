package easy.temperatures;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.stream.Collectors;

public class SolutionTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void shouldSolveSimpleData() throws Exception {
        String result = runSolutionWithFiles("/temperatures/in01.txt", "/temperatures/out01.txt");
        Assertions.assertThat(outContent.toString()).isEqualTo(result);
    }

    @Test
    public void shouldSolveOnlyNegativeData() throws Exception {
        String result = runSolutionWithFiles("/temperatures/in02.txt", "/temperatures/out02.txt");
        Assertions.assertThat(outContent.toString()).isEqualTo(result);
    }

    public String runSolutionWithFiles(String in, String out) throws IOException {
        try (InputStream isPrb = getClass().getResourceAsStream(in);
             InputStream isSol =  getClass().getResourceAsStream(out)) {
            System.setIn(isPrb);
            Solution.main(null);
            String result = new BufferedReader(new InputStreamReader(isSol))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
            return result;
        }
    }
}
