using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace OperatorOverloading
{
    public class Complex
    {
        private double re;
        private double im;

        public Complex(double re, double im)
        {
            this.re = re;
            this.im = im;
        }
        
        public double Re { get { return re; } }
        public double Im { get { return im; } }

        public override string ToString() { return String.Format("({0,5:0.0},{1,5:0.0}i)", Re, Im); }

        public override int GetHashCode()
        {
            return Convert.ToInt32(this.Re * this.Im * 1000);
        }

        public static Complex operator+ (Complex left, Complex right)
        {
            return new Complex(left.re+right.re, left.im+right.im);
        }
        public static Complex operator -(Complex left, Complex right)
        {
            return new Complex(left.re - right.re, left.im - right.im);
        }

        public static Complex operator ++(Complex c)
        {

            return new Complex(c.re+1, c.im+1);
        }

        public Boolean Equals(Complex c)
        {
            return (this.re == c.re) && (this.im == c.im);
        }

       

    }
}
