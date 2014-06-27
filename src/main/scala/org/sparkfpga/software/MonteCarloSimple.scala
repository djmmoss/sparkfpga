package org.sparkfpga.software

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import scala.util.Random
import scala.math._

import breeze.numerics._

object MonteCarloSimple {

    val INVSQRT2 : Double = 0.7071067811865475
    val SQRT2 : Double = 1.414213562373095

    // Option Prices

    def main(args: Array[String]) {
        val conf = new SparkConf().setAppName("WordCount")
        val spark = new SparkContext(conf)

        val p : Double = 1      // Payoff
        val t : Double = 0.5    // Expiry
        val k : Double = 40     // Strike
        val s : Double = 42     // Asset Price
        val v : Double = 0.2    // Asset Volatility
        val q : Double = 0.0    // Asset Yield
        val r : Double = 0.1    // Continuous-Compounding Risk-Free Interest Rate

        val drift : Double = (r -q -0.5*v*v)*t
        val vsqrt : Double = v*breeze.numerics.sqrt(t)

        val nits : Integer = 10000000 // Number of Simulations

        Random.setSeed(42);
        val gen : Array[Double] = Array.fill(nits)(Random.nextDouble) // Generate Array of Uniformly Distributed Doubles

        val prices : Double = gen.map(sim(_, s, p, k, drift, vsqrt)).reduce(_+_) // Magic

        val e_p = (prices/nits.toDouble)

        println("Monte Carlo Price: " + (breeze.numerics.exp(-r*t)*e_p).toString)
        spark.stop()
    }

    def sim(x : Double, s : Double, p : Double, k : Double,  drift : Double, vsqrt : Double) : Double =  {
        val st : Double = s*breeze.numerics.exp(drift + vsqrt*IN(x))
        max(0.0, p*(st - k))
    }

    def IN(p : Double) = -SQRT2*inverfc(2*p)

    def N(x : Double) = 0.5*erfc(-x*INVSQRT2)

    def inverfc(p : Double) : Double = {
        if (p >= 2.0) return -100.0
        if (p <= 0.0) return 100.0
        val pp = if (p < 1.0) p else 2.0 - p
        val t = breeze.numerics.sqrt(-2.0*breeze.numerics.log(pp/2.0))
        var x = -0.70711*((2.30753+t*0.27061)/(1.+t*(0.99229+t*0.04481))-t)
        for (j <- 0 until 2) {
            val err = erfc(x) - pp
            x = x +  err/(1.12837916709551257*breeze.numerics.exp(-x*x)-x*err)
        }
        if (p < 1.0) x else -x
    }
}
