import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SecretFinder {

    static class Share {
        BigInteger x;
        BigInteger y;

        Share(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {
        String[] files = {"testcase1.json", "testcase2.json"}; // replace with actual filenames
        for (String file : files) {
            System.out.println("Processing: " + file);
            JSONObject json = (JSONObject) new JSONParser().parse(new FileReader(file));

            JSONObject keys = (JSONObject) json.get("keys");
            int n = ((Long) keys.get("n")).intValue();
            int k = ((Long) keys.get("k")).intValue();

            List<Share> shares = new ArrayList<>();

            for (Object objKey : json.keySet()) {
                if (objKey.equals("keys")) continue;
                String key = (String) objKey;
                int x = Integer.parseInt(key);

                JSONObject root = (JSONObject) json.get(key);
                int base = Integer.parseInt((String) root.get("base"));
                String value = (String) root.get("value");

                BigInteger y = new BigInteger(value, base);
                shares.add(new Share(BigInteger.valueOf(x), y));
            }

            BigInteger secret = lagrangeInterpolationAtZero(shares.subList(0, k));
            System.out.println("Recovered Secret (constant term): " + secret);
            System.out.println();
        }
    }

    public static BigInteger lagrangeInterpolationAtZero(List<Share> shares) {
        MathContext mc = new MathContext(100); // high precision
        BigDecimal secret = BigDecimal.ZERO;

        for (int i = 0; i < shares.size(); i++) {
            BigDecimal xi = new BigDecimal(shares.get(i).x);
            BigDecimal yi = new BigDecimal(shares.get(i).y);
            BigDecimal term = yi;

            for (int j = 0; j < shares.size(); j++) {
                if (i == j) continue;
                BigDecimal xj = new BigDecimal(shares.get(j).x);
                BigDecimal numerator = xj.negate();
                BigDecimal denominator = xi.subtract(xj, mc);
                term = term.multiply(numerator.divide(denominator, mc), mc);
            }
            secret = secret.add(term, mc);
        }

        return secret.toBigInteger(); // round/truncate to BigInteger as per constraints
    }
}
