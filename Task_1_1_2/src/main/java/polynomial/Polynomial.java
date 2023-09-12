package polynomial;

public class Polynomial {
    public int[] cfs;
    public int degree;

    public Polynomial(int[] parameters) {
        degree = parameters.length;
        cfs = parameters;
    }

    public int evaluate(int val) {
        int res = 0;

        for (int i = 0; i < this.degree; i++) {
            res += (int) (this.cfs[i] * Math.pow(val, this.degree - i - 1));
        }

        return res;
    }

    public int[] plus(Polynomial p2) {
        int[] new_cfs;

        if (this.degree < p2.degree) {
            new_cfs = p2.cfs;
            for (int i = 0; i < this.degree; i++) {
                new_cfs[p2.degree - i - 1] += this.cfs[this.degree - i - 1];
            }
            this.degree = p2.degree;
        } else {
            new_cfs = this.cfs;
            for (int i = 0; i < p2.degree; i++) {
                new_cfs[this.degree - i - 1] += p2.cfs[p2.degree - i - 1];
            }
        }

        this.cfs = new_cfs;

        return this.cfs;
    }

    public int[] minus(Polynomial p2) {
        int[] new_cfs;

        if (this.degree < p2.degree) {
            new_cfs = p2.cfs;
            for (int i = 0; i < this.degree; i++) {
                new_cfs[p2.degree - i - 1] -= this.cfs[this.degree - i - 1];
            }
            this.degree = p2.degree;
        } else {
            new_cfs = this.cfs;
            for (int i = 0; i < p2.degree; i++) {
                new_cfs[this.degree - i - 1] -= p2.cfs[p2.degree - i - 1];
            }
        }

        this.cfs = new_cfs;

        return this.cfs;
    }

    public int[] times(Polynomial p2) {
        int[] new_cfs;

        if (this.degree < p2.degree) {
            new_cfs = p2.cfs;
            for (int i = 0; i < this.degree; i++) {
                new_cfs[p2.degree - i - 1] *= this.cfs[this.degree - i - 1];
            }
            this.degree = p2.degree;
        } else {
            new_cfs = this.cfs;
            for (int i = 0; i < p2.degree; i++) {
                new_cfs[this.degree - i - 1] *= p2.cfs[p2.degree - i - 1];
            }
        }

        this.cfs = new_cfs;

        return this.cfs;
    }
}
