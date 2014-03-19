import java.util.LinkedList;

public class Nullstellen {

	public static double ksi = 0.0001;

	public static void main(String[] args) {
//		a();
//		b();
		aitken();
	}

	public static void a() {
		double x = 0, xOld = Double.NaN, f = Double.NaN, fl;
		LinkedList<Float> newton = new LinkedList<Float>();
		LinkedList<Float> aitken = new LinkedList<Float>();
		int i = 0;
		while (f != 0.0 && i < 500) {
			f = Math.pow(Math.E, -x) + Math.pow(2, -x) + 2 * Math.cos(x) - 6;
			fl = ((Math.pow(Math.E, -(x + ksi)) + Math.pow(2, -(x + ksi)) + 2
					* Math.cos(x + ksi) - 6) - f)
					/ ksi;
			xOld = x;
			x = x - (f / fl);
			i++;

			newton.add((float) x);

			System.out.println(i + "\t" + x);
		}

		float n = 0, nOld = Float.NaN;
		i = 0;
		while (n != nOld) {
			nOld = n;
			n = (float) (newton.get(i) - Math.pow(
					newton.get(i + 1) - newton.get(i), 2)
					/ (newton.get(i + 2) - 2 * newton.get(i + 1) + newton
							.get(i)));
			aitken.add(n);
			i++;
			if (i + 2 == newton.size())
				break;
		}
//		for (int n = 0; n < newton.size() - 2; ++n) {
//			aitken.add((float) (newton.get(n) - Math.pow(newton.get(n + 1)
//					- newton.get(n), 2)
//					/ (newton.get(n + 2) - 2 * newton.get(n + 1) + newton
//							.get(n))));
//		}

		System.out.println(i + "\tNewton\t\tAitken");
		float q = Float.NaN;
		for (int j = 0; j < newton.size(); ++j) {

			if (j < aitken.size())
				q = aitken.get(j);
			else
				q = 0.0f;
			System.out.println(j + "\t" + newton.get(j) + "\t" + q);
		}

	}

	public static void b() {
		double x = 0, xOld = 100000, f = Double.NaN, fl;
		int i = 0;
		while (f != 0.0 && i < 500) {

//			 f(x) = ℯ^(6x) + 3ln(2)² ℯ^(2x) - ln(8) ℯ^(4x) - ln(2)³
			f = Math.pow(Math.E, 6 * x) + 3 * Math.pow(Math.log(2), 2)
					* Math.pow(Math.E, 2 * x) - Math.log(8)
					* Math.pow(Math.E, 4 * x) - Math.pow(Math.log(2), 3);

			fl = (Math.pow(Math.E, 6 * (x + ksi)) + 3
					* Math.pow(Math.log(2), 2)
					* Math.pow(Math.E, 2 * (x + ksi)) - Math.log(8)
					* Math.pow(Math.E, 4 * (x + ksi))
					- Math.pow(Math.log(2), 3) - f)
					/ ksi;
			xOld = x;
			x = x - (f / fl);
			i++;
			System.out.println(i + "\t" + x);
		}
	}

	public static void aitken() {
		LinkedList<Float> folge = new LinkedList<Float>();
		LinkedList<Float> aitken = new LinkedList<Float>();
		float f = 0.0f;

		// Generiere Newton-Folge
		for (int i = 0; i < 103; i++) {
			f += Math.pow(-1, i) / (2 * i + 1); // Funktion
			folge.add(f);
		}

		// Generiere Aitken aus Newton
		for (int i = 0; i < folge.size() - 2; i++) {
			aitken.add((float) (folge.get(i) - Math.pow(folge.get(i + 1)
					- folge.get(i), 2)
					/ (folge.get(i + 2) - 2 * folge.get(i + 1) + folge.get(i))));
		}

		// Output
		System.out.println("xi\tNewton\t\tAitken");
		for (int i = 0; i < folge.size(); i++)
			System.out.println(i + "\t" + folge.get(i) + "\t"
					+ (aitken.size() > i ? aitken.get(i) : ""));

	}
}
